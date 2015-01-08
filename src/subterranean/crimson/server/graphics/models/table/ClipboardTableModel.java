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

public class ClipboardTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	public ArrayList<String> saved_strings;
	private String[] columnNames = { "Clipboard Contents" };

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public int getRowCount() {
		return saved_strings.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		return saved_strings.get(row);
	}

	public void add_string(String s) {
		saved_strings.add(0, s);
		this.fireTableDataChanged();
	}

	public ClipboardTableModel(ArrayList<String> cl) {
		saved_strings = cl;
		this.fireTableDataChanged();

	}

}
