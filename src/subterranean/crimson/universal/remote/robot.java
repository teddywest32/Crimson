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

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * robot.java
 * 
 * @author benbac
 */

public class robot {

	private Robot rt;
	// private Server server;
	public boolean running = false;
	Rectangle defaultScreenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

	private Rectangle screenRect = RemoteVariables.emptyRect;
	private Rectangle oldScreenRect = RemoteVariables.diffRect;

	public robot() {
		try {
			rt = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void Wait() {
		try {
			synchronized (this) {
				wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Notify() {
		try {
			synchronized (this) {
				notify();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, byte[]> getChangedScreenBlocks() {
		if (RemoteVariables.capture.isEmpty())
			RemoteVariables.capture.clearScreen();

		updateScreenRect();
		if (!oldScreenRect.equals(screenRect)) {
			oldScreenRect = screenRect;
			RemoteVariables.capture.updateScreenSize(screenRect);
			RemoteVariables.setNewScreenImage(screenRect, RemoteVariables.colorQuality);
		}

		try {
			RemoteVariables.capture.takeAndSaveImage(rt, RemoteVariables.imageQuality, RemoteVariables.colorQuality, 1, // viewerOptions.getScreenScale(),
					RemoteVariables.screenRect);
			return RemoteVariables.capture.getChangedBlocks();
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, byte[]>();
		}
	}

	public void updateScreenRect() {
		screenRect = new Rectangle(RemoteVariables.screenRect);
		if (screenRect.equals(RemoteVariables.emptyRect))
			screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	}

	public BufferedImage captureScreen() {
		// updateScreenRect(viewerOptions);
		updateScreenRect();
		oldScreenRect = screenRect;
		BufferedImage screen = rt.createScreenCapture(screenRect);

		BufferedImage bimage = new BufferedImage(screenRect.width, screenRect.height, RemoteVariables.colorQuality);
		Graphics2D g2d = bimage.createGraphics();
		g2d.drawImage(screen, 0, 0, screenRect.width, screenRect.height, null);
		// g2d.dispose ();

		return bimage;
	}

	public void setMouseEvents(ArrayList<MouseEvent> evts) {
		for (int i = 0; i < evts.size(); i++)
			setMouseEvent(evts.get(i));
	}

	public void setMouseEvent(MouseEvent evt) {
		final int x = RemoteVariables.screenRect.x + (int) (evt.getX() / RemoteVariables.screenScale);
		final int y = RemoteVariables.screenRect.y + (int) (evt.getY() / RemoteVariables.screenScale);
		rt.mouseMove(x, y);
		int buttonMask = 0;
		int buttons = evt.getButton();
		if ((buttons == MouseEvent.BUTTON1))
			buttonMask = InputEvent.BUTTON1_MASK;
		if ((buttons == MouseEvent.BUTTON2))
			buttonMask |= InputEvent.BUTTON2_MASK;
		if ((buttons == MouseEvent.BUTTON3))
			buttonMask |= InputEvent.BUTTON3_MASK;
		switch (evt.getID()) {
		case MouseEvent.MOUSE_PRESSED:
			rt.mousePress(buttonMask);
			break;
		case MouseEvent.MOUSE_RELEASED:
			rt.mouseRelease(buttonMask);
			break;
		case MouseEvent.MOUSE_WHEEL:
			rt.mouseWheel(((MouseWheelEvent) evt).getUnitsToScroll());
			break;
		}
	}

	public void setKeyEvents(ArrayList<KeyEvent> evts) {
		for (int i = 0; i < evts.size(); i++)
			setKeyEvent(evts.get(i));
	}

	public void setKeyEvent(KeyEvent evt) {
		switch (evt.getID()) {
		case KeyEvent.KEY_PRESSED:
			rt.keyPress(evt.getKeyCode());
			break;
		case KeyEvent.KEY_RELEASED:
			rt.keyRelease(evt.getKeyCode());
			break;
		}
	}
}