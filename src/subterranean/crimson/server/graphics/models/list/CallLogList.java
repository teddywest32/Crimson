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
package subterranean.crimson.server.graphics.models.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.AbstractListModel;

import subterranean.crimson.universal.containers.CallLog;

public class CallLogList extends AbstractListModel {

	private static final long serialVersionUID = 1L;
	private boolean sortName = false;
	private boolean sortDate = true;
	private boolean sortNumber = false;

	private String filterName;
	private String filterDate;
	private String filterNumber;

	ArrayList<CallLog> list = new ArrayList<CallLog>();

	@Override
	public Object getElementAt(int arg0) {
		return list.get(arg0);
	}

	public int getSize() {
		return list.size();
	}

	public void addElement(CallLog ac) {
		list.add(ac);

	}

	public void setList(ArrayList<CallLog> l) {
		list = l;
	}

	private void resort() {

		if (sortDate) {
			Collections.sort(list, new Comparator<CallLog>() {
				public int compare(CallLog o1, CallLog o2) {
					long d1 = o1.getDate().getTime();
					long d2 = o2.getDate().getTime();
					if (d1 == d2)
						return 0;
					return d1 < d2 ? -1 : 1;
				}
			});
		} else if (sortName) {
			Collections.sort(list, new Comparator<CallLog>() {
				public int compare(CallLog o1, CallLog o2) {
					String n1 = o1.getName();
					String n2 = o2.getName();

					if (n1.equals(n2))
						return 0;
					return n1.compareTo(n2);
				}
			});
		} else if (sortNumber) {
			Collections.sort(list, new Comparator<CallLog>() {
				public int compare(CallLog o1, CallLog o2) {
					String n1 = o1.getPhoneNumber();
					String n2 = o2.getPhoneNumber();

					if (n1.equals(n2))
						return 0;
					return n1.compareTo(n2);
				}
			});
		}
		this.fireContentsChanged(this, 0, list.size());
	}

	public void addSort(String type) {
		switch (type) {
		case ("Date"): {
			sortDate = true;
			sortName = false;
			sortNumber = false;
			break;
		}
		case ("Name"): {
			sortDate = false;
			sortName = true;
			sortNumber = false;
			break;
		}
		case ("Number"): {
			sortDate = false;
			sortName = false;
			sortNumber = true;
			break;
		}

		}
		resort();
	}

	public void addFilter(String type, String argument) {
		switch (type) {
		case ("Date"): {

			break;
		}
		case ("Name"): {

			break;
		}
		case ("Number"): {

			break;
		}

		}
		resort();
	}

	public void remFilter(String type) {
		switch (type) {
		case ("Date"): {

			break;
		}
		case ("Name"): {

			break;
		}
		case ("Number"): {

			break;
		}

		}
		resort();
	}

}
