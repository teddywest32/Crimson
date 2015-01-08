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
package subterranean.crimson.universal.streams.infostream;

import subterranean.crimson.server.graphics.panels.mainscreen.MainPane;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.SigarCollection;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.streams.Stream;

public class InfoStream extends Stream {

	public InfoStream(ISParameters param) {
		super(param);
	}

	public InfoStream(ISParameters param, Connection c) {
		super(param, c);
	}

	@Override
	public void received(Message m) {
		// update the profile
		c.sd.cpu = (String) m.auxObject[1];
		c.sd.mem = (String) m.auxObject[2];
		MainPane.dp.updateAll();
	}

	@Override
	public void send() {
		Message m = new Message(Utilities.randId(), BMN.STREAM_data);

		m.auxObject = new Object[] { getStreamID(), getParam().isCpuUsage() ? "Unknown" : "N/A", getParam().isRamUsage() ? SigarCollection.getMEMUsage() : "N/A" };

		sendMessage(m);
	}

	public ISParameters getParam() {
		return (ISParameters) param;
	}

}
