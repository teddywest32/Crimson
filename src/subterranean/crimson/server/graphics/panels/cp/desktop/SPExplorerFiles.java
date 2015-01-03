package subterranean.crimson.server.graphics.panels.cp.desktop;

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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.FileProperties;
import subterranean.crimson.server.graphics.FileTransferConfirm;
import subterranean.crimson.server.graphics.StatusLights;
import subterranean.crimson.server.graphics.frames.MainScreen;
import subterranean.crimson.server.graphics.models.table.FileSystemModel;
import subterranean.crimson.server.graphics.renderers.table.FileSystemRenderer;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.FileListing;
import subterranean.crimson.universal.GetFileInfo;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.exceptions.InvalidResponseException;

public class SPExplorerFiles extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable local_table;
	private JTable remote_table;
	public FileSystemModel t1;
	public FileSystemModel t2;
	private Connection c;

	public boolean waiting = false;

	private JTextField local_pwd;
	private JTextField remote_pwd;
	private StatusLights sl;
	private JLabel lblStatusBar;
	private JSplitPane mainSplitPane;
	private JSplitPane toolbarSplitPane;
	private JSplitPane pwdSplitPane;

	private JButton remoteUP;
	private JButton localUP;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JLabel lblLocalFiles;
	private JLabel lblRemote;

	public SPExplorerFiles(final ControlPanel cp, final SPExplorer spe) {
		c = cp.c;
		setLayout(new BorderLayout(0, 0));
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout(0, 0));
		add(jp);

		JPanel top_panel = new JPanel();
		jp.add(top_panel, BorderLayout.NORTH);
		top_panel.setLayout(new BorderLayout(0, 0));

		sl = new StatusLights();
		sl.light(Color.GREEN, 3);
		sl.setPreferredSize(new Dimension(7, 19));
		sl.setBounds(new Rectangle(0, 0, 7, 19));
		sl.setVisible(true);

		top_panel.add(sl, BorderLayout.WEST);

		lblStatusBar = new JLabel("Status Bar");
		top_panel.add(lblStatusBar, BorderLayout.CENTER);

		JPanel toolbar_panel = new JPanel();
		toolbar_panel.setPreferredSize(new Dimension(400, 20));
		top_panel.add(toolbar_panel, BorderLayout.SOUTH);
		toolbar_panel.setLayout(new BorderLayout(0, 0));

		toolbarSplitPane = new JSplitPane();
		toolbar_panel.add(toolbarSplitPane, BorderLayout.CENTER);

		JPanel left_toolPanel = new JPanel();
		FlowLayout fl_left_toolPanel = (FlowLayout) left_toolPanel.getLayout();
		fl_left_toolPanel.setVgap(0);
		fl_left_toolPanel.setAlignment(FlowLayout.RIGHT);
		toolbarSplitPane.setLeftComponent(left_toolPanel);

		localUP = new JButton("up");
		localUP.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sl.replaceLight(Color.YELLOW, 2);
				dispStatus("Please wait...");
				new Thread(new Runnable() {
					public void run() {
						// move local panel up
						freeze();
						t1.localUp();
						t1.clear();
						local_pwd.setText(t1.getLocalPWD());
						t1.add(t1.getLocalListing());
						sl.replaceLight(Color.GREEN, 3);
						dispStatus("Listed: " + t1.files.size() + " files");
						unfreeze();
					}
				}).start();

			}
		});

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!comboBox.isEnabled()) {
					return;
				}

				int nSize = Integer.parseInt(((String) comboBox.getSelectedItem()).substring(0, 2));

				t1.size = nSize;
				local_table.setRowHeight(nSize);
			}
		});

		lblLocalFiles = new JLabel("Local");
		left_toolPanel.add(lblLocalFiles);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "16px", "32px" }));
		left_toolPanel.add(comboBox);
		localUP.setPreferredSize(new Dimension(30, 20));
		localUP.setMinimumSize(new Dimension(0, 0));
		localUP.setMargin(new Insets(0, 0, 0, 0));
		localUP.setFont(new Font("Dialog", Font.BOLD, 9));
		localUP.setActionCommand("");
		left_toolPanel.add(localUP);

		FlowLayout fl_right_toolPanel = new FlowLayout();
		fl_right_toolPanel.setVgap(0);
		fl_right_toolPanel.setAlignment(FlowLayout.LEFT);
		JPanel right_toolPanel = new JPanel(fl_right_toolPanel);
		toolbarSplitPane.setRightComponent(right_toolPanel);

		remoteUP = new JButton("up");
		remoteUP.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				sl.replaceLight(Color.YELLOW, 2);
				dispStatus("Please wait...");
				new Thread(new Runnable() {
					public void run() {
						freeze();

						// move remote panel up
						ClientCommands.filemanager_up(c);
						t2.clear();
						FileListing[] fl = null;
						try {
							fl = ClientCommands.filemanager_list(c);
						} catch (InvalidResponseException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						t2.add(fl);
						if (fl.length > 0) {
							remote_pwd.setText(fl[0].path);
						} else {
							// TODO get remote
						}

						sl.replaceLight(Color.GREEN, 3);
						dispStatus("Listed: " + t1.files.size() + " files");
						unfreeze();
					}
				}).start();

			}

		});
		remoteUP.setPreferredSize(new Dimension(30, 20));
		remoteUP.setMinimumSize(new Dimension(0, 0));
		remoteUP.setActionCommand("");
		remoteUP.setFont(new Font("Dialog", Font.BOLD, 9));
		remoteUP.setMargin(new Insets(0, 0, 0, 0));
		right_toolPanel.add(remoteUP);

		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!comboBox_1.isEnabled()) {
					return;
				}

				int nSize = Integer.parseInt(((String) comboBox_1.getSelectedItem()).substring(0, 2));

				t2.size = nSize;
				remote_table.setRowHeight(nSize);
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "16px", "32px" }));
		right_toolPanel.add(comboBox_1);

		lblRemote = new JLabel("Remote");
		right_toolPanel.add(lblRemote);
		toolbarSplitPane.setDividerLocation(297);
		toolbarSplitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				// resize the other ones too
				int s = toolbarSplitPane.getDividerLocation();
				pwdSplitPane.setDividerLocation(s);
				toolbarSplitPane.setDividerLocation(s);

			}
		});

		mainSplitPane = new JSplitPane();

		JScrollPane local = new JScrollPane();
		mainSplitPane.setLeftComponent(local);
		jp.add(mainSplitPane);

		local_table = new JTable();
		local_table.setRowHeight(16);
		local_table.setOpaque(false);
		local_table.setDefaultRenderer(Object.class, new FileSystemRenderer());
		local_table.setFillsViewportHeight(true);
		local_table.setSize(new Dimension(200, 200));
		local_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// LOCAL TABLE
				final JTable source = (JTable) e.getSource();
				final int sourceRow = source.rowAtPoint(e.getPoint());
				if (sourceRow == -1) {
					return;
				}
				if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {// right click
					// select row
					if (!source.isRowSelected(sourceRow)) {
						source.changeSelection(sourceRow, 0, false, false);
					}

					final FileListing fl = t1.files.get(sourceRow);

					JPopupMenu popup = new JPopupMenu();
					JMenuItem upload = new JMenuItem();
					JMenuItem properties = new JMenuItem();

					upload.setText("Upload");
					upload.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + upload.getText().charAt(0)).toUpperCase() + ".png")));
					upload.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// FileTransferConfirm ftc = new FileTransferConfirm(param);
							// ftc.setVisible(true);

						}
					});
					popup.add(upload);

					// file properties
					properties.setText("File Properties");
					properties.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							FileListing fl = (FileListing) t1.files.get(sourceRow);
							HashMap<String, Object> info = GetFileInfo.run(fl.path + fl.name);
							FileProperties fp = new FileProperties(info);
							fp.setVisible(true);

						}
					});
					popup.add(properties);

					popup.show(local_table, e.getX(), e.getY());

				} else {

					Runnable r = new Runnable() {

						@Override
						public void run() {
							// normal click

							FileListing fl = t1.files.get(sourceRow);
							if (fl.dir) {
								sl.replaceLight(Color.YELLOW, 2);
								dispStatus("Please wait...");
								// down request

								t1.localDown(fl.name);
								t1.clear();
								//

								// list new files
								t1.add(t1.getLocalListing());
								//
								sl.replaceLight(Color.GREEN, 3);
								local_pwd.setText(t1.getLocalPWD());
								dispStatus("Done.");
							}

						}

					};

					new Thread(r).start();

				}

			}
		});
		local.setViewportView(local_table);

		JScrollPane remote = new JScrollPane();
		mainSplitPane.setRightComponent(remote);

		remote_table = new JTable();
		remote_table.setRowHeight(16);
		remote_table.setOpaque(false);
		remote_table.setDefaultRenderer(Object.class, new FileSystemRenderer());
		remote_table.setFillsViewportHeight(true);
		remote_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// REMOTE TABLE
				JTable source = (JTable) e.getSource();
				final int sourceRow = source.rowAtPoint(e.getPoint());
				if (sourceRow == -1) {
					return;
				}

				if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {// right click
					// select row
					if (!source.isRowSelected(sourceRow)) {
						source.changeSelection(sourceRow, 0, false, false);
					}

					final FileListing fl = t2.files.get(sourceRow);

					JPopupMenu popup = new JPopupMenu();

					JMenuItem download = new JMenuItem();
					JMenuItem delete = new JMenuItem();
					JMenuItem properties = new JMenuItem();

					download.setText("Download File");
					download.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// start download

							String rpath = fl.path + fl.name;
							String lpath = t1.getLocalPWD() + "/" + fl.name;

//							FileTransferConfirm ftc = new FileTransferConfirm(param);
//							ftc.setVisible(true);

						}

					});

					// delete
					delete.setText("Delete");
					delete.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							ClientCommands.filemanager_delete(c, fl.path + fl.name);
							// refresh listing
							t2.clear();
							try {
								t2.add(ClientCommands.filemanager_list(c));
							} catch (InvalidResponseException e1) {
								Logger.add("Failed to list files");
							}
							//
						}
					});

					// file properties
					properties.setText("File Properties");
					properties.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							HashMap<String, Object> info = ClientCommands.getFileInfo(c, fl.path + fl.name);
							FileProperties fp = new FileProperties(info);
							fp.setVisible(true);

						}
					});

					popup.add(download);
					popup.add(delete);
					popup.add(properties);
					popup.show(remote_table, e.getX(), e.getY());

				} else {
					Runnable r = new Runnable() {

						@Override
						public void run() {
							// normal click

							FileListing fl = t2.files.get(sourceRow);
							if (fl.dir) {
								sl.replaceLight(Color.YELLOW, 2);
								dispStatus("Please wait...");

								// down request
								ClientCommands.down(c, fl.name);
								t2.clear();

								try {
									remote_pwd.setText(ClientCommands.filemanager_pwd(c));
								} catch (InvalidResponseException e) {
									remote_pwd.setText("Failed");
								}

								// list new files
								try {
									t2.add(ClientCommands.filemanager_list(c));
								} catch (InvalidResponseException e) {
									Logger.add("Failed to list Files");
								}

								sl.replaceLight(Color.GREEN, 3);
								dispStatus("Done.");
							} else {
							}

						}

					};

					new Thread(r).start();

				}
			}
		});
		remote.setViewportView(remote_table);

		// create models
		t1 = new FileSystemModel();
		t2 = new FileSystemModel(c);

		local_table.setModel(t1);
		remote_table.setModel(t2);
		mainSplitPane.setDividerLocation(297);

		JPanel pwd_panel = new JPanel();
		jp.add(pwd_panel, BorderLayout.SOUTH);
		pwd_panel.setLayout(new BorderLayout(0, 0));

		pwdSplitPane = new JSplitPane();
		pwd_panel.add(pwdSplitPane, BorderLayout.CENTER);

		local_pwd = new JTextField();
		local_pwd.setEditable(false);
		local_pwd.setText(t1.getLocalPWD());
		pwdSplitPane.setLeftComponent(local_pwd);
		local_pwd.setColumns(10);

		remote_pwd = new JTextField();
		remote_pwd.setEditable(false);
		try {
			remote_pwd.setText(ClientCommands.filemanager_pwd(c));
		} catch (InvalidResponseException e1) {
			remote_pwd.setText("Failed...");
		}
		pwdSplitPane.setRightComponent(remote_pwd);
		remote_pwd.setColumns(10);
		pwdSplitPane.setDividerLocation(297);

		// define these at the end
		mainSplitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				// resize the other ones too
				int s = mainSplitPane.getDividerLocation();
				pwdSplitPane.setDividerLocation(s);
				toolbarSplitPane.setDividerLocation(s);

			}
		});
		pwdSplitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				// resize the other ones too
				int s = pwdSplitPane.getDividerLocation();
				mainSplitPane.setDividerLocation(s);
				toolbarSplitPane.setDividerLocation(s);

			}
		});
		toolbarSplitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				// resize the other ones too
				int s = toolbarSplitPane.getDividerLocation();
				pwdSplitPane.setDividerLocation(s);
				mainSplitPane.setDividerLocation(s);

			}
		});

	}

	public void dispStatus(String s) {
		lblStatusBar.setText(" " + s);
	}

	private void unfreeze() {
		remoteUP.setEnabled(true);
		localUP.setEnabled(true);

	}

	private void freeze() {
		remoteUP.setEnabled(false);
		localUP.setEnabled(false);

	}
}
