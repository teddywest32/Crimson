package subterranean.crimson.server.graphics.models.list;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import subterranean.crimson.universal.containers.AndroidContact;

public class ContactList extends AbstractListModel {

	private static final long serialVersionUID = 1L;
	ArrayList<AndroidContact> list = new ArrayList<AndroidContact>();

	@Override
	public Object getElementAt(int arg0) {
		return list.get(arg0);
	}

	public int getSize() {
		return list.size();
	}

	public void addElement(AndroidContact ac) {
		list.add(ac);

	}

}
