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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.containers.TransferContainer;
import subterranean.crimson.universal.streams.Stream;

public class FileStream extends Stream {

	private OutputStream os;

	private BufferWatcher bw;

	public FileStream(FSParameters fsp) {
		super(fsp);
		setup();
	}

	public FileStream(FSParameters fsp, Connection c) {
		super(fsp, c);
		setup();
	}

	private void setup() {
		if (param.isSender()) {

			bw = new BufferWatcher(this);
		} else {
			try {
				os = new FileOutputStream(getFSP().getDestFile());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void received(Message m) {
		// write this container to the temp file
		TransferContainer tc = (TransferContainer) m.auxObject[1];
		try {
			os.write(tc.payload);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void send() {
		// send a container from the buffer
		Message m = new Message(Utilities.randId(), BMN.STREAM_data);
		m.auxObject = new Object[] { getStreamID(), bw.remove() };
		sendMessage(m);

	}

	public FSParameters getFSP() {
		return (FSParameters) param;
	}

}
