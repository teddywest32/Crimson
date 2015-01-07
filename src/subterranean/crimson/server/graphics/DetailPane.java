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
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.network.Connection;

public class DetailPane extends JPanel {

	private static final long serialVersionUID = 1L;

	ArrayList<DModule> modules = new ArrayList<DModule>();

	public DetailPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	}

	public void updateTarget(Connection c) {
		for (DModule dm : modules) {
			dm.changeTarget(c);
		}

	}

	public void updateAll() {
		for (DModule dm : modules) {
			dm.updateGraphics();
		}
	}

	public void addModule(DModule dm) {
		modules.add(dm);
		add(dm);
	}

	public void remModule(DModule dm) {
		modules.remove(dm);
		remove(dm);
	}

	public void stopStreams() {
		updateTarget(null);

	}

	public void refreshModules() {
		for (DModule d : modules) {
			remove(d);
		}
		modules.clear();
		if (Server.getSettings().getDmoduleMemory().netUsage != null) {
			modules.add(Server.getSettings().getDmoduleMemory().netUsage);
		}

		if (Server.getSettings().getDmoduleMemory().thumbnail != null) {
			modules.add(Server.getSettings().getDmoduleMemory().thumbnail);
		}
		if (Server.getSettings().getDmoduleMemory().sysInfo != null) {
			modules.add(Server.getSettings().getDmoduleMemory().sysInfo);
		}

		Collections.sort(modules);

		for (DModule d : modules) {
			add(d);
		}

	}
}
