package subterranean.crimson.universal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class NativeUtilities {

	public static native String getTitle();

	public static native String virtualization();

	public static native long uptime();

	public static native void protectProcess();

	public static native int getPrivileges();

	public static void load(String jarPath) throws Exception {

		if (!jarPath.startsWith("/")) {
			// add it
			jarPath = "/" + jarPath;
		}

		String[] parts = jarPath.split("/");
		String filename = (parts.length > 1) ? parts[parts.length - 1] : null;
		if (filename == null) {
			throw new IllegalArgumentException();
		}

		String prefix = "";
		String suffix = null;
		if (filename != null) {
			parts = filename.split("\\.", 2);
			prefix = parts[0];
			suffix = (parts.length > 1) ? "." + parts[parts.length - 1] : null;
		}

		// Check if the filename is okay
		if ((prefix.length() < 3)) {
			throw new IllegalArgumentException("The filename has to be at least 3 characters long.");
		}

		// Prepare temporary file
		File temp = File.createTempFile(prefix, suffix);
		temp.deleteOnExit();

		if (!temp.exists()) {
			throw new FileNotFoundException("File " + temp.getAbsolutePath() + " does not exist.");
		}

		// Prepare buffer for data copying
		byte[] buffer = new byte[1024];
		int readBytes;

		// Open and check input stream
		InputStream is = NativeUtilities.class.getClass().getResourceAsStream(jarPath);
		if (is == null) {
			throw new FileNotFoundException("File " + jarPath + " was not found inside JAR.");
		}

		// Open output stream and copy data between source file in JAR and the temporary
		// file
		OutputStream os = new FileOutputStream(temp);
		try {
			while ((readBytes = is.read(buffer)) != -1) {
				os.write(buffer, 0, readBytes);
			}
		} finally {
			// If read/write fails, close streams safely before throwing an exception
			os.close();
			is.close();
		}

		// load the library
		try {
			System.load(temp.getAbsolutePath());
		} catch (Exception e) {
			throw new Exception();
		}
	}

}
