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
package subterranean.crimson.universal.streams.filestream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import subterranean.crimson.universal.containers.TransferContainer;

public class BufferWatcher extends Thread {

	private volatile ArrayList<TransferContainer> buffer = new ArrayList<TransferContainer>();
	private FileStream fs;
	private BufferedInputStream is;

	public BufferWatcher(FileStream fileStream) {
		fs = fileStream;
		try {
			is = new BufferedInputStream(new FileInputStream(fs.getFSP().getSrcFile()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start();
	}

	public void run() {
		while (!Thread.interrupted()) {
			if (buffer.size() < 10) {
				TransferContainer tc = new TransferContainer();
				tc.seq = (int) fs.getFSP().getProgress();
				tc.payload = new byte[fs.getFSP().getContainerSize()];
				try {
					is.read(tc.payload, fs.getFSP().getContainerSize() * (int) fs.getFSP().getProgress(), tc.payload.length);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				buffer.add(tc);
			} else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public TransferContainer remove() {
		return buffer.get(0);
	}

}
