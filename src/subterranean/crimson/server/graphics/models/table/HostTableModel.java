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
package subterranean.crimson.server.graphics.models.table;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.Reporter;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.translation.T;

public class HostTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	public ArrayList<Connection> hosts = new ArrayList<Connection>();
	public String[] columnNames;

	public HostTableModel() {

		columnNames = Server.getSettings().getListHeaders();

	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return hosts.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Connection h = null;
		try {
			h = hosts.get(rowIndex);
		} catch (IndexOutOfBoundsException e2) {
			// Unlucky, the host is no longer in the table
			Reporter.report("The host was not in the table for getValueAt(int, int)");
			this.fireTableDataChanged();
			return null;
		}
		switch (columnNames[columnIndex]) {
		case "Location": {

			ImageIcon ico = null;
			// get country code

			try {
				ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/flags/" + h.getProfile().info.getLocation().get("CountryCode").toLowerCase() + ".png")));
				ico.setDescription(h.getProfile().info.getLocation().get("CountryName"));
			} catch (Exception e) {
				try {
					ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/flags/unknown.png")));

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Reporter.report(e);
				}
				ico.setDescription(T.t("misc-unknown"));
			}
			return ico;
		}
		case "Client Version":

			return h.getProfile().info.getVersion();
		case "Activity Status":

			return h.getProfile().info.getActivityStatus();
		case "Hostname":

			return h.getProfile().info.getHostname();
		case "Username":

			return h.getProfile().info.getUsername();
		case "Internal IP":
			return h.getProfile().info.getAdapters().nextElement().getInetAddresses().nextElement().getHostAddress();
		case "Operating System":

			ImageIcon ico = null;
			String os = h.getProfile().info.getOSname().toLowerCase();

			try {
				if (os.contains("windows")) {
					if (os.contains("8")) {
						ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/windows/windows8-16.png")));
					} else if (os.contains("7")) {
						ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/windows/windows7-16.png")));
					} else if (os.contains("xp")) {
						ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/windows/windowsxp-16.png")));
					} else if (os.contains("vista")) {
						ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/windows/windowsvista-16.png")));
					}
				} else if (os.contains("mac") || os.contains("osx")) {
					ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/osx/apple-16.png")));

				} else if (os.contains("arch")) {
					ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/linux/arch-16.png")));
				} else if (os.contains("slackware")) {
					ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/linux/slackware-16.png")));
				} else if (os.contains("fedora")) {
					ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/linux/fedora-16.png")));
				} else if (os.contains("centos")) {
					ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/linux/centos-16.png")));
				} else if (os.contains("debian")) {
					ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/linux/debian-16.png")));
				} else if (os.contains("mint")) {
					ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/linux/mint-16.png")));
				} else if (os.contains("opensuse")) {
					ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/linux/opensuse-16.png")));
				} else if (os.contains("ubuntu")) {
					ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/linux/ubuntu-16.png")));
				}

				ico.setDescription(h.getProfile().info.getOSname());
			} catch (Exception e) {
				try {
					ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/flags/unknown.png")));
					ico.setDescription(h.getProfile().info.getOSname());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Reporter.report(e);
				}
			}
			return ico;

		case "Privileges":
			return "Unknown";
		case "Sent Data":
			return Utilities.familiarize(h.getWrittenBytes(), Utilities.BYTES);
		case "Received Data":
			return Utilities.familiarize(h.getReadBytes(), Utilities.BYTES);
		case "Down Speed":
			return Utilities.familiarize(h.getWriteSpeed(), Utilities.BYTES_PER_SECOND);
		case "Up Speed":
			return Utilities.familiarize(h.getReadSpeed(), Utilities.BYTES_PER_SECOND);
		case "Java Version":
			return "Unknown";
		case "External IP":

			return h.rAddress;
		case "System Uptime":
			return (new Date().getTime() - h.getProfile().info.getSystemStartDate().getTime()) / 1000;
		case "Virtualization":
			return "" + h.getProfile().info.isVirtualized();
		case "Client ID": {
			return "" + h.clientID;
		}
		case "Encoding": {
			return h.getProfile().info.getEncoding();
		}
		case "Timezone": {
			return h.getProfile().info.getTimezone();
		}
		case "Language": {
			return h.getProfile().info.getLanguage();
		}
		case "Phone Number": {
			return h.getProfile().info.getPhoneNumber();
		}
		case "IMEI": {
			return h.getProfile().info.getIMEI();
		}
		case "Operator": {
			return h.getProfile().info.getOperatorName();
		}
		case "SIM Serial": {
			return h.getProfile().info.getSIM_serial();
		}
		case "SIM Code": {
			return h.getProfile().info.getSIM_operator_code();
		}
		case "JRE Uptime": {
			return (new Date().getTime() - h.getProfile().info.getJreStartDate().getTime()) / 1000;
		}
		case "Current Process": {

			return "Unknown";
		}

		}

		return null;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public void addHost(Connection c) {

		hosts.add(c);
		this.fireTableDataChanged();
	}

	public void removeHost(Connection c) {

		for (Connection h : hosts) {
			if (h == c) {
				hosts.remove(h);
				this.fireTableDataChanged();
				return;
			}
		}

	}

	public Connection getHostConnection(int row) {
		return hosts.get(row);
	}

	public ImageIcon fakeLocation(int row) {
		ImageIcon ico = null;
		try {
			switch (row) {
			case 0: {
				ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/flags/us.png")));
				ico.setDescription("United States");
				break;
			}
			case 1: {
				ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/flags/de.png")));
				ico.setDescription("Germany");
				break;
			}
			case 2: {
				ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/flags/us.png")));
				ico.setDescription("United States");
				break;
			}
			case 3: {
				ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/flags/fr.png")));
				ico.setDescription("France");
				break;
			}
			case 4: {
				ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/flags/us.png")));
				ico.setDescription("United States");
				break;
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ico;
	}

	public String fakeVersion(int row) {
		return "3.0.0";
	}

	public String fakeProcess(int row) {
		switch (row) {
		case 0: {
			return "idle";
		}
		case 1: {
			return "downloading file";
		}
		case 2: {
			return "idle";
		}
		case 3: {
			return "idle";
		}
		case 4: {
			return "retrieving information";
		}
		}
		return "";
	}

	public String fakeExternal(int row) {
		switch (row) {
		case 0: {
			return "74.188.196.90";
		}
		case 1: {
			return "31.172.55.177";
		}
		case 2: {
			return "74.188.61.83";
		}
		case 3: {
			return "37.60.50.66";
		}
		case 4: {
			return "74.188.23.103";
		}
		}
		return "";
	}

	public String fakeActivity(int row) {
		switch (row) {
		case 0: {
			return "idle";
		}
		case 1: {
			return "active";
		}
		case 2: {
			return "active";
		}
		case 3: {
			return "active";
		}
		case 4: {
			return "idle";
		}
		}
		return "";
	}

	public String fakeHostname(int row) {
		switch (row) {
		case 0: {
			return "ubuntu_server";
		}
		case 1: {
			return "workstation_3";
		}
		case 2: {
			return "desktop";
		}
		case 3: {
			return "win";
		}
		case 4: {
			return "android-2120ee3b45b25e49";
		}
		}
		return "";
	}

	public String fakeUsername(int row) {
		switch (row) {
		case 0: {
			return "root";
		}
		case 1: {
			return "user3";
		}
		case 2: {
			return "administrator";
		}
		case 3: {
			return "win-admin";
		}
		case 4: {
			return "none";
		}
		}
		return "";
	}

	public ImageIcon fakeOS(int row) {
		ImageIcon ico = null;
		try {
			switch (row) {
			case 0: {
				ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/linux/ubuntu-16.png")));
				ico.setDescription("Ubuntu Linux");
				break;
			}
			case 1: {
				ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/windows/windows8-16.png")));
				ico.setDescription("Windows 8.1");
				break;
			}
			case 2: {
				ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/osx/apple-16.png")));
				ico.setDescription("OSX");
				break;
			}
			case 3: {
				ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/windows/windowsXP-16.png")));
				ico.setDescription("Windows XP");
				break;
			}
			case 4: {
				ico = new ImageIcon(ImageIO.read(getClass().getResource("/subterranean/crimson/server/graphics/icons/android/android-16.png")));
				ico.setDescription("Android");
				break;
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ico;
	}

	public int fakeRows() {
		return 5;
	}

}
