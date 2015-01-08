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

public class DataViewerModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] headers = { "Name", "Value" };// default
	public ArrayList<String[]> values;

	public DataViewerModel() {
		values = new ArrayList<String[]>();
	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public int getRowCount() {
		return values.size();
	}

	@Override
	public String getColumnName(int col) {
		return headers[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return values.get(rowIndex)[columnIndex];
	}

	public void setHeaders(String[] h) {
		headers = h;
		this.fireTableStructureChanged();
	}

	public void setList(ArrayList<String[]> l) {
		values = l;
		this.fireTableDataChanged();

	}

}
