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
package subterranean.crimson.permajar.stage2.modules;

import java.io.IOException;

import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Platform;

public class Power {

	public static void restart() {
		Logger.add("Restarting");
		switch (Platform.os) {

		case WINDOWS:
			try {
				Runtime.getRuntime().exec("shutdown -t 0 -r -f");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			try {
				Runtime.getRuntime().exec("reboot");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}

	}

	public static void hibernate() {

		if (Platform.windows) {
			try {
				Runtime.getRuntime().exec("shutdown /h /f");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				Runtime.getRuntime().exec("dbus-send --system --print-reply --dest=\"org.freedesktop.UPower\" /org/freedesktop/UPower org.freedesktop.UPower.Hibernate");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void shutdown() {

		if (Platform.windows) {
			try {
				Runtime.getRuntime().exec("shutdown /p /f");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				Runtime.getRuntime().exec("poweroff");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void logoff() {

		if (Platform.windows) {
			try {
				Runtime.getRuntime().exec("shutdown /l /f");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}

	}

	public static void standby() {

		if (Platform.windows) {
			// nope

		} else {
			try {
				Runtime.getRuntime().exec("dbus-send --system --print-reply --dest=\"org.freedesktop.UPower\" /org/freedesktop/UPower org.freedesktop.UPower.Suspend");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static Boolean[] power_test() {
		Boolean[] b = { test_restart(), test_hibernate(), test_shutdown(), test_logoff(), test_standby() };

		return b;
	}

	public static boolean test_restart() {

		if (Platform.windows) {
			return true;

		} else {
			return false;

		}

	}

	public static boolean test_hibernate() {

		if (Platform.windows) {
			return true;

		} else {
			return true;

		}

	}

	public static boolean test_shutdown() {

		if (Platform.windows) {
			return true;

		} else {
			return false;

		}

	}

	public static boolean test_logoff() {

		if (Platform.windows) {
			return true;

		} else {
			return false;

		}

	}

	public static boolean test_standby() {

		if (Platform.windows) {
			return false;

		} else {
			return true;

		}

	}

}
