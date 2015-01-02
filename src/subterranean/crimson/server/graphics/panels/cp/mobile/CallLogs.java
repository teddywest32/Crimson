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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import subterranean.crimson.server.commands.AndroidCommands;
import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.DataViewer;
import subterranean.crimson.server.graphics.models.list.CallLogList;
import subterranean.crimson.server.graphics.panels.cp.CPanel;
import subterranean.crimson.universal.containers.CallLog;

public class CallLogs extends CPanel {

	private static final long serialVersionUID = 1L;

	public CallLogs(final ControlPanel cp) {

		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		add(splitPane);

		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		final DataViewer dv = new DataViewer();
		String[] headers = { "Property", "Value" };
		ArrayList<String[]> val = new ArrayList<String[]>();
		val.add(new String[] { "Name", "" });
		val.add(new String[] { "Phone Number", "" });
		val.add(new String[] { "Duration", "" });
		val.add(new String[] { "Date", "" });
		val.add(new String[] { "Type", "" });
		val.add(new String[] { "ID", "" });

		dv.setList(val);
		dv.setHeaders(headers);
		panel_1.add(dv, BorderLayout.CENTER);

		final JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (list.getSelectedValue() == null) {
					return;
				}
				CallLog cl = (CallLog) list.getSelectedValue();

				// extract values and put into the table
				String[] headers = { "Property", "Value" };
				ArrayList<String[]> val = new ArrayList<String[]>();
				val.add(new String[] { "Name", cl.getName() });
				val.add(new String[] { "Phone Number", cl.getPhoneNumber() });
				val.add(new String[] { "Duration", "" + cl.getDuration() });
				val.add(new String[] { "Date", cl.getDate().toString() });
				val.add(new String[] { "Type", "" + cl.getType() });
				val.add(new String[] { "ID", "" + cl.getId() });

				dv.setList(val);
				dv.setHeaders(headers);

			}
		});
		final CallLogList cll = new CallLogList();
		ArrayList<CallLog> ac = AndroidCommands.getCallLog(cp.c);
		if (ac.size() == 0) {
			// no logs
		}
		// add to the model
		for (CallLog a : ac) {
			cll.addElement(a);
		}
		list.setModel(cll);
		scrollPane.setViewportView(list);

		JPanel panel_2 = new JPanel();
		dv.add(panel_2, BorderLayout.NORTH);

		JMenuBar menuBar = new JMenuBar();
		panel_1.add(menuBar, BorderLayout.NORTH);

		JMenu mnNewMenu_2 = new JMenu("Sort");
		menuBar.add(mnNewMenu_2);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("By Date");
		rdbtnNewRadioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				cll.addSort("Date");

			}
		});
		mnNewMenu_2.add(rdbtnNewRadioButton);

		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("By Name");
		rdbtnNewRadioButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				cll.addSort("Name");
			}
		});
		mnNewMenu_2.add(rdbtnNewRadioButton_1);

		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("By Number");
		rdbtnNewRadioButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				cll.addSort("Number");
			}
		});
		mnNewMenu_2.add(rdbtnNewRadioButton_2);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnNewRadioButton);
		bg.add(rdbtnNewRadioButton_1);
		bg.add(rdbtnNewRadioButton_2);

		JMenu mnNewMenu = new JMenu("Filter");
		mnNewMenu.setEnabled(false);
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Date");
		mnNewMenu.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Name");
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Number");
		mnNewMenu.add(mntmNewMenuItem_3);

		JMenu mnNewMenu_1 = new JMenu("Action");
		menuBar.add(mnNewMenu_1);

		final JProgressBar progressBar = new JProgressBar();
		progressBar.setString("");
		progressBar.setStringPainted(true);
		add(progressBar, BorderLayout.SOUTH);

		final JMenuItem mntmNewMenuItem = new JMenuItem("Refresh");
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!mntmNewMenuItem.isEnabled()) {
					return;
				}

				new Thread(new Runnable() {
					public void run() {

						mntmNewMenuItem.setEnabled(false);

						progressBar.setString("Refreshing...");
						progressBar.setIndeterminate(true);
						list.clearSelection();
						String[] headers = { "Property", "Value" };
						ArrayList<String[]> val = new ArrayList<String[]>();
						val.add(new String[] { "Name", "" });
						val.add(new String[] { "Phone Number", "" });
						val.add(new String[] { "Duration", "" });
						val.add(new String[] { "Date", "" });
						val.add(new String[] { "Type", "" });
						val.add(new String[] { "ID", "" });

						dv.setList(val);
						dv.setHeaders(headers);

						cll.setList(AndroidCommands.getCallLog(cp.c));
						progressBar.setString("");
						progressBar.setIndeterminate(false);
						mntmNewMenuItem.setEnabled(true);
					}
				}).start();

			}
		});
		mnNewMenu_1.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Insert");
		mntmNewMenuItem_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				InsertLog il = new InsertLog(cp.c);
				il.setVisible(true);

			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_4);
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
