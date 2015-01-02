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
import java.util.HashMap;

import javax.swing.filechooser.FileSystemView;

public class GetFileInfo {

	public static HashMap<String, Object> run(String path) {

		Logger.add("Resolving file info for: " + path);

		HashMap<String, Object> info = new HashMap<String, Object>();

		File file = new File(path);
		Logger.add("Using SIGAR to resolve: " + file.getAbsolutePath());
		if (file.isDirectory()) {
			info.putAll(SigarCollection.getDirStats(file.getAbsolutePath()));
		} else {
			info.putAll(SigarCollection.getFileAttr(file.getAbsolutePath()));
		}

		// try to get the icon
		info.put("icon", FileSystemView.getFileSystemView().getSystemIcon(file));
		info.put("filename", file.getName());
		info.put("filepath", file.getAbsolutePath());
		info.put("filesize", file.length());
		Logger.add("Filesize: " + file.length());
		info.put("partition-freespace", file.getFreeSpace());
		info.put("partition-totalspace", file.getTotalSpace());

		for (String s : info.keySet()) {
			Logger.add("[FILEINFO] Key: " + s + " Value: " + info.get(s));
		}

		return info;
	}

}
