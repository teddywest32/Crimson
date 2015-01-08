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
package subterranean.crimson.server.graphics.graphs;

import java.awt.event.ActionEvent;

import org.jfree.data.time.Millisecond;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.Logger;

public class NetworkLineGraph extends LineChart {

	private static final long serialVersionUID = 1L;

	Connection c;

	public NetworkLineGraph(Connection con) {
		c = con;

	}

	public NetworkLineGraph() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (c != null) {
			s1.add(new Millisecond(), c.g.trafficCounter().lastReadThroughput());
		} else {
			s1.add(new Millisecond(), Server.getThroughput());
		}

	}

}
