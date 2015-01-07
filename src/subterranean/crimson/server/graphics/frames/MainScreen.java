package subterranean.crimson.server.graphics.frames;

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

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;

import sl.SLAnimator;
import sl.SLConfig;
import sl.SLKeyframe;
import sl.SLPanel;
import sl.SLSide;
import subterranean.crimson.server.Server;
import subterranean.crimson.server.ServerCommands;
import subterranean.crimson.server.audio.Sounds;
import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.EndUserLicenseAgreement;
import subterranean.crimson.server.graphics.GenerateAPK;
import subterranean.crimson.server.graphics.GenerateEXE;
import subterranean.crimson.server.graphics.GeneratePayload;
import subterranean.crimson.server.graphics.LoadingDialog;
import subterranean.crimson.server.graphics.Login;
import subterranean.crimson.server.graphics.Logs;
import subterranean.crimson.server.graphics.MarketMaintainence;
import subterranean.crimson.server.graphics.MovingPanel;
import subterranean.crimson.server.graphics.Notification;
import subterranean.crimson.server.graphics.PluginManager;
import subterranean.crimson.server.graphics.PrimordialCommandsPanel;
import subterranean.crimson.server.graphics.ProgressArea;
import subterranean.crimson.server.graphics.StatusLights;
import subterranean.crimson.server.graphics.panels.mainscreen.MainPane;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Version;
import subterranean.crimson.universal.exceptions.NoReplyException;
import subterranean.crimson.universal.translation.T;

public class MainScreen extends JFrame {

	public static MainScreen window;

	private static final long serialVersionUID = 1L;

	public SystemTray tray;

	// for animation
	private final SLConfig mainCfg, p1Cfg;
	public MovingPanel main;
	public MovingPanel note;

	public void updateTitle() {
		setTitle(T.t("misc-title") + " " + Server.connections.size());

	}

	public void addHost(Connection c) {
		Sounds.play("ping");

		// add to list
		mainPane.rootPanel.h.addHost(c);
		// add to graph
		mainPane.rootPanel.hp.addConnection(c);
		updateTitle();
		if (Server.getSettings().getNotePolicy().isConnection_unique()) {
			addNotification("Established Connection with New Client: " + c.getProfile().info.getUsername());
		} else if (Server.getSettings().getNotePolicy().isConnection_new()) {
			addNotification("Established Connection with Old Client: " + c.getProfile().info.getUsername());
		}

	}

	public void removeHost(Connection c) {
		// remove from list
		mainPane.rootPanel.h.removeHost(c);
		// remove from graph
		mainPane.rootPanel.hp.removeConnection(c);

		// TODO drop detail if needed

		// close cps
		for (ControlPanel cp : Server.controlPanels) {
			if (cp.c == c) {
				Logger.add("Closing control panel due to disconnection");
				cp.dispose();
			}
		}

		updateTitle();
		if (Server.getSettings().getNotePolicy().isConnection_lost()) {
			addNotification("Lost Client Connection: " + c.getProfile().info.getUsername());
		}

		// free closed cps
		Server.freeClosedCPs();

	}

	public boolean closeToTray() {
		// Check the SystemTray is supported
		if (!SystemTray.isSupported()) {
			Logger.add("SystemTray is not supported");
			return false;
		}

		Logger.add("Closing to tray");
		// close to tray
		this.setVisible(false);

		// Check if tray is already visible
		if (tray != null) {
			return true;
		}

		final PopupMenu popup = new PopupMenu();
		final TrayIcon trayIcon = new TrayIcon((new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/trayIcon.png"), "")).getImage());
		tray = SystemTray.getSystemTray();

		// Create a pop-up menu components
		MenuItem restoreItem = new MenuItem("Restore Crimson");
		restoreItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// restore Crimson
				MainScreen.window.setVisible(true);
				MainScreen.window.removeTray();

			}
		});

		MenuItem exitItem = new MenuItem("Exit Crimson");
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// exit
				exit();

			}
		});

		// Add components to pop-up menu

		popup.add(restoreItem);
		popup.add(exitItem);

		trayIcon.setPopupMenu(popup);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
			return false;
		}
		Logger.add("Crimson is now running in the system tray");
		return true;
	}

	public boolean removeTray() {
		tray.remove(new TrayIcon((new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/trayIcon.png"), "")).getImage()));
		return true;
	}

	@Override
	public void dispose() {
		if (Server.getSettings().isCloseOnTray()) {
			if (!closeToTray()) {
				exit();
			}
		} else {
			exit();
		}

	}

	public void exit() {
		// close the program
		Runnable r = new Runnable() {
			public void run() {
				// closing the program
				Logger.add("Mainscreen dispose() has been called");

				System.exit(0);
			}
		};
		new Thread(r).start();
		super.dispose();
	}

	public MainScreen() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/C-40.png")));
		updateTitle();
		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnListen = new JMenu(T.t("menu-listeners"));
		mnListen.setFont(new Font("Dialog", Font.BOLD, 11));
		menuBar.add(mnListen);

		JMenuItem mntmListenerManager = new JMenuItem(T.t("menu-listeners_manager"));
		mntmListenerManager.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmListenerManager.getText().charAt(0)).toUpperCase() + ".png")));
		mntmListenerManager.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ListenerManager lm = new ListenerManager();
				lm.setVisible(true);

			}
		});
		mnListen.add(mntmListenerManager);

		JMenu mntmQuickadd = new JMenu(T.t("menu-quick_add"));
		mntmQuickadd.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmQuickadd.getText().charAt(0)).toUpperCase() + ".png")));

		mnListen.add(mntmQuickadd);

		JMenuItem mntmClient = new JMenuItem(T.t("menu-client_listener"));
		mntmClient.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmClient.getText().charAt(0)).toUpperCase() + ".png")));
		mntmClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				AddClientListener acl = new AddClientListener();
				acl.setLocationRelativeTo(null);

				acl.setVisible(true);

			}
		});
		mntmQuickadd.add(mntmClient);

		JMenuItem mntmAppletListener = new JMenuItem(T.t("menu-applet_listener"));
		mntmAppletListener.setEnabled(false);
		mntmAppletListener.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmAppletListener.getText().charAt(0)).toUpperCase() + ".png")));
		mntmQuickadd.add(mntmAppletListener);

		JMenu mnGenerate = new JMenu(T.t("menu-generate"));
		mnGenerate.setFont(new Font("Dialog", Font.BOLD, 11));
		menuBar.add(mnGenerate);

		JMenu mnJar = new JMenu(T.t("menu-jar"));
		mnJar.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mnJar.getText().charAt(0)).toUpperCase() + ".png")));
		mnGenerate.add(mnJar);

		JMenuItem mntmJarInstaller = new JMenuItem(T.t("menu-installer"));
		mnJar.add(mntmJarInstaller);
		mntmJarInstaller.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmJarInstaller.getText().charAt(0)).toUpperCase() + ".png")));
		mntmJarInstaller.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// start jar generation process
				final GeneratePayload generateFrame = new GeneratePayload();
				generateFrame.setLocationRelativeTo(null);
				generateFrame.setVisible(true);

				new Thread(new Runnable() {
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						generateFrame.main.runAction();
					}
				}).start();
			}
		});

		JMenu mnExe = new JMenu(T.t("menu-exe"));
		mnExe.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mnExe.getText().charAt(0)).toUpperCase() + ".png")));
		mnGenerate.add(mnExe);

		JMenuItem mntmInstaller = new JMenuItem(T.t("menu-installer"));
		mntmInstaller.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmInstaller.getText().charAt(0)).toUpperCase() + ".png")));
		mntmInstaller.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// make sure plugin is installed

				if (Server.wp.isPurchased("adv") || !Version.release) {
					// start exe generation process
					final GenerateEXE generateFrame = new GenerateEXE();
					generateFrame.setLocationRelativeTo(null);
					generateFrame.setVisible(true);

					new Thread(new Runnable() {
						public void run() {
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							generateFrame.main.runAction();
						}
					}).start();
				} else {
					MainScreen.window.addNotification("This feature is available in Crimson Advanced Edition");
				}

			}
		});
		mnExe.add(mntmInstaller);

		JMenu mnApk = new JMenu(T.t("menu-apk"));
		mnApk.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mnApk.getText().charAt(0)).toUpperCase() + ".png")));
		mnGenerate.add(mnApk);

		JMenuItem mntmApk = new JMenuItem(T.t("menu-installer"));
		mnApk.add(mntmApk);
		mntmApk.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// make sure plugin is installed

				if (Server.wp.isPurchased("and") || !Version.release) {
					final GenerateAPK gapk = new GenerateAPK();
					gapk.setLocationRelativeTo(null);
					gapk.setVisible(true);
					new Thread(new Runnable() {
						public void run() {
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							gapk.main.runAction();
						}
					}).start();
				} else {
					MainScreen.window.addNotification("The APK generator requires the Official Mobile Plugin");
				}

			}
		});
		mntmApk.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmApk.getText().charAt(0)).toUpperCase() + ".png")));

		JMenu mnIpa = new JMenu(T.t("menu-ipa"));
		mnIpa.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mnIpa.getText().charAt(0)).toUpperCase() + ".png")));
		mnGenerate.add(mnIpa);

		JMenuItem mntmComingSoon_1 = new JMenuItem("Coming in a future release");
		mnIpa.add(mntmComingSoon_1);

		mnPlugins = new JMenu(T.t("menu-plugins"));
		mnPlugins.setFont(new Font("Dialog", Font.BOLD, 11));
		menuBar.add(mnPlugins);

		final JMenuItem mntmPluginManager = new JMenuItem(T.t("menu-marketplace"));
		mntmPluginManager.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/P.png")));
		mntmPluginManager.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!mntmPluginManager.isEnabled()) {
					return;
				}
				new Thread(new Runnable() {
					public void run() {
						LoadingDialog ld = new LoadingDialog(T.t("background-loading_marketplace"));
						ld.setLocationRelativeTo(null);
						ld.setVisible(true);
						// make sure crimson is the latest version
						try {
							if (!ServerCommands.latestVersion()) {
								MainScreen.window.addNotification(T.t("notification-update_required"));
								ld.dispose();
								return;
							}
						} catch (NoReplyException e1) {
							Logger.add("Marketplace is in maintainence");
							MarketMaintainence mm = new MarketMaintainence();
							mm.setVisible(true);
							return;
						}

						// get market info
						try {
							Server.marketInfo = ServerCommands.getMarketInfo();
						} catch (NoReplyException e) {
							MainScreen.window.addNotification(T.t("notification-market_connection_failed"));
							ld.dispose();
							return;
						}

						ld.dispose();

						if (Server.marketInfo == null) {
							MainScreen.window.addNotification(T.t("notification-market_connection_failed"));
							return;
						}

						if (Server.marketInfo.isMaintainenceMode()) {
							Logger.add("Marketplace is in maintainence");
							MarketMaintainence mm = new MarketMaintainence();
							mm.setVisible(true);
							return;
						}

						// check if logged in
						if (!Server.loggedIN) {
							// present user with login dialog
							Logger.add("Presenting login dialog");

							Login login = new Login();
							login.setLocationRelativeTo(null);
							login.setVisible(true);
						} else {
							Logger.add("Already logged in");
							PluginManager pm = new PluginManager();
							pm.setVisible(true);
							pm.setLocationRelativeTo(null);

						}
					}
				}).start();

			}
		});
		mnPlugins.add(mntmPluginManager);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(200, 0, 0));
		mnPlugins.add(separator);

		JMenu mnInterface = new JMenu(T.t("menu-interface"));
		mnInterface.setFont(new Font("Dialog", Font.BOLD, 11));
		menuBar.add(mnInterface);

		JMenu mnView = new JMenu(T.t("menu-view"));
		mnView.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mnView.getText().charAt(0)).toUpperCase() + ".png")));
		mnInterface.add(mnView);

		JRadioButton rdbtnHostList = new JRadioButton(T.t("menu-host_list"));
		rdbtnHostList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// switch to host list
				MainScreen.window.mainPane.rootPanel.listPane();
			}
		});
		rdbtnHostList.setSelected(true);
		mnView.add(rdbtnHostList);

		JRadioButton rdbtnHostMap = new JRadioButton(T.t("menu-host_map"));
		rdbtnHostMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// switch to host map
				MainScreen.window.mainPane.rootPanel.graphPane();
			}
		});
		mnView.add(rdbtnHostMap);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnHostMap);
		bg.add(rdbtnHostList);

		JMenuItem mntmRefresh = new JMenuItem(T.t("menu-refresh"));
		mntmRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainScreen.window.repaint();
			}
		});

		mntmRefresh.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmRefresh.getText().charAt(0)).toUpperCase() + ".png")));
		mnInterface.add(mntmRefresh);

		JMenuItem mntmCloseToTray = new JMenuItem(T.t("menu-close_to_tray"));
		mntmCloseToTray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!closeToTray()) {
					addNotification(T.t("notification-tray_unsupported"));
				}
			}
		});
		mntmCloseToTray.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmCloseToTray.getText().charAt(0)).toUpperCase() + ".png")));
		mnInterface.add(mntmCloseToTray);

		JMenu mnSettings = new JMenu(T.t("menu-miscellaneous"));
		mnSettings.setFont(new Font("Dialog", Font.BOLD, 11));
		menuBar.add(mnSettings);

		JMenuItem mntmLocalSettings = new JMenuItem(T.t("menu-settings"));
		mntmLocalSettings.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmLocalSettings.getText().charAt(0)).toUpperCase() + ".png")));
		mntmLocalSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SettingsWindow settings = new SettingsWindow();
				settings.setVisible(true);

			}
		});
		mnSettings.add(mntmLocalSettings);

		JMenuItem mntmPrimordialCommands = new JMenuItem(T.t("menu-state_commands"));
		mntmPrimordialCommands.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmPrimordialCommands.getText().charAt(0)).toUpperCase() + ".png")));
		mntmPrimordialCommands.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				if (false) {
					if (Server.wp.isPurchased("adv") || !Version.release) {
						// pull up the pcc panel
						PrimordialCommandsPanel pcp = new PrimordialCommandsPanel();
						pcp.setLocationRelativeTo(null);
						pcp.setVisible(true);
					} else {
						MainScreen.window.addNotification("This feature is available in Crimson Advanced Edition");
					}
				} else {
					addNotification("Under Construction");
				}

			}
		});
		mnSettings.add(mntmPrimordialCommands);

		JMenuItem mntmHistory = new JMenuItem(T.t("menu-history"));
		mntmHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNotification("Under Construction");
			}
		});
		mntmHistory.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmHistory.getText().charAt(0)).toUpperCase() + ".png")));
		mnSettings.add(mntmHistory);

		JMenuItem mntmBroadcastControlPanel = new JMenuItem("Broadcast Control Panel");
		mntmBroadcastControlPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (false) {
					if (Server.wp.isPurchased("adv") || !Version.release) {

					} else {
						MainScreen.window.addNotification("This feature is available in Crimson Advanced Edition");
					}
				} else {
					addNotification("Under Construction");
				}
			}
		});
		mntmBroadcastControlPanel.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmBroadcastControlPanel.getText().charAt(0)).toUpperCase() + ".png")));
		mnSettings.add(mntmBroadcastControlPanel);

		JMenu mnAbout = new JMenu("About");
		mnAbout.setFont(new Font("Dialog", Font.BOLD, 11));
		menuBar.add(mnAbout);

		JMenuItem mntmAboutCrimsonRat = new JMenuItem("About Crimson " + Version.version);
		mntmAboutCrimsonRat.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmAboutCrimsonRat.getText().charAt(0)).toUpperCase() + ".png")));
		mntmAboutCrimsonRat.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// display about
				About about = new About();
				about.setVisible(true);

			}
		});
		mnAbout.add(mntmAboutCrimsonRat);

		JMenuItem mntmEula = new JMenuItem("Show EULA");
		mntmEula.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmEula.getText().charAt(0)).toUpperCase() + ".png")));
		mntmEula.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				EndUserLicenseAgreement eula = new EndUserLicenseAgreement(Version.version, false);
				eula.setVisible(true);
			}
		});

		JMenuItem mntmLog = new JMenuItem(T.t("menu-log"));
		mntmLog.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmLog.getText().charAt(0)).toUpperCase() + ".png")));
		mntmLog.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new Logs().setVisible(true);

			}
		});
		mnAbout.add(mntmLog);
		mnAbout.add(mntmEula);

		JMenuItem mntmLegal = new JMenuItem(T.t("menu-legal"));
		mntmLegal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNotification("Under Construction");
			}
		});
		mntmLegal.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmLegal.getText().charAt(0)).toUpperCase() + ".png")));
		mnAbout.add(mntmLegal);

		JMenuItem mntmHelpCenter = new JMenuItem(T.t("menu-help_center"));
		mntmHelpCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNotification("Under Construction");
			}
		});
		mntmHelpCenter.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/" + ("" + mntmHelpCenter.getText().charAt(0)).toUpperCase() + ".png")));
		mnAbout.add(mntmHelpCenter);

		progressArea = new ProgressArea();
		menuBar.add(progressArea);

		stl = new StatusLights();
		stl.setToolTipText("Network Usage Indicators; TOP - outgoing, BOTTOM - incoming");
		stl.setPreferredSize(new Dimension(7, 20));
		stl.setBounds(0, 0, 7, 20);
		menuBar.add(stl);

		setBounds(new Rectangle(0, 0, 695, 350));
		setPreferredSize(new Dimension(695, 350));
		setMinimumSize(new Dimension(695, 350));

		setBounds(100, 100, 695, 350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Notification notification = new Notification(T.t("notification-welcome") + " " + Version.version);

		//
		mainPane = new MainPane();
		note = new MovingPanel(notification);
		main = new MovingPanel(mainPane);

		slPanel = new SLPanel();
		getContentPane().add(slPanel, BorderLayout.CENTER);

		// animations
		main.setAction(p1Action);

		mainCfg = new SLConfig(slPanel).gap(0, 0).row(6f).row(1f).col(1f).place(0, 0, main).place(1, 0, note);

		p1Cfg = new SLConfig(slPanel).gap(0, 0).row(2f).col(1f).place(0, 0, main);

		slPanel.setTweenManager(SLAnimator.createTweenManager());
		slPanel.initialize(mainCfg);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// close the detail on resize
				mainPane.dropDetail();

			}
		});
	}

	public MainPane mainPane;

	private final Runnable p1Action = new Runnable() {
		@Override
		public void run() {

			slPanel.createTransition().push(new SLKeyframe(p1Cfg, 0.8f).setEndSide(SLSide.BOTTOM, note).setCallback(new SLKeyframe.Callback() {
				@Override
				public void done() {
					main.setAction(p1BackAction);
					main.enableAction();
				}
			})).play();
		}
	};

	private final Runnable p1BackAction = new Runnable() {
		@Override
		public void run() {

			slPanel.createTransition().push(new SLKeyframe(mainCfg, 0.8f).setStartSide(SLSide.BOTTOM, note).setCallback(new SLKeyframe.Callback() {
				@Override
				public void done() {
					main.setAction(p1Action);

				}
			})).play();
		}
	};

	private SLPanel slPanel;

	public ProgressArea progressArea;

	private StatusLights stl;

	private JMenu mnPlugins;

	public void addNotification(final String s) {

		Runnable def = new Runnable() {
			public void run() {
				// default action
			}

		};
		addNotification(s, def, "info");

	}

	private synchronized void displayNotification(String string, Runnable r, String type) {
		if (!Server.getSettings().getNotePolicy().isShowingNotes()) {
			return;
		}

		Notification n = new Notification(string, r, type);

		note.removeAll();
		note.add(n);
		// move the note panel up
		main.runAction();

		try {
			Thread.sleep(5500);

		} catch (InterruptedException e) {

		} finally {
			// move the note back down
			main.runAction();
			try {
				Thread.sleep(900);
			} catch (InterruptedException e) {

			}
		}

	}

	public void updateHeaders() {
		mainPane.rootPanel.h.columnNames = Server.getSettings().getListHeaders();
		mainPane.rootPanel.h.fireTableStructureChanged();

	}

	public void addNotification(final String string, final Runnable r, final String type) {
		Runnable rr = new Runnable() {
			public void run() {
				displayNotification(string, r, type);

			}
		};
		new Thread(rr).start();
	}

	public synchronized void blinkSTL(Color c, byte i) {
		switch (i) {
		case 1: {
			stl.blink(c, 1);
			return;
		}
		case 2: {
			stl.blink(c, 2);
			return;
		}
		case 3: {
			stl.blink(c, 3);
			return;
		}
		default: {

		}
		}

	}

	public void addPluginMenu(JMenu mainMenu) {
		mnPlugins.add(mainMenu);

	}
}
