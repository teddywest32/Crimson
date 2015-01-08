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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * The main logger for both server and client.
 *
 */

public enum Logger {
	;

	public static ArrayList<String> log = new ArrayList<String>();

	// 0 - least 5 - most verbose
	private static byte logLevel = 3;

	public static void add(String s) {
		String entry = "[G][" + new Date().toString() + "] " + s;
		log.add(entry);

		System.out.println(entry);

	}

	public static void debug(String s) {
		String entry = "[DEBUG][" + new Date().toString() + "] " + s;
		log.add(entry);

		if (logLevel >= 4) {
			System.out.println(entry);
		}

	}

	public static void disk(String s) {
		String entry = "[DISK][" + new Date().toString() + "] " + s;
		log.add(entry);

		if (logLevel == 5) {
			System.out.println(entry);
		}

	}

	public static ArrayList<String> retrieve() {
		return log;
	}

	public static String retrieveString() {

		String out = "";
		for (Iterator<String> it = log.iterator(); it.hasNext();) {
			String s = it.next();
			out += s + "\n";
		}

		return out;
	}

	public static void write(String filename) {
		// writes the whole log
		File f = new File(filename);
		try {
			PrintWriter pw = new PrintWriter(f);
			for (String str : log) {
				pw.println(str);
			}

			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
