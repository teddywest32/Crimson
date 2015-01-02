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

public class ProcessModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<String> processes = new ArrayList<String>(); // tab or space
																	// delimited
	private ArrayList<String> columnNames;

	@Override
	public int getColumnCount() {

		return columnNames.size();
	}

	@Override
	public int getRowCount() {

		return processes.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String p = processes.get(rowIndex);
		String parts[] = p.split(" ");

		return parts[columnIndex];

	}

	public String getColumnName(int col) {
		return columnNames.get(col);
	}

	public void refresh(String columns, String[] p) {
		String c[] = columns.split(" ");
		columnNames.clear();
		for (String cc : c) {
			columnNames.add(cc);
		}
		processes.clear();
		for (String pp : p) {
			processes.add(pp);
		}
		this.fireTableDataChanged();
	}

}
