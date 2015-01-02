package subterranean.crimson.universal.remote;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * ImageUtility.java
 * 
 * @author benbac
 */

public class ImageUtility {

	private static ImageWriter writer = null;
	private static ImageWriteParam param = null;

	static {
		ImageIO.setUseCache(false);
		Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpeg");
		writer = writers.next();
		param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(1);
	}

	public static void write(BufferedImage image, float quality, OutputStream out) throws IOException {
		ImageOutputStream ios = ImageIO.createImageOutputStream(out);
		writer.setOutput(ios);
		param.setCompressionQuality(quality);
		writer.write(null, new IIOImage(image, null, null), param);
		ios.close();
		// writer.dispose();
	}

	public static byte[] toByteArray(BufferedImage image, float quality) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			if (quality == -1)
				ImageIO.write(image, "jpeg", out); // write without compression
			else
				write(image, quality, out); // write with compression
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[] {};
		}
	}

}