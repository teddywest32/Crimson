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

import subterranean.crimson.server.MarketPluginInfo;

public class PluginTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<MarketPluginInfo> plugins = new ArrayList<MarketPluginInfo>();
	// change in renderer too
	private String[] headers = { "Plugin Name", "Latest Version", "Type", "Compatible", "Price" };

	public PluginTableModel() {

	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public int getRowCount() {
		return plugins.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		MarketPluginInfo p = plugins.get(rowIndex);

		return p;
	}

	public String getColumnName(int col) {
		return headers[col];
	}

	public void remove(int removal) {
		plugins.remove(removal);

		this.fireTableDataChanged();

	}

	public MarketPluginInfo getRow(int selectedRow) {
		return plugins.get(selectedRow);
	}

	public void add(ArrayList<MarketPluginInfo> p) {
		plugins = p;
		this.fireTableDataChanged();

	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

}
