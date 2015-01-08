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
package subterranean.crimson.server.graphics;



import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.network.ClientListener;
import subterranean.crimson.server.network.Listener;
import subterranean.crimson.universal.Utilities;

public class GraphicUtilities {

	public static String totalUp() {
		long t = 0;

		for (Listener l : Server.listeners) {
			if (l instanceof ClientListener) {

			}
		}

		return Utilities.familiarize(t, Utilities.BYTES);
	}

	public static String totalDown() {
		long t = 0;

		for (Listener l : Server.listeners) {
			if (l instanceof ClientListener) {

			}
		}

		return Utilities.familiarize(t, Utilities.BYTES);
	}

	public static boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public static long randLong(long n) {
		long bits, val;
		do {
			bits = (new Random().nextLong() << 1) >>> 1;
			val = bits % n;
		} while (bits - val + (n - 1) < 0L);
		return val;
	}

	public static boolean containsTab(JTabbedPane tabs, String name) {

		for (int i = 0; i < tabs.getTabCount(); i++) {
			if (tabs.getTitleAt(i).equals(name)) {
				return true;
			}

		}
		return false;

	}

	public static ImageIcon resize(ImageIcon image, int width, int height) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics2D g2d = (Graphics2D) bi.createGraphics();
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g2d.drawImage(image.getImage(), 0, 0, width, height, null);
		g2d.dispose();
		return new ImageIcon(bi);
	}

}
