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
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import subterranean.crimson.universal.objects.Base64;

public enum FileLocking {
	;

	private static FileChannel channel;
	private static File file;
	private static FileLock lock;

	public static void lock() {

		if (lockExists()) {
			// already locked
			return;
		}

		// create the filename of the file to lock
		String base = (Platform.isServer ? "S" : "P") + Utilities.nameGen(15);
		// Logger.add("Creating FileLock base: " + base);
		// hash the base
		MessageDigest digest;
		String second = null;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.update(base.getBytes());
			second = Base64.encodeString(new String(digest.digest())).replaceAll("[/\\+=]", "");// strip
																								// out
																								// all
																								// wrong
																								// chars

		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			file = new File(Platform.tempDir + base + second);

			channel = new RandomAccessFile(file, "rw").getChannel();
			lock = channel.lock();
			// Logger.add("Created lock file: " + file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public static boolean lockExists() {
		// Logger.add("Checking for file locks");
		// search through the temp dir looking for a lock file by the algorithm
		File temp = new File(Platform.tempDir);
		for (File f : temp.listFiles()) {
			if (f.getName().startsWith("S")) {
				// this could be a server lock
				if (!Platform.isServer) {
					// Logger.add("This is a permajar, next");
					continue;
				} else {
					// Logger.add("This is a server");
				}
			} else if (f.getName().startsWith("P")) {
				// this could be a permajar lock
				if (Platform.isServer) {
					// Logger.add("This is a server, next");
					continue;
				} else {
					// Logger.add("This is a permajar");
				}
			} else {
				continue;
			}
			// look at the name
			if (f.getName().length() < 20) {
				// not this one
				continue;
			}
			String base = f.getName().substring(0, 16);

			// Logger.add("File base is: " + base);
			// hash the base
			MessageDigest digest;
			String second = null;
			try {
				digest = MessageDigest.getInstance("MD5");
				digest.update(base.getBytes());
				second = Base64.encodeString(new String(digest.digest())).replaceAll("[/\\+=]", "");// strip
																									// out
																									// all
																									// wrong
																									// chars

			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// Logger.add("Checking if: " + f.getName() + " is equal to: " + base +
			// second);
			if (f.getName().equals(base + second)) {
				// we found a lockfile created by Crimson
				if (f.delete()) {
					// the jvm that created this lockfile has exited
					continue;
				} else {
					// could not delete it either locking is still active or no
					// permissions to delete

					return true;
				}
			}

		}

		return false;
	}

	public static void unlock() {

		try {
			// release the lock
			lock.release();

			// close the channel
			channel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		file.delete();

	}

}
