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

import subterranean.crimson.universal.Job;
import subterranean.crimson.universal.Logger;

public class JobsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] headers = { "Job Number", "Job Name", "Job Type", "Status", "Completion" };
	private ArrayList<Job> jobs = new ArrayList<Job>();

	@Override
	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public int getRowCount() {
		return jobs.size();
	}

	@Override
	public String getColumnName(int col) {
		return headers[col];
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		switch (headers[arg1]) {
		case ("Job Number"): {

			return jobs.get(arg0).getJobNumber();
		}
		case ("Job Name"): {

			return jobs.get(arg0).getJobName();
		}
		case ("Job Type"): {

			return jobs.get(arg0).getJobType();
		}
		case ("Status"): {

			return jobs.get(arg0).getJobStatus();
		}
		case ("Completion"): {

			return jobs.get(arg0).getJobCompletion();
		}

		}

		Logger.add("Header mismatch!");
		return null;

	}

}
