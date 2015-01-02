package subterranean.crimson.universal.objects;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import subterranean.crimson.universal.Logger;

public enum ObjectTransfer {

	;

	public static Serializable fromString(String s, boolean compressed) throws InvalidObjectException {
		if (s == null) {
			throw new InvalidObjectException();
		}

		ObjectInputStream ois = null;
		try {

			ois = new ObjectInputStream(new ByteArrayInputStream(!compressed ? Base64.decode(s) : decompress(Base64.decode(s))));
			return (Serializable) ois.readObject();

		} catch (IllegalArgumentException | IOException | ClassNotFoundException e1) {

			throw new InvalidObjectException();
		} finally {
			try {
				ois.close();
			} catch (Exception e) {

			}
		}

	}

	public static Serializable fromString(String s) throws InvalidObjectException {
		// try uncompressed first
		Serializable o = null;
		try {
			o = fromString(s, false);
		} catch (Throwable e) {
			try {
				o = fromString(s, true);
			} catch (Throwable e1) {
				Logger.add("Could not convert the following string to an object: " + (s.length() > 500 ? s.substring(0, 100) + " . . . " + s.substring(s.length() - 100, s.length()) : s));
				throw new InvalidObjectException();
			}
		}

		return o;
	}

	public static String toString(Serializable object, boolean compress) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (compress) {
			return new String(Base64.encode(compress(baos.toByteArray())));
		} else {
			return new String(Base64.encode(baos.toByteArray()));
		}
	}

	public static byte[] toBytes(Serializable object, boolean compress) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (compress) {
			return compress(baos.toByteArray());
		} else {
			return baos.toByteArray();
		}
	}

	public static Serializable fromBytes(byte[] object, boolean compressed) throws InvalidObjectException {
		if (object == null) {
			throw new InvalidObjectException();
		}

		ObjectInputStream ois = null;
		try {

			ois = new ObjectInputStream(new ByteArrayInputStream(!compressed ? object : decompress(object)));
			return (Serializable) ois.readObject();

		} catch (IllegalArgumentException | IOException | ClassNotFoundException e1) {

			throw new InvalidObjectException();
		} finally {
			try {
				ois.close();
			} catch (Exception e) {

			}
		}
	}

	public static byte[] compress(byte[] target) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			OutputStream out = new DeflaterOutputStream(baos);
			out.write(target);
			out.close();
		} catch (IOException e) {
			throw new AssertionError(e);
		}
		return baos.toByteArray();
	}

	public static byte[] decompress(byte[] bytes) {
		InputStream in = new InflaterInputStream(new ByteArrayInputStream(bytes));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			byte[] buffer = new byte[8192];
			int len;
			while ((len = in.read(buffer)) > 0)
				baos.write(buffer, 0, len);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new AssertionError(e);
		}
	}

}
