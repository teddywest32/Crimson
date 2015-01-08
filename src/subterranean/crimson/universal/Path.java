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

import java.io.Serializable;



public class Path implements Serializable {

	private static final long serialVersionUID = 1L;
	public static String unixSeparator = "/";
	public static String windowsSeparator = "\\";

	private String path = null;
	private boolean valid;

	public Path(String p) {
		// try to validate
		path = p;
	}

	public Path(String parent, String filename) {
		if (parent == null) {
			parent = "";
		}
		if (filename == null) {
			filename = "";
		}
		String uParent = Path.toUnix(parent);
		String uName = Path.toUnix(filename);
		if (!uParent.endsWith("/")) {
			uParent += "/";
		}
		path = uParent + uName.replaceAll("/", "");

	}

	public String getName() {
		String uPath = Path.toUnix(path);
		return uPath.substring(uPath.lastIndexOf("/") + 1);
	}

	public String getAbsolutePath() {
		return Path.toPlat(path);
	}

	public String getParentPath() {
		String uPath = Path.toUnix(path);
		return Path.toPlat(uPath.substring(0, uPath.lastIndexOf("/") + 1));
	}

	public void replace(String regex, String replacement) {
		path = path.replaceAll(regex, replacement);
	}

	public static String toPlat(String path) {
		if (Platform.windows) {
			return toWindows(path);
		} else {
			return toUnix(path);
		}

	}

	public static String toUnix(String path) {
		return path.replaceAll("\\\\", "/");
	}

	public static String toWindows(String path) {
		return path.replaceAll("/", "\\\\");
	}

}
