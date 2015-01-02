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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import subterranean.crimson.permajar.stage1.network.Communications;
import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.containers.TransferContainer;

public class UploadTransfer extends Transfer {

	public static final String type = "upload";

	public UploadTransfer(String lpath) {
		transferredContainers = 0;
		localFile = new File(lpath);
		filename = localFile.getName();
		status = "RUNNING";

		totalSize = localFile.length(); // size in bytes
		containerSize = 1024;// TODO make this dynamic
		if ((totalSize % containerSize) == 0) {
			// it fits evenly
			containersNeeded = (int) (totalSize / containerSize);
		} else {
			// it doesnt fit evenly
			containersNeeded = (int) ((totalSize / containerSize) + 1);

		}

	}

	public UploadTransfer(String lpath, Connection con) {
		this(lpath);
		c = con;
	}

	@Override
	public void run() {
		Logger.add("Starting Upload");
		// get the hash
		try {
			sha1 = Utilities.getHash(localFile.getAbsolutePath(), "SHA");
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// inform the download handler of the hash
		Object[] o = { transferId, sha1 };
		Message m = new Message(Utilities.randId(), MN.informHash);
		m.auxObject = o;
		if (c != null) {
			c.send(m);
		} else {
			Communications.sendHome(m);
		}

		// get a input stream on the object
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(localFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// skip the inputstream ahead if any containers have been sent
		try {
			Logger.add("Skipping the inputstream ahead: " + (transferredContainers * containerSize) + " bytes");
			bis.skip(transferredContainers * containerSize);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = transferredContainers + 1; i <= containersNeeded; i++) {
			// test state
			if (status.equals("Paused")) {

				try {
					synchronized (this) {
						this.wait();
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (status.equals("Terminated")) {

				return;

			}

			TransferContainer tc = new TransferContainer();
			tc.transferId = transferId;
			byte[] buffer = new byte[containerSize];
			try {
				bis.read(buffer, 0, buffer.length);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tc.payload = buffer;
			tc.seq = i;
			tc.last = containersNeeded;
			tc.totalSize = totalSize;

			// send the segment
			Message mes = new Message(Utilities.randId(), MN.TRANSFER_data, tc);
			if (c != null) {
				c.send(mes);
			} else {
				Communications.sendHome(mes);

			}
			transferredContainers++;
			update();

		}
		// complete
		if (c != null) {
			status = "Checking Integrity";
			update();

			String hash = ClientCommands.transfer_getMD5(c, transferId);

			if (hash.equals(sha1)) {
				status = "Complete";
			} else {
				status = "Failed";
			}
			update();
		}

	}

	public void terminate() {
		status = "Terminated";

	}

	public void pause() {
		Logger.add("Containers sent before pause: " + transferredContainers);
		status = "Paused";
		update();

	}

	public void unpause() {
		Logger.add("Starting on container: " + containerSize + 1);
		status = "Transferring";
		update();
		// continue the thread
		synchronized (this) {
			notify();
		}

	}
}
