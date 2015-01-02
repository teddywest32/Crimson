package subterranean.crimson.universal;

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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public enum Zip {
	;

	public static void unzip(File zip, File folder) {
		if (!folder.exists()) {
			folder.mkdirs();
		}

		byte[] buffer = new byte[1024];

		try {

			// get the zip file content
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String fileName = ze.getName();
				File newFile = new File(folder.getAbsolutePath() + File.separator + fileName);
				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	// for zipping
	private static ArrayList<String> fileList;
	private static String OUTPUT_ZIP_FILE;
	private static String SOURCE_FOLDER;

	public static synchronized void zip(File unzipped, File ofile) {
		fileList = new ArrayList<String>();
		OUTPUT_ZIP_FILE = ofile.getAbsolutePath();
		SOURCE_FOLDER = unzipped.getAbsolutePath();
		generateFileList(new File(SOURCE_FOLDER));

		byte[] buffer = new byte[1024];

		try {

			FileOutputStream fos = new FileOutputStream(OUTPUT_ZIP_FILE);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (String file : fileList) {
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(SOURCE_FOLDER + File.separator + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			// remember close it
			zos.close();

			System.out.println("Done");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		fileList = null;
		OUTPUT_ZIP_FILE = null;
		SOURCE_FOLDER = null;
	}

	public static void generateFileList(File node) {

		// add file only
		if (node.isFile()) {
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileList(new File(node, filename));
			}
		}

	}

	private static String generateZipEntry(String file) {
		return file.substring(SOURCE_FOLDER.length() + 1, file.length());
	}

}
