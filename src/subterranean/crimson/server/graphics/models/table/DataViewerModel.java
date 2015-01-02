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
