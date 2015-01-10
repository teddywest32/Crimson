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
package subterranean.crimson.universal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/** Reboots and runs a script **/
public class DelayedScript {

	public static void run(ArrayList<String> lines) {
		String scriptEnding = "";
		switch (Platform.os) {
		case WINDOWS:
			scriptEnding = ".bat";
			break;
		default:
			scriptEnding = ".sh";
			break;

		}

		File root = Utilities.getTemp();
		root.mkdirs();
		File script = new File(root.getAbsoluteFile() + File.separator + "script" + scriptEnding);
		try {
			script.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		try {
			PrintWriter pw = new PrintWriter(script);

			switch (Platform.os) {
			case WINDOWS:
				pw.println("PING -n 2 127.0.0.1>nul");
				break;
			default:
				pw.println("sleep 1");
				break;

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
			switch (Platform.os) {
			case WINDOWS:
				Runtime.getRuntime().exec("cmd /c start /min " + script.getAbsolutePath());
				break;
			default:
				Runtime.getRuntime().exec("sh " + script.getAbsolutePath());
				break;

			}

		} catch (Exception e) {
			// TODO: handle exception
			Logger.add("Could not run script");
		}

		// shut this thing down
		System.exit(0);

	}

}
