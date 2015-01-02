package subterranean.crimson.universal;

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
import java.net.URISyntaxException;
import java.util.Date;

import subterranean.crimson.server.Server;

public enum Platform {
	;

	public static File currentJar;

	public static String fileSeparator;
	public static boolean linux;
	public static boolean osx;

	public static String platformName;
	public static String scriptFilename;

	public static String systemDir;
	public static String tempDir;
	public static boolean versionOsx10_7;

	public static boolean versionOsx10_8;
	public static boolean versionOsx10_9;
	public static boolean versionWindows7;

	public static boolean versionWindows8;
	public static boolean versionWindowsXp;
	public static boolean windows;
	public static boolean systemX64;
	public static boolean systemX86;

	public static boolean jreX64;
	public static boolean jreX86;

	public static boolean isServer;

	public static Date start = new Date();

	static {
		try {
			Server.probe();
			isServer = true;
		} catch (Throwable t) {
			isServer = false;
		}

		platformName = System.getProperty("os.name").toLowerCase();

		try {
			currentJar = new File(Platform.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// find architecture
		if (System.getProperty("os.arch").indexOf("64") != -1) {
			// this system is for sure 64 bit
			systemX86 = false;
			systemX64 = true;
			jreX86 = false;
			jreX64 = true;

		}// else it could be 32 or 64
		else {
			jreX64 = false;
			jreX86 = true;
		}

		tempDir = Path.toPlat(System.getProperty("java.io.tmpdir"));
		// fill out fields
		if (platformName.contains("windows")) {
			windows = true;
			linux = false;
			osx = false;

			fileSeparator = "\\";
			scriptFilename = ".bat";

			if (platformName.contains("7")) {
				versionWindows7 = true;
			}

			if (!systemX86 && !systemX64) {
				// has not been filled out yet
				if (System.getenv("ProgramFiles(x86)") != null) {
					systemX64 = true;
					systemX86 = false;

				} else {
					systemX64 = false;
					systemX86 = true;
				}

			}

			if (!tempDir.endsWith("\\")) {
				tempDir += "\\";
			}

		} else if (platformName.contains("linux")) {
			windows = false;
			linux = true;
			osx = false;

			fileSeparator = "/";
			scriptFilename = ".sh";

			if (!tempDir.endsWith("/")) {
				tempDir += "/";
			}

		} else if (platformName.contains("mac")) {// TODO verify
			windows = false;
			linux = false;
			osx = true;

			fileSeparator = "/";
			scriptFilename = ".sh";

			if (!tempDir.endsWith("/")) {
				tempDir += "/";
			}

		}

	}

}
