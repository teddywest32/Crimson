package subterranean.crimson.server.graphics;

/*
 * 	Crimson Extended Administration Tool
 *  Copyright (C) 2015 Subterranean Security
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

import java.util.ArrayList;

import subterranean.crimson.server.graphics.frames.MainScreen;

public class BackgroundProgressLights {
	public static ArrayList<String> waiting = new ArrayList<String>();

	public static void start(String name) {
		if (waiting.size() != 0) {
			// something is already using the progressbar
			waiting.add(name);
			return;
		}
		// theres nothing waiting
		MainScreen.window.progressArea.showDetail(" " + name);
		waiting.add(name);

	}

	public static void stop(String s) {
		if (MainScreen.window.progressArea.pp.p.getText().equals(" " + s)) {
			// stopping current process
			MainScreen.window.progressArea.dropDetail();

			// check for other processes waiting for progressbar
			waiting.remove(0);
			if (waiting.size() != 0) {
				start(waiting.remove(0));
			}
		} else {
			// stopping a waiting process
			waiting.remove(s);
			return;
		}

	}

}
