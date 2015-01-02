package subterranean.crimson.permajar.stage2.modules;

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

import java.awt.MouseInfo;
import java.awt.PointerInfo;

import subterranean.crimson.permajar.stage2.Commands;
import subterranean.crimson.universal.Logger;

public class Idle extends Thread {

	public boolean idle = false;
	private int time = 60000;// 1 minute
	private int segments = 0;

	public void run() {
		while (!Thread.interrupted()) {
			int x = MouseInfo.getPointerInfo().getLocation().x;
			int y = MouseInfo.getPointerInfo().getLocation().y;
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// probably exited

			}
			PointerInfo p = MouseInfo.getPointerInfo();
			if (p != null) {
				int x2 = p.getLocation().x;
				int y2 = p.getLocation().y;

				if (x == x2 && y == y2) {
					// pointer has likely not moved since last poll
					segments++;// another segment has been completed

					if (segments > 10) {// ten consecutive segments went without
										// interaction
						idle = true;
						if (segments == 11) {
							Logger.add("System is now IDLE");
							Commands.updateActivityStatus("Idle");
						}

					}
				} else {
					if (idle == true) {
						Logger.add("System is now ACTIVE");
						Commands.updateActivityStatus("Active");
					}
					idle = false;
					segments = 0;// start over

				}

			} else {
				Logger.add("No mouse!");
				return;
			}

		}
	}

}
