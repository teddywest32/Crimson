package subterranean.crimson.universal.transfer;

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

import java.io.File;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.network.Connection;

public abstract class Transfer extends Thread {

	// SERVER
	Connection c;
	public int containerSize; // How many bytes each payload carries
	public int containersNeeded;
	public String filename;

	public File localFile; // whatever local file is associated with this transfer

	public String sha1 = "";
	public String progress;
	public int savedContainers;
	public String status;

	public long totalSize;
	public int transferId;

	public int transferredContainers;

	public void update() {
		try {
			// find transfer and update it
			for (ControlPanel cp : Server.controlPanels) {
				// just update all control panels
				cp.updateTransfers();
			}

		} catch (NoClassDefFoundError e) {
			// this must be a permajar so no update needed
		}

	}

}
