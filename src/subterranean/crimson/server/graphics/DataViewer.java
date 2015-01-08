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
package subterranean.crimson.server.graphics;



import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import subterranean.crimson.server.graphics.models.table.DataViewerModel;

public class DataViewer extends JPanel {

	private static final long serialVersionUID = 1L;
	private DataViewerModel dvm;

	public DataViewer() {
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		dvm = new DataViewerModel();
		JTable table = new JTable();
		table.setModel(dvm);

		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
	}

	public void setList(ArrayList<String[]> l) {
		dvm.setList(l);

	}

	public void setHeaders(String[] h) {
		dvm.setHeaders(h);

	}

}
