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



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class GenerationUtils {

	private static void add(File source, JarOutputStream target, File root) throws IOException {
		BufferedInputStream in = null;
		try {
			if (source.isDirectory()) {
				String name = source.getPath().replace("\\", "/").substring(root.getAbsolutePath().length() + 1);
				// Logger.add("Name: " + name);
				if (!name.isEmpty()) {
					if (!name.endsWith("/")) {
						name += "/";
					}
					JarEntry entry = new JarEntry(name);
					entry.setTime(source.lastModified());
					target.putNextEntry(entry);
					target.closeEntry();
				}
				for (File nestedFile : source.listFiles()) {
					add(nestedFile, target, root);
				}
				return;
			}
			String t = source.getPath().replace("\\", "/").substring(root.getAbsolutePath().length() + 1);
			// Logger.add("Name2: " + t);

			JarEntry entry = new JarEntry(t);
			entry.setTime(source.lastModified());
			target.putNextEntry(entry);
			in = new BufferedInputStream(new FileInputStream(source));

			byte[] buffer = new byte[1024];
			while (true) {
				int count = in.read(buffer);
				if (count == -1) {
					break;
				}
				target.write(buffer, 0, count);
			}
			target.closeEntry();
		} finally {
			if (in != null) {
				in.close();
			}
		}

	}

	public static boolean create(String dest, String[] targets, String main, File root) throws IOException {
		Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, main);

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(dest);
		} catch (Exception e) {
			// failed
			return false;
		}
		JarOutputStream target = new JarOutputStream(fos, manifest);
		for (String s : targets) {
			add(new File(root, s), target, root);
		}

		target.close();
		return true;
	}

	public static String[] listDirs(JarFile jar) {
		ArrayList<String> list = new ArrayList<String>();

		Enumeration enumEntries = jar.entries();
		while (enumEntries.hasMoreElements()) {
			JarEntry file = (JarEntry) enumEntries.nextElement();
			File f = new File(file.getName());
			if (f.isDirectory()) {
				list.add(f.getAbsolutePath());
			}

		}
		String[] ret = new String[list.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = list.get(i);
		}
		return ret;
	}

	public static void makeDIRS(String[] dirs, File path) {
		// makes all of these dirs
		File t = null;
		for (String s : dirs) {
			s = path.getAbsolutePath() + Platform.fileSeparator + s;
			// Logger.add("Making Dir: " + s);
			new File(s).mkdirs();

		}
	}

	public static void writeClasses(String[] classes, File path, ArrayList<String> skip) {
		if (skip == null) {
			// nothing to skip
			skip = new ArrayList<String>();
		}
		for (String s : classes) {
			if (s.isEmpty() || skip.contains(s)) {
				Logger.add("Skipping class: " + s);
				continue;
			}

			InputStream in = GenerationUtils.class.getResourceAsStream("/" + s);

			s = path.getAbsolutePath() + Platform.fileSeparator + s;
			// try to create the dir if needed
			File t = new File(s);
			if (!t.getParentFile().exists()) {
				t.getParentFile().mkdirs();
			}

			if ((in == null) || !s.contains(".")) {
				// Logger.add("Skipping Class: " + s);
				continue;
			}

			// Logger.add("Writing Class: " + s);

			FileOutputStream out = null;
			try {

				out = new FileOutputStream(s);
				int readBytes;
				byte[] buffer = new byte[4096];

				while ((readBytes = in.read(buffer)) > 0) {
					out.write(buffer, 0, readBytes);
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				out.close();
			} catch (IOException e) {
				// we tried
			}

			try {
				in.close();
			} catch (IOException e) {
				// we tried
			}
		}

	}

}
