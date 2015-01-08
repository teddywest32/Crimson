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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package subterranean.crimson.universal.remote;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class EventsListener {

	private final Player player;
	private boolean isActive = false;

	private KeyAdapter keyAdapter;
	private MouseAdapter mouseAdapter;
	private MouseWheelListener mouseWheelListener;
	private MouseMotionAdapter mouseMotionAdapter;

	private ArrayList<KeyEvent> keyEvents = new ArrayList<KeyEvent>();
	private ArrayList<MouseEvent> mouseEvents = new ArrayList<MouseEvent>();

	public EventsListener(Player p) {
		this.player = p;

		keyAdapter = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				keyEvents.add(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				keyEvents.add(e);
			}
		};

		mouseWheelListener = new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				mouseEvents.add(e);
			}
		};

		mouseMotionAdapter = new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseEvents.add(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (player.isSelecting) {
					player.destx = e.getX();
					player.desty = e.getY();
				} else
					mouseEvents.add(e);
			}
		};

		mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (player.isSelecting) {
					player.destx = player.srcx = e.getX();
					player.desty = player.srcy = e.getY();
				} else
					mouseEvents.add(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				player.doneSelecting();
				mouseEvents.add(e);
			}
		};

	}

	public ArrayList<MouseEvent> getMouseEvents() {
		ArrayList<MouseEvent> _mouseEvents = new ArrayList<MouseEvent>();

		synchronized (mouseEvents) {
			_mouseEvents = mouseEvents;
			mouseEvents = new ArrayList<MouseEvent>();
		}

		return _mouseEvents;
	}

	public ArrayList<KeyEvent> getKeyEvents() {
		ArrayList<KeyEvent> _keyEvents = new ArrayList<KeyEvent>();

		synchronized (keyEvents) {
			_keyEvents = keyEvents;
			keyEvents = new ArrayList<KeyEvent>();
		}

		return _keyEvents;
	}

	public boolean isActive() {
		return isActive;
	}

	public void addAdapters() {
		addAdapters(false);
	}

	public void addAdapters(boolean all) {
		if (isActive)
			return;
		player.addKeyListener(keyAdapter);
		player.addMouseWheelListener(mouseWheelListener);
		player.addMouseMotionListener(mouseMotionAdapter);
		player.addMouseListener(mouseAdapter);
		isActive = true;
	}

	public void removeAdapters() {
		removeAdapters(false);
	}

	public void removeAdapters(boolean all) {
		if (!isActive)
			return;
		player.removeKeyListener(keyAdapter);
		player.removeMouseWheelListener(mouseWheelListener);
		player.removeMouseMotionListener(mouseMotionAdapter);
		player.removeMouseListener(mouseAdapter);
		isActive = false;
	}
}
