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
import java.io.IOException;

import subterranean.crimson.universal.Utilities;

public class PluginOperations {

	public static void installLocal(File f, String packagename) {
		try {
			Utilities.copyFile(f, Server.getSettings().getPluginDir());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Server.scan();

	}

	public static void installRemote(byte[] p, String pname) {

		Utilities.write(p, Server.getSettings().getPluginDir().getAbsolutePath() + "/" + pname + ".jar");
		Server.scan();
	}

	public static void updateRemote(byte[] plugin, String packagename) {

	}

}
