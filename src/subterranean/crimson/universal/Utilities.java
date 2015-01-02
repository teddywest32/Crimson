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

import java.awt.Component;
import java.awt.Container;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.AbstractButton;

import subterranean.crimson.server.graphics.frames.MainScreen;
import subterranean.crimson.universal.objects.Base64;

public enum Utilities {
	;

	private static Random rand = new Random();

	/**
	 * Copies sourceFile to destFile
	 *
	 * @param sourceFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File destFile) throws IOException {
		if (!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
	}

	/**
	 * Delete a file or directory
	 *
	 * @param directory
	 *            the file or directory to delete
	 * @return true on success false on failure
	 */
	public static boolean delete(File directory) {

		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (null != files) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						delete(files[i]);
					} else {
						files[i].delete();
					}
				}
			}
		}
		return (directory.delete());
	}

	/**
	 * Extracts a jar file to the given destination directory
	 *
	 * @param target
	 *            target jar
	 * @param destination
	 *            destination directory
	 */
	public static void extractJar(String target, String destination) {

		try {
			JarFile jar = new JarFile(target);
			Enumeration enumEntries = jar.entries();
			ArrayList<JarEntry> list = new ArrayList<JarEntry>();
			while (enumEntries.hasMoreElements()) {
				JarEntry je = (JarEntry) enumEntries.nextElement();
				if (!je.isDirectory()) {
					list.add(je);
				}
			}

			// write files
			for (JarEntry file : list) {
				File f = new File(destination + File.separator + file.getName());
				if (!f.getParentFile().exists()) {
					f.getParentFile().mkdirs();
				}

				InputStream is = jar.getInputStream(file); // get the input
				// stream
				FileOutputStream fos = new FileOutputStream(f);
				while (is.available() > 0) { // write contents of 'is' to 'fos'
					fos.write(is.read());
				}
				fos.close();
				is.close();
			}
			jar.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * Searches for the given filename in the given directory
	 *
	 * @param name
	 * @param searchDir
	 * @param delay
	 * @return full path to the file or null
	 */
	public static String findFileTopDown(String name, File searchDir, long delay) {
		File[] list = searchDir.listFiles();
		if ((list == null) || (list.length == 0) || (!searchDir.isDirectory())) {
			return null;
		}
		for (File f : list) {
			if (f.isDirectory()) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// recurse
				String p = findFileTopDown(name, f, delay);
				if (p != null) {
					return p;
				}
			} else {
				if (f.getName().equals(name)) {
					return f.getAbsolutePath();
				}
			}
		}

		return null;
	}

	public static String findFileBottomUp(String name, File searchDir, long delay) {

		return "";
	}

	/**
	 * Gets the environment variables
	 *
	 * @return HashMap containing the environment variables
	 */
	public static HashMap<String, String> getENV() {
		Map<String, String> map = System.getenv();
		HashMap<String, String> newmap = new HashMap<String, String>();
		for (String k : map.keySet()) {
			newmap.put(k, map.get(k));
		}
		return newmap;
	}

	/**
	 * @return Gets the external IP
	 */
	public static String getExternalIp() {

		// determine external ip of server for distance data
		BufferedReader in = null;
		String extip = "";
		try {
			URL whatismyip = new URL("http://checkip.amazonaws.com");

			in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

			extip = in.readLine();

		} catch (IOException e) {
			// may not be connected to the internet
			extip = "Unknown";
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {

				}
			}
		}

		if (extip == null) {
			extip = "Unknown";
		}
		return extip;

	}

	public static String getIntenalIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "Unknown";
		}
	}

	/**
	 * Gets a byte[] from a file in the main jar
	 *
	 * @param path
	 *            path to resource
	 * @return resource
	 */
	public static byte[] getFileInJar(String path) {
		InputStream is = new BufferedInputStream(Utilities.class.getResourceAsStream(path));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			for (int b; (b = is.read()) != -1;) {
				out.write(b);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return out.toByteArray();

	}

	public static String getHash(String filename, String type) {
		MessageDigest complete = null;
		try {
			InputStream fis = new FileInputStream(filename);
			byte[] buffer = new byte[1024];
			complete = MessageDigest.getInstance(type);
			int numRead;
			do {
				numRead = fis.read(buffer);
				if (numRead > 0) {
					complete.update(buffer, 0, numRead);
				}
			} while (numRead != -1);
			fis.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		byte[] b = complete.digest();

		StringBuffer result = new StringBuffer();

		for (int i = 0; i < b.length; i++) {
			result.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
		}
		return result.toString();
	}

	/**
	 * Returns true when v1 is greater(later) than v2
	 *
	 * @param v1
	 * @param v2
	 * @return true when v1 is greater(later) than v2
	 */
	public static boolean laterVersion(String v1, String v2) {
		if (v1.equals(v2)) {
			return false;
		}
		String[] v1s = v1.split("\\.");
		String[] v2s = v2.split("\\.");

		if (v1s.length != 3) {
			// invalid v1
			return false;
		}
		if (v2s.length != 3) {
			// invalid v2
			return false;
		}

		for (int i = 0; i < 3; i++) {

			if (Integer.parseInt(v2s[i]) > Integer.parseInt(v1s[i])) {
				return false;
			}

		}
		return true;

	}

	/**
	 * Generates a random string of given length
	 *
	 * @param characters
	 *            length of string
	 * @return random string
	 */
	public static String nameGen(int characters) {

		String filename = "";
		for (int i = 0; i < characters; i++) {
			// append a random character
			char c = (char) (new Random().nextInt(122 - 97) + 97);
			filename += c;
		}

		return filename;
	}

	/**
	 * Generates a random port larger than 1024
	 *
	 * @return port
	 */
	public static int randPort() {
		return new Random().nextInt(65525 - 1025) + 1025;
	}

	public static void removeMinMaxClose(Component comp) {
		if (comp instanceof AbstractButton) {
			comp.getParent().remove(comp);
		}
		if (comp instanceof Container) {
			Component[] comps = ((Container) comp).getComponents();
			for (int x = 0, y = comps.length; x < y; x++) {
				removeMinMaxClose(comps[x]);
			}
		}
	}

	/**
	 * Writes the byte array at the given location
	 *
	 * @param b
	 *            byte[] to write
	 * @param target
	 *            destination
	 * @return a File referencing the written array
	 */
	public static File write(byte[] b, File target) {

		try {
			FileOutputStream fileOuputStream = new FileOutputStream(target);
			fileOuputStream.write(b);
			fileOuputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return target;
	}

	/**
	 * Writes the byte array at the given location
	 *
	 * @param b
	 *            byte[] to write
	 * @param target
	 *            destination
	 * @return a File referencing the written array
	 */
	public static File write(byte[] b, String target) {
		return write(b, new File(target));
	}

	public static byte[] readFile(File f) {
		try {
			java.nio.file.Path path = Paths.get(f.getAbsolutePath());
			return Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static File getTempDir() {
		File f = new File(Platform.tempDir + "crimson_" + new Date().getTime());
		f.mkdirs();
		f.deleteOnExit();
		return f;
	}

	public static int randId() {
		return new Random().nextInt(Integer.MAX_VALUE);
	}

	public static short randStreamId() {
		return (short) new Random().nextInt(Short.MAX_VALUE);
	}

	public static int getDetailWidth() {

		return (int) (((float) 0.2004) * (float) MainScreen.window.getWidth() - (float) 3.636);
	}

	public static boolean checkHash(String hash) {
		// extract crimson to temp and delete plugins folder
		File root = new File(Platform.tempDir);
		Zip.unzip(Platform.currentJar, root);
		Utilities.delete(new File(root.getAbsolutePath() + "/subterranean/crimson/server/plugins"));
		Utilities.delete(new File(root.getAbsolutePath() + "/subterranean/crimson/permaJarMulti/plugins"));
		// create the jar

		// hash it
		if (Utilities.getHash(root.getAbsolutePath() + "/jar.jar", "MD5").equals(hash)) {
			return true;
		} else {
			return false;
		}

	}

	public static File getTemp() {
		File root = new File(Platform.tempDir + "crimson_" + (new Date().getTime()));
		root.deleteOnExit();
		return root;

	}

	public static int uptime() {
		Date now = new Date();
		return (int) (now.getTime() - Platform.start.getTime()) / 1000;
	}

	public static byte[] concat(byte[] A, byte[] B) {
		int aLen = A.length;
		int bLen = B.length;
		byte[] C = new byte[aLen + bLen];
		System.arraycopy(A, 0, C, 0, aLen);
		System.arraycopy(B, 0, C, aLen, bLen);
		return C;
	}

	public static String post(String u, HashMap<String, String> p) {

		String response = null;
		try {
			URL url = new URL(u);
			Map<String, Object> params = new LinkedHashMap<>();

			Iterator it = p.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();
				params.put((String) pairs.getKey(), (String) pairs.getValue());
				it.remove();
			}

			StringBuilder postData = new StringBuilder();
			for (Map.Entry<String, Object> param : params.entrySet()) {
				if (postData.length() != 0) {
					postData.append('&');
				}
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(postDataBytes);

			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			StringBuffer out = new StringBuffer();
			for (int c; (c = in.read()) >= 0; out.append((char) c)) {
				;
			}

			response = out.toString();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Logger.add("Received from post: " + response);
		return response;
	}

	public static boolean validDNSname(String s) {
		return s.matches("\\D+");
	}

	public static final String[] BYTES = { " B", " kB", " MB", " GB", " TB", " PB", " EB", " ZB", " YB" };

	public static final String[] BYTES_PER_SECOND = { " B/s", " kB/s", " MB/s", " GB/s", " TB/s", " PB/s", " EB/s", " ZB/s", " YB/s" };

	public static String familiarize(long size, String[] units) {
		int measureQuantity = 1024;

		if (size <= 0) {
			return null;
		}

		if (size < measureQuantity) {
			return size + units[0];
		}

		// incrementing "letter" while value >1023
		int i = 1;
		double d = size;
		while ((d = d / measureQuantity) > (measureQuantity - 1)) {
			i++;
		}

		// remove symbols after coma, left only 2:
		long l = (long) (d * 100);
		d = (double) l / 100;

		if (i < units.length) {
			return d + units[i];
		}

		// if we still here - value is tooo big for us.
		return String.valueOf(size);
	}

	public static void enableAT() {
		// enable assistive technologies for osx
		// code me
	}

	public static byte[] download(String rlocation) {

		URLConnection con;
		DataInputStream dis;
		byte[] fileData = null;
		try {

			con = new URL(rlocation).openConnection();
			dis = new DataInputStream(con.getInputStream());
			fileData = new byte[con.getContentLength()];
			for (int i = 0; i < fileData.length; i++) {
				fileData[i] = dis.readByte();
			}
			dis.close();

		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

		return fileData;
	}

	public static String getHWID() {
		String base = "";
		if (Platform.windows) {
			base = Utilities.run("wmic path win32_physicalmedia get SerialNumber");
		} else if (Platform.linux) {
			base = Utilities.run("lsblk --nodeps -o name,serial | grep sda");
		} else {
			base = Utilities.run("/usr/sbin/diskutil info / | /usr/bin/awk '$0 ~ /UUID/ { print $3 }'");
		}

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.update(base.getBytes());
			return Base64.encodeString(new String(digest.digest())).replaceAll("[/\\+=]", "");

		} catch (NoSuchAlgorithmException e1) {

		}

		return "default";
	}

	public static String run(String cmd) {
		Scanner s = null;
		try {
			s = new Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			s.close();
		}
		return null;

	}

	public static int rand(int lower, int upper) {
		return rand.nextInt(upper - lower + 1) + lower;
	}

}
