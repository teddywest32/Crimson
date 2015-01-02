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
