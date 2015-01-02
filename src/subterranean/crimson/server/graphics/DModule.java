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

import javax.swing.JPanel;

import subterranean.crimson.server.network.Connection;

public abstract class DModule extends JPanel implements Comparable {

	private static final long serialVersionUID = 1L;
	private boolean showing = false;

	public byte weight; // -5 <= x <= 5

	public abstract void changeTarget(Connection c);

	public boolean isShowing() {
		return showing;
	}

	public void setShowing(boolean showing) {
		this.showing = showing;
	}

	public abstract void updateGraphics();

	public int compareTo(DModule o) {
		return (weight - o.weight);
	}

}
