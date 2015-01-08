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