package subterranean.crimson.server.graphics.panels.cp.mobile;

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

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import subterranean.crimson.server.HostInfo;
import subterranean.crimson.server.graphics.DataViewer;
import subterranean.crimson.server.network.Connection;

public class Information extends JPanel {

	private static final long serialVersionUID = 1L;

	public Information(Connection c) {
		setLayout(new BorderLayout(0, 0));
		ArrayList<String[]> l = new ArrayList<String[]>();
		HostInfo hi = c.getProfile().info;
		l.add(new String[] { "Phone Number", hi.getPhoneNumber() });
		l.add(new String[] { "IMEI", hi.getIMEI() });
		l.add(new String[] { "Operator", hi.getOperatorName() });
		l.add(new String[] { "SIM Operator Code", hi.getSIM_operator_code() });
		l.add(new String[] { "Sim Serial Number", hi.getSIM_serial() });

		String[] h = { "Property", "Value" };

		DataViewer dv = new DataViewer();
		dv.setList(l);
		dv.setHeaders(h);
		add(dv, BorderLayout.CENTER);

	}

}
