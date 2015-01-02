package subterranean.crimson.server;

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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.crypto.spec.SecretKeySpec;

import subterranean.crimson.server.containers.PluginEntry;
import subterranean.crimson.server.containers.ServerSettings;
import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.MovingPanel;
import subterranean.crimson.server.graphics.Welcome;
import subterranean.crimson.server.graphics.frames.MainScreen;
import subterranean.crimson.server.network.AppletConnection;
import subterranean.crimson.server.network.AppletListener;
import subterranean.crimson.server.network.ClientListener;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.server.network.Listener;
import subterranean.crimson.server.network.ListenerContainer;
import subterranean.crimson.server.sdk.PluginSettings;
import subterranean.crimson.server.sdk.interfaces.Manifest;
import subterranean.crimson.universal.CompressionLevel;
import subterranean.crimson.universal.EncType;
import subterranean.crimson.universal.Environment;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.Version;
import subterranean.crimson.universal.database.Database;
import subterranean.crimson.universal.exceptions.NoReplyException;
import subterranean.crimson.universal.objects.InvalidObjectException;
import subterranean.crimson.universal.objects.ObjectTransfer;
import subterranean.crimson.universal.translation.T;
import subterranean.slidinglayout.SLAnimator;
import aurelienribon.tweenengine.Tween;

public class Server {

	public static Database database;

	public static ArrayList<AppletConnection> appletConnections = new ArrayList<AppletConnection>(); // TODO
	public static ArrayList<Connection> connections = new ArrayList<Connection>();
	public static ArrayList<ControlPanel> controlPanels = new ArrayList<ControlPanel>();
	public static String externalIP;

	public static ArrayList<ClientListener> listeners = new ArrayList<ClientListener>();

	public static HashMap<String, String> locationData;

	public static MarketInfo marketInfo;
	public static WebsiteProfile wp = new WebsiteProfile();

	public static Date start;

	public static void addAppletListener(String name, boolean b, SecretKeySpec key, int accessport) {

		// start listener
		// listeners.add(new AppletListener(name, accessport, key));
		Logger.add("Started Applet listener on port: " + accessport);

		// remember listener
		if (b) {

			getSettings().getListeners().add(name + ":" + accessport + ":" + ObjectTransfer.toString(key, false));

		}
		MainScreen.window.addNotification("Successfully Started Applet Listener on Port: " + accessport);

	}

	public static void addClientListener(String name, int port, boolean remember, EncType encryption, SecretKeySpec key, boolean upnp, boolean ssl, CompressionLevel cl) {

		listeners.add(new ClientListener(name, port, key, encryption, upnp, ssl));

		// remember listener
		if (remember) {
			// saving this listener
			ListenerContainer m = new ListenerContainer();
			m.name = name;
			m.portNumber = port;
			m.encryption = encryption;
			m.key = key;
			m.compression = cl;
			m.ssl = ssl;

			getSettings().getListeners().add(ObjectTransfer.toString(m, false));

		}

		Logger.add("Started listener on port: " + port);
		MainScreen.window.addNotification(T.t("notification-listener_add") + " " + port + ")");
	}

	public static int getAppletListenerNumber() {
		int total = 0;
		for (Listener l : listeners) {

			if (l instanceof AppletListener) {
				total++;
			}

		}
		return total;
	}

	public static long getAppletTrafficDN() {
		long total = 0;
		for (Listener l : listeners) {

			if (l instanceof AppletListener) {
				for (AppletConnection c : ((AppletListener) l).connections) {
					// TODO
				}
			}

		}
		return total;
	}

	public static long getAppletTrafficUP() {
		long total = 0;
		for (Listener l : listeners) {

			if (l instanceof AppletListener) {
				for (AppletConnection c : ((AppletListener) l).connections) {
					// TODO
				}
			}

		}
		return total;
	}

	public static int getClientListenerNumber() {
		int total = 0;
		for (Listener l : listeners) {

			if (l instanceof ClientListener) {
				total++;
			}

		}
		return total;
	}

	public static long getClientTrafficDN() {
		long total = 0;
		for (Listener l : listeners) {

			if (l instanceof ClientListener) {
				for (Connection c : ((ClientListener) l).connections) {
					total += c.getReadBytes();
				}
			}

		}
		return total;
	}

	public static long getClientTrafficUP() {
		long total = 0;
		for (Listener l : listeners) {

			if (l instanceof ClientListener) {
				for (Connection c : ((ClientListener) l).connections) {
					total += c.getWrittenBytes();
				}
			}

		}
		return total;
	}

	public static ServerSettings getSettings() {
		return (ServerSettings) database.retrieveObject((short) 1);
	}

	public static boolean installed(String packagename) {
		for (PluginEntry pi : getSettings().getPlugins()) {
			if (pi.packagename.equals(packagename)) {
				return true;
			}
		}
		return false;
	}

	public static void removePluginEntry(String packagename) {
		for (Iterator<PluginEntry> it = getSettings().getPlugins().iterator(); it.hasNext();) {
			PluginEntry s = it.next();
			if (s.packagename.equals(packagename)) {
				it.remove();
			}
		}
	}

	public static void main(String[] argv) {

		Logger.add("Loading Crimson eXtended Administration Tool Version: " + Version.version);

		// Establish an UncaughtException handler first in case something goes wrong
		Thread.setDefaultUncaughtExceptionHandler(new ServerExceptionHandler());

		// Server must start logged off
		getSettings().setLoggedIn(false);

		// Establish a shutdown hook
		Runtime.getRuntime().addShutdownHook(new ServerShutdownHook());

		// Clear out old temp files
		for (File f : new File(Platform.tempDir).listFiles()) {

			if (f.getName().startsWith("crimson_")) {

				// delete it
				if (!Utilities.delete(f)) {
					f.deleteOnExit();
				}

			}

		}

		// Start the user interface
		Logger.add("Loading user interface");
		Tween.registerAccessor(MovingPanel.class, new MovingPanel.Accessor());

		SLAnimator.start();

		MainScreen.window = new MainScreen();

		MainScreen.window.setLocationRelativeTo(null);

		MainScreen.window.setVisible(true);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		MainScreen.window.main.runAction();

		Logger.add("[Server]Interface started");
		// Do the rest in a new thread so the interface can take control

		new Thread(new Runnable() {
			@SuppressWarnings("unused")
			@Override
			public void run() {
				Logger.add("Launching secondary boot thread");
				if (database.isFirstRun() && Version.release) {
					// show welcome screen
					Welcome w = new Welcome();

					w.setLocationRelativeTo(null);
					w.setVisible(true);
				}
				// get some location data
				externalIP = Utilities.getExternalIp();
				locationData = Location.resolve(externalIP);

				// setup plugins
				Logger.add("[Server]Scanning for new plugins");
				scan();
				loadPlugins();

				// Start saved listeners
				if (getSettings().getListeners().size() != 0) {
					for (String s : getSettings().getListeners()) {

						ListenerContainer lc = null;
						try {
							lc = (ListenerContainer) ObjectTransfer.fromString(s);
						} catch (InvalidObjectException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Logger.add("[Server]Adding saved listener on port: " + lc.portNumber);
						addClientListener(lc.name, lc.portNumber, false, lc.encryption, lc.key, lc.upnp, lc.ssl, lc.compression);

					}
				}

				/** Try to login to Crimson **/
				// check for saved login credentials
				if (!Server.getSettings().getUserEmail().isEmpty() && !Server.getSettings().getUserPassword().isEmpty()) {

					try {
						if (ServerCommands.login(getSettings().getUserEmail(), getSettings().getUserPassword()).equals("true")) {
							// success
							Logger.add("[Server]Loading Plugins");
							loadPlugins();
						}
					} catch (NoReplyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				// look for updates
				try {
					if (Server.getSettings().getNotePolicy().isInform_on_update() && !ServerCommands.latestVersion()) {
						Logger.add("[Server]Found an update");
						MainScreen.window.addNotification("A new Version of Crimson has been released!");

					}
				} catch (NoReplyException e) {

				}

				Logger.add("Load Completed");
				Environment.booting = false;
			}
		}).start();

	}

	/**
	 * This method does nothing. It is called in the platform class to determine if this
	 * instance is a server or permajar
	 */
	public static void probe() {
	}

	public static void removeListener(int port) {

		// remove listener
		for (Iterator<ClientListener> it = listeners.iterator(); it.hasNext();) {
			ClientListener s = it.next();
			if (s.getPORT() == port) {
				// found the one to remove
				s.interrupt();
				listeners.remove(s);

				// remove from settings
				int target = 0;
				for (int i = 0; i < getSettings().getListeners().size(); i++) {
					ListenerContainer lc = null;
					try {
						lc = (ListenerContainer) ObjectTransfer.fromString(getSettings().getListeners().get(i));
					} catch (InvalidObjectException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (lc.portNumber == port) {
						// found the entry
						target = i;
						break;
					}

				}
				//
				getSettings().getListeners().remove(target);
				return;
			} else {
				// keep looking
			}
			// Didn't find listener on that port
		}

	}

	public static void scan() {
		if (!getSettings().getPluginDir().exists()) {
			getSettings().getPluginDir().mkdirs();
		}
		for (File f : getSettings().getPluginDir().listFiles()) {
			String base = f.getName().split("\\.")[0];
			boolean contains = false;
			for (PluginEntry p : getSettings().getPlugins()) {
				if (base.equals(p.packagename)) {
					// this plugin is already installed
					contains = true;
				}
			}

			if (!contains) {
				// the plugin is new
				Logger.add("Discovered new plugin");
				installPlugin(f);

			}
		}
	}

	public static void installPlugin(File f) {
		Logger.add("Installing plugin");
		PluginEntry pe = new PluginEntry(f.getName().split("\\.")[0]);
		pe.settingsPointer = database.storeObject(new PluginSettings());
		pe.file = f;
		pe.manifest = loadJar(pe.file);
		if (pe.manifest == null) {
			MainScreen.window.addNotification("Failed to install a plugin");
			return;
		}
		getSettings().getPlugins().add(pe);
		pe.manifest.install();

		MainScreen.window.addNotification(pe.manifest.getPluginName() + " was installed successfully!");

	}

	public static Manifest loadJar(File f) {
		try {

			Class<Manifest> cls = (Class<Manifest>) new URLClassLoader(new URL[] { f.toURI().toURL() }).loadClass("subterranean.crimson.server.plugins." + f.getName().split("\\.")[0] + ".M");
			return cls.newInstance();

		} catch (MalformedURLException e2) {

		} catch (ClassNotFoundException e) {

		} catch (InstantiationException e) {

		} catch (IllegalAccessException e) {

		}
		return null;
	}

	public static void loadPlugins() {
		for (PluginEntry pe : getSettings().getPlugins()) {
			if (pe.manifest == null) {
				pe.manifest = loadJar(pe.file);
			}
			MainScreen.window.addPluginMenu(pe.manifest.getPluginMenu());
			pe.manifest.startup();
		}
	}

	public static int virtConnectionCount() {
		int total = 0;
		for (Connection c : connections) {
			if (c.isVirtual()) {
				total++;
			}
		}
		return total;
	}

	public static int realConnectionCount() {
		int total = 0;
		for (Connection c : connections) {
			if (!c.isVirtual()) {
				total++;
			}
		}
		return total;
	}

	public static String getPluginVersion(String packagename) {
		for (PluginEntry p : getSettings().getPlugins()) {
			if (p.packagename.equals(packagename)) {
				return p.manifest.getVersion();
			}

		}
		return null;
	}

	public static void freeClosedCPs() {
		Iterator<ControlPanel> iter = controlPanels.iterator();
		while (iter.hasNext()) {
			if (!iter.next().isVisible()) {
				iter.remove();
			}
		}

	}

	public static Number getThroughput() {
		// total up throughput on all connections
		long speed = 0;
		for (Connection c : connections) {
			speed += c.getWriteSpeed();
			speed += c.getReadSpeed();
		}
		return speed;
	}

}
