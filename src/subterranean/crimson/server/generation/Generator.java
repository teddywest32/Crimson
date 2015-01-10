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
package subterranean.crimson.server.generation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import subterranean.crimson.server.graphics.GenerationReport;
import subterranean.crimson.server.graphics.frames.MainScreen;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.Options;

public abstract class Generator extends Thread {
	protected Options options;
	protected Path outPath;
	protected ArrayList<String[]> report;

	protected void success() {
		report.add(new String[] { "Outcome", "Success" });
		report.add(new String[] { "Filesize", new File(outPath.toString()).length() + " bytes" });
		report.add(new String[] { "MD5", Utilities.getHash(outPath.toString(), "MD5") });
		report.add(new String[] { "SHA-1", Utilities.getHash(outPath.toString(), "SHA1") });

		try {
			BasicFileAttributes attr = Files.readAttributes(outPath, BasicFileAttributes.class);
			report.add(new String[] { "Creation Date", attr.creationTime().toString() });
			report.add(new String[] { "Last Access Date", attr.lastAccessTime().toString() });
			report.add(new String[] { "Last Modified Date", attr.lastModifiedTime().toString() });
		} catch (IOException e) {
			report.add(new String[] { "Creation Date", "Could not determine" });
			report.add(new String[] { "Last Access Date", "Could not determine" });
			report.add(new String[] { "Last Modified Date", "Could not determine" });
		}

		Runnable r = new Runnable() {
			public void run() {
				GenerationReport gr = new GenerationReport(report, new String[] { "Property", "Value" });
				gr.setLocationRelativeTo(null);
				gr.setVisible(true);
			}

		};
		MainScreen.window.addNotification("Success! Click here for a generation report", r, "info");
	}

	protected void failure(String reason) {
		report.add(new String[] { "Outcome", "Failed" });
		report.add(new String[] { "Reason", reason });
		report.add(new String[] { "MD5", "Failed" });
		report.add(new String[] { "SHA-1", "Failed" });
		report.add(new String[] { "Creation Date", "Could not determine" });
		report.add(new String[] { "Last Access Date", "Could not determine" });
		report.add(new String[] { "Last Modified Date", "Could not determine" });

		MainScreen.window.addNotification("Could not generate jar! Opening generation report...");
		// open a generation report
		GenerationReport gr = new GenerationReport(report, new String[] { "Property", "Value" });
		gr.setLocationRelativeTo(null);
		gr.setVisible(true);
	}
}
