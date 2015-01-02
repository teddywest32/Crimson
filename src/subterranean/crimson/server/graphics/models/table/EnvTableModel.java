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
import java.util.Map;

import javax.swing.table.AbstractTableModel;

public class EnvTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] headers = { "Name", "Value" };
	private ArrayList<String> list;

	public EnvTableModel(Map<String, String> env) {
		list = new ArrayList<String>();
		for (String s : env.keySet()) {
			list.add(s + "<delimeter>" + env.get(s));
		}

	}

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

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String entry = list.get(rowIndex);

		if (columnIndex == 0) {
			return entry.split("<delimeter>")[0];
		} else {
			return entry.split("<delimeter>")[1];
		}

	}

	public void add(String s) {
		list.add(s);
	}

	public void clear() {
		list.clear();
	}

}
