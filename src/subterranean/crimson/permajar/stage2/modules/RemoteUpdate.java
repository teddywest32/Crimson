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



import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import subterranean.crimson.universal.DelayedScript;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.Utilities;

public class RemoteUpdate {

	public static void run(byte[] newJar) {

		Logger.add("Updating Client");
		// write the jar
		File root = new File(Platform.tempDir + "crimson_" + new Date().getTime());
		root.mkdirs();
		Utilities.write(newJar, root.getAbsolutePath() + "/temp.jar");

		ArrayList<String> script = new ArrayList<String>();

		if (Platform.windows) {

			script.add("del \"" + Platform.currentJar.getAbsolutePath() + "\"");
			script.add("javaw -jar " + root.getAbsolutePath() + "/temp.jar");
		} else {

			script.add("rm \"" + Platform.currentJar.getAbsolutePath() + "\"");
			script.add("java -jar " + root.getAbsolutePath() + "/temp.jar ");
		}

		DelayedScript.run(script);

	}

}
