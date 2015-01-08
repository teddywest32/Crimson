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
package subterranean.crimson.server.graphics.dmodules;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import subterranean.crimson.server.graphics.DModule;
import subterranean.crimson.server.graphics.graphs.NetworkLineGraph;
import subterranean.crimson.server.graphics.panels.mainscreen.Main;
import subterranean.crimson.server.network.Connection;

public class NetworkUsage extends DModule {

	private static final long serialVersionUID = 1L;

	NetworkLineGraph nlc;

	public NetworkUsage() {
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Network Usage", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		nlc = new NetworkLineGraph();
		add(nlc);
		setSize(200, 50);

	}

	public void updateGraphics() {
		Main.mainPane.repaint();
		Main.mainPane.validate();
	}

	@Override
	public void changeTarget(Connection c) {
		if (c != null) {
			nlc = new NetworkLineGraph(c);
		} else {
			nlc = null;
		}
	}

	@Override
	public int compareTo(Object o) {
		return (weight - ((DModule) o).weight);
	}

}
