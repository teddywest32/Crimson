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



import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.network.ClientListener;
import subterranean.crimson.server.network.ListenerContainer;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.objects.InvalidObjectException;
import subterranean.crimson.universal.objects.ObjectTransfer;

public class ListenerTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<ClientListener> listeners;
	private String[] headers = { "Name", "Port", "Status", "Sent", "Received" };

	public ListenerTableModel() {
		listeners = Server.listeners;
	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public int getRowCount() {
		return listeners.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ClientListener v = listeners.get(rowIndex);

		switch (columnIndex) {
		case (0): {// name
			return v.getListenerName();
		}
		case (1): {// port
			return v.getPORT();
		}
		case (2): {// status
			if (v.isInterrupted()) {
				return "Not Listening";
			} else {
				return "Listening";
			}
		}
		case (3): {// bytes sent
			long total = 0;

			/*
			 * if (v instanceof ClientListener) { total = ((ClientListener) v).upBytes();
			 * } else {// TODO do it like above // must be a Applet listener for
			 * (AppletConnection c : ((AppletListener) v).connections) { total +=
			 * c.os.getCount(); } }
			 */
			return Utilities.familiarize(total, Utilities.BYTES);
		}
		case (4): {// bytes received
			int total = 0;

			/*
			 * if (v instanceof ClientListener) { for (Connection c : ((ClientListener)
			 * v).connections) { total += c.is.getCount(); } } else { // must be a Applet
			 * listener for (AppletConnection c : ((AppletListener) v).connections) {
			 * total += c.is.getCount(); } }
			 */
			return Utilities.familiarize(total, Utilities.BYTES);
		}

		}
		return null;
	}

	public String getColumnName(int col) {
		return headers[col];
	}

	public void remove(int removal) {
		ClientListener l = null;
		try {
			l = listeners.get(removal);
			l.interrupt();

		} catch (Exception e) {
			// doesnt exist in table
			Logger.add("Listener doesent exist in table");
			return;
		}
		Server.listeners.remove(listeners.remove(removal));
		// remove from settings
		for (int i = 0; i < Server.getSettings().getListeners().size(); i++) {
			String s = Server.getSettings().getListeners().get(i);
			try {
				ListenerContainer lc = (ListenerContainer) ObjectTransfer.fromString(s);
				if (lc.portNumber == l.getPORT()) {
					// remove this listener
					Server.getSettings().getListeners().remove(i);
					break;
				}
			} catch (InvalidObjectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.fireTableDataChanged();

	}

}
