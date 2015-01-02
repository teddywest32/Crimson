package subterranean.crimson.permajar.stage2.modules;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeSystem;

public class LoadJNativeHook {

	public static void loadNativeLibrary() {
		String libName = "JNativeHook";

		try {
			// Try to load the native library assuming the java.library.path was
			// set correctly at launch.
			System.loadLibrary(libName);
		} catch (UnsatisfiedLinkError linkError) {
			// The library is not in the java.library.path so try to extract it.
			try {
				String libResourcePath = "/org/jnativehook/lib/" + NativeSystem.getFamily() + "/" + NativeSystem.getArchitecture() + "/";

				// Get what the system "thinks" the library name should be.
				String libNativeName = System.mapLibraryName(libName);
				// Hack for OS X JRE 1.6 and earlier.
				libNativeName = libNativeName.replaceAll("\\.jnilib$", "\\.dylib");

				// Slice up the library name.
				int i = libNativeName.lastIndexOf('.');
				String libNativePrefix = libNativeName.substring(0, i) + '_';
				String libNativeSuffix = libNativeName.substring(i);

				// Determine if the user specified temp directory should be used.
				String tmpDir = System.getProperty("jnativehook.tmpdir", null);

				File libDir = null;
				if (tmpDir != null) {
					libDir = new File(tmpDir);
				}

				// Create the temp file for this instance of the library.
				File libFile = File.createTempFile(libNativePrefix, libNativeSuffix, libDir);

				// This may return null in some circumstances.
				InputStream libInputStream = GlobalScreen.class.getResourceAsStream(libResourcePath.toLowerCase() + libNativeName);

				if (libInputStream == null) {
					throw new IOException("Unable to locate the native library.");
				}

				// Check and see if a copy of the native lib already exists.
				FileOutputStream libOutputStream = new FileOutputStream(libFile);
				byte[] buffer = new byte[4 * 1024];

				int size;
				while ((size = libInputStream.read(buffer)) != -1) {
					libOutputStream.write(buffer, 0, size);
				}
				libOutputStream.close();
				libInputStream.close();

				libFile.deleteOnExit();

				System.load(libFile.getPath());
			} catch (IOException e) {
				// Tried and Failed to manually setup the java.library.path.
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

}
