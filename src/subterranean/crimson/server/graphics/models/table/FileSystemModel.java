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

//TODO Split this class into remote and local counterparts

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.FileListing;
import subterranean.crimson.universal.FileManager;
import subterranean.crimson.universal.FileResolver;
import subterranean.crimson.universal.exceptions.InvalidResponseException;

public class FileSystemModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	public ArrayList<FileListing> files = new ArrayList<FileListing>();
	private String[] columnNames = { "Filename", "Type", "Last Modification" };
	private static FileManager fm;
	public int size = 16;

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return files.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		FileListing fl = null;
		try {
			fl = files.get(rowIndex);
		} catch (ArrayIndexOutOfBoundsException e) {

			return null;
		}

		switch (columnNames[columnIndex]) {
		case ("Filename"): {
			if (fl.dir) {

				ImageIcon ii = FileResolver.getDirIcon(size);
				ii.setDescription(fl.name);
				return ii;

			} else {
				ImageIcon ii = FileResolver.getIcon(FileResolver.getExtension(fl.name), size);
				ii.setDescription(fl.name);
				return ii;

			}

		}
		case ("Type"): {
			return fl.dir ? "Directory" : FileResolver.expandExtension(FileResolver.getExtension(fl.name));

		}
		case ("Last Modification"): {
			return fl.mTime;

		}

		}
		return null;

	}

	public void clear() {
		files.clear();
		this.fireTableDataChanged();
	}

	public void add(FileListing[] f) {
		if (f == null) {
			// no items to display
		} else {
			for (FileListing s : f) {
				files.add(s);
			}
		}
		this.fireTableDataChanged();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public String getLocalPWD() {

		return fm.pwd();
	}

	public FileListing[] getLocalListing() {
		return fm.list();
	}

	public void localUp() {
		fm.up();
	}

	public void localDown(String f) {
		fm.down(f);
	}

	public FileSystemModel(Connection c) {

		FileListing[] f = null;
		try {
			f = ClientCommands.filemanager_list(c);
		} catch (InvalidResponseException e) {
			// failed to list
			f = new FileListing[1];
			f[0] = new FileListing();
			f[0].name = "Failed...";
		}
		add(f);
		this.fireTableDataChanged();

	}

	public FileSystemModel() {
		// query local system
		fm = new FileManager();
		FileListing[] f = getLocalListing();
		add(f);
		this.fireTableDataChanged();
	}

}
