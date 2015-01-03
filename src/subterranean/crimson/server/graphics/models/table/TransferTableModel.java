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

import subterranean.crimson.universal.streams.filestream.FileStream;
import subterranean.crimson.universal.translation.T;

public class TransferTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] headers = { T.t("misc-type"), T.t("misc-sha1"), T.t("misc-status"), T.t("misc-filename"), T.t("misc-transferred"), T.t("misc-progress") };
	public ArrayList<FileStream> list = new ArrayList<FileStream>();

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

	public void add(FileStream t) {
		list.add(t);
		this.fireTableDataChanged();
	}

	public void update() {
		this.fireTableDataChanged();

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		FileStream t = list.get(rowIndex);
		String header = headers[columnIndex];
		if (header.equals(T.t("misc-type"))) {
			if (t.getFSP().isSender()) {
				return T.t("misc-upload");
			} else {
				return T.t("misc-download");
			}
		} else if (header.equals(T.t("misc-sha1"))) {
			return t.getFSP().getSHA1();
		} else if (header.equals(T.t("misc-status"))) {
			return t.getFSP().getStatus();
		} else if (header.equals(T.t("misc-filename"))) {
			return t.getFSP().getSrcFile().getName();
		} else if (header.equals(T.t("misc-transferred"))) {
			return "";
		} else if (header.equals(T.t("misc-progress"))) {
			return "";
		} else {
			return "";
		}

	}
}
