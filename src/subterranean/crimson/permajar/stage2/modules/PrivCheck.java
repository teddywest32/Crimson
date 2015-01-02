package subterranean.crimson.permajar.stage2.modules;

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
import java.io.IOException;

import subterranean.crimson.universal.Platform;

public class PrivCheck {
	public static final int ADMINISTRATOR = 0;
	public static final int NORMALUSER = 1;

	public static int run() {
		// test privileges by trying to write to a system dir
		File testFile = new File(Platform.systemDir + "t25535file.tmp");
		try {
			testFile.createNewFile();
		} catch (IOException e) {
			// failed probably normal user
			testFile.delete();
			return 1;
		}
		if (testFile.exists()) {
			// successfully created file in a system dir
			testFile.delete();
			return 0;

		}
		testFile.delete();
		return 1;
	}

}
