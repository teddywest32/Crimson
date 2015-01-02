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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

/** Reboots and runs a script **/
public class DelayedScript {

	public static void run(ArrayList<String> lines) {
		File root = new File(Platform.tempDir + "crimson_" + new Date().getTime());
		root.mkdirs();
		File script = new File(root.getAbsoluteFile() + Platform.fileSeparator + "script" + Platform.scriptFilename);
		try {
			script.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			PrintWriter pw = new PrintWriter(script);

			if (Platform.windows) {
				pw.println("PING -n 2 127.0.0.1>nul");

			} else {
				pw.println("sleep 1");
			}
			for (String s : lines) {
				pw.println(s);
			}
			pw.println("exit");
			pw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (Platform.windows) {
				Runtime.getRuntime().exec("cmd /c start /min " + Path.toWindows(script.getAbsolutePath()));
			} else {
				Runtime.getRuntime().exec("sh " + Path.toUnix(script.getAbsolutePath()));
			}
		} catch (Exception e) {
			// TODO: handle exception

		}

		// shut this thing down
		System.exit(0);

	}

}
