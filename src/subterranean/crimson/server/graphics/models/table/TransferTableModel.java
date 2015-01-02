package subterranean.crimson.server.graphics.models.table;

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

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import subterranean.crimson.universal.transfer.Transfer;
import subterranean.crimson.universal.transfer.UploadTransfer;

public class TransferTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] headers = { "Type", "MD5 Hash", "Status", "Filename", "Progress" };
	public ArrayList<Transfer> list = new ArrayList<Transfer>();

	@Override
	public int getColumnCount() {

		return headers.length;
	}

	@Override
	public int getRowCount() {

		return list.size();
	}

	@Override
	public String getColumnName(int col) {
		return headers[col];
	}

	public void add(Transfer t) {
		list.add(t);
		this.fireTableDataChanged();
	}

	public void update() {
		this.fireTableDataChanged();

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Transfer t = list.get(rowIndex);

		switch (columnIndex) {
		case (0): {
			if (t instanceof UploadTransfer) {
				return "Upload";
			} else {
				return "Download";
			}

		}
		case (1): {
			return t.sha1;

		}
		case (2): {
			return t.status;

		}
		case (3): {
			return t.filename;

		}
		case (4): {
			// calculate percentage transferred
			int total = t.containersNeeded;
			int transferred = t.transferredContainers;
			float percent = ((float) transferred / (float) total) * 100;

			return "" + (int) percent + " %";
		}
		default: {
			return null;

		}

		}

	}

}
