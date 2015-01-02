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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.TransferContainer;
import subterranean.crimson.universal.objects.InvalidObjectException;
import subterranean.crimson.universal.objects.ObjectTransfer;

public class DownloadTransfer extends Transfer {

	public static final String type = "download";
	public ArrayList<TransferContainer> received = new ArrayList<TransferContainer>();
	public File tempFile;

	public DownloadTransfer(int tId, String lPath, int contNeeded, int contSize, long size) {

		transferId = tId;
		localFile = new File(lPath);
		filename = localFile.getName();
		containersNeeded = contNeeded;
		containerSize = contSize;
		totalSize = size;

		Logger.add("Total file size: " + totalSize);
	}

	public DownloadTransfer(String lPath) {

		localFile = new File(lPath);
		filename = localFile.getName();

	}

	public void add(TransferContainer tc) {
		transferredContainers++;
		status = "Transferring";
		if (containersNeeded == 0) {
			Logger.add("Using information from transfercontainer");
			containersNeeded = tc.last;
			totalSize = tc.totalSize;

		}

		received.add(tc);
		if ((received.size() + savedContainers) == containersNeeded) {
			// received last one; write the file
			Logger.add("Received last container. Writing File");
			status = "Completed";
			write();

			Logger.add("Checking file integrity");
			try {
				sha1 = Utilities.getHash(localFile.getAbsolutePath(), "SHA");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		update();

		// save when buffer is larger than 1MB
		if ((received.size() * containerSize) > 1048576) {
			saveContainers();
		}
	}

	public void deleteTempStorage() {
		tempFile.delete();
	}

	public void pause() {

		Logger.add("Containers sent before pause: " + transferredContainers);
		status = "Pause";
		update();
		saveContainers();
	}

	private void saveContainers() {
		Logger.add("Saving Containers");
		if (tempFile == null) {
			tempFile = new File("transfer_" + transferId);
			if (!tempFile.exists()) {
				try {
					tempFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// open up temp file for writing
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(tempFile, true));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < received.size(); i++) {
			pw.println(ObjectTransfer.toString(received.remove(0).payload, false));
			savedContainers++;

		}
		pw.close();

	}

	public void terminate() {
		// TODO Auto-generated method stub
		deleteTempStorage();

	}

	public void unpause() {

		Logger.add("Starting on container: " + containerSize + 1);
		status = "Transferring";
		update();
	}

	public void write() {
		Logger.add("WRITING DOWNLOADED FILE");

		try {
			localFile.createNewFile();
			Logger.add("File location: " + localFile.getAbsolutePath());
			OutputStream os = new FileOutputStream(localFile);

			// write saved containers
			if (tempFile != null) {
				Scanner sc = new Scanner(tempFile);
				while (sc.hasNextLine()) {
					os.write((byte[]) ObjectTransfer.fromString(sc.nextLine()));
				}
				sc.close();
			}

			// write buffered containers
			for (TransferContainer tc : received) {
				os.write(tc.payload);
			}

			os.close();

			Logger.add("Write completed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		deleteTempStorage();
	}

}
