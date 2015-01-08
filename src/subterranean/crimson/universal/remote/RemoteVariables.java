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

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

public enum RemoteVariables {

	;

	public static final Rectangle emptyRect = new Rectangle(0, 0, 0, 0);
	public static final Rectangle diffRect = new Rectangle(-1, -1, -1, -1);

	public static float imageQuality = -1.0f;
	public static float screenScale = 1.0f;
	public static int blocks = 20;
	public static int colorQuality = BufferedImage.TYPE_INT_RGB;
	public static BufferedImage screenImage = null;
	public static ScreenCapture capture = new ScreenCapture(imageQuality, blocks, blocks);

	public static Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

	public static Hashtable properties = new Hashtable();

	public static boolean screenCompression = true;

	public static void setNewScreenImage(Rectangle rectangle, int colorQuality) {
		screenImage = (new BufferedImage(rectangle.width, rectangle.height, colorQuality));
	}

	public static void setNewScreenRect() {
		screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	}

	static {
		setNewScreenImage(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()), colorQuality);
	}
}
