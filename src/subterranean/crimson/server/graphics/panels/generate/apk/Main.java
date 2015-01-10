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
package subterranean.crimson.server.graphics.panels.generate.apk;



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
import java.util.Date;
import java.util.Locale;

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
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import net.sourceforge.jcalendarbutton.JCalendarButton;
import subterranean.crimson.server.Server;
import subterranean.crimson.server.generation.APK;
import subterranean.crimson.server.graphics.GenerateAPK;
import subterranean.crimson.universal.Cryptography;
import subterranean.crimson.universal.EncType;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Path;
import subterranean.crimson.universal.Reporter;
import subterranean.crimson.universal.Version;
import subterranean.crimson.universal.containers.AddressSpec;
import subterranean.crimson.universal.containers.Options;
import subterranean.crimson.universal.containers.PortSpec;

import javax.swing.BoxLayout;

public class Main extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;

	private JTextField textField_output_path;
	private JTextField textField_output_name;
	private JTextField textField_server_address;
	private JTextField textField_server_port;
	private JCheckBox chckbxAutostart;
	private JButton btnRestoreLastValues;
	private JTextField client_name;
	private JPasswordField passwordField;
	private JLabel lblPassword;
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
	private JLabel lblStatus;
	private JCalendarButton payload_calender;
	private JPasswordField crimson_password;
	private JTextField crimson_username;
	private JPanel cards;
	private JComboBox comboBox;
	private JTextField crimson_port;
	private JCalendarButton installed_calender;
	private DateFormat df;
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

	private GenerateAPK parent;

	public Main(GenerateAPK p) {
		parent = p;
		setLayout(new BorderLayout(0, 0));
		setBounds(100, 100, 596, 340);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);

		if (Server.getSettings().isInformationPanels()) {
			JPanel information_panel = new JPanel();
			tabbedPane.addTab("Information", null, information_panel, null);
			information_panel.setLayout(null);

			JTextArea txtrTheJarPayload = new JTextArea();
			txtrTheJarPayload.setText("The APK Generator can generate an Android .apk file which can be used to installed the Crimson client on an Android device.");
			txtrTheJarPayload.setBackground(new Color(0, 0, 0, 0));
			txtrTheJarPayload.setOpaque(false);

			txtrTheJarPayload.setWrapStyleWord(true);
			txtrTheJarPayload.setLineWrap(true);
			txtrTheJarPayload.setEditable(false);
			txtrTheJarPayload.setBounds(12, 12, 458, 248);
			information_panel.add(txtrTheJarPayload);

		}

		// check for loaded settings settings file

		final Options savedValues = Server.getSettings().getSavedApkPayloadOptions();
		btnRestoreLastValues = new JButton("Restore Saved Values");
		lblStatus = new JLabel("status");

		boolean settings = savedValues.encryptionType == null ? false : true;
		if (settings) {
			btnRestoreLastValues.setEnabled(true);
			lblStatus.setText("Settings found!");
			lblStatus.setForeground(Color.GREEN);

		} else {
			btnRestoreLastValues.setEnabled(false);
			lblStatus.setText("No settings found.");
			lblStatus.setForeground(Color.GRAY);
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
		settings_panel.setLayout(new BoxLayout(settings_panel, BoxLayout.Y_AXIS));

		JPanel restore_panel = new JPanel();
		restore_panel.setBorder(new TitledBorder(null, "Saved Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		settings_panel.add(restore_panel);
		restore_panel.setLayout(null);

		btnRestoreLastValues.setFont(new Font("Dialog", Font.BOLD, 10));
		btnRestoreLastValues.setBounds(12, 29, 161, 25);
		restore_panel.add(btnRestoreLastValues);
		btnRestoreLastValues.setToolTipText("Restore fields with the last values used.");

		lblStatus.setBounds(191, 33, 265, 16);
		restore_panel.add(lblStatus);

		JPanel info_panel = new JPanel();
		info_panel.setBorder(new TitledBorder(null, "Optional Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		settings_panel.add(info_panel);
		info_panel.setLayout(null);

		JLabel lbloptionalVersionNumber = new JLabel("Client Name:");
		lbloptionalVersionNumber.setBounds(12, 30, 95, 15);
		info_panel.add(lbloptionalVersionNumber);
		lbloptionalVersionNumber.setToolTipText("");

		client_name = new JTextField();
		client_name.setText("CR");
		client_name.setBounds(125, 28, 114, 19);
		info_panel.add(client_name);
		client_name.setToolTipText("Payload version number.  May be in any format.");
		client_name.setColumns(10);

		JLabel lblJarCreationDate = new JLabel("Payload APK Creation Date:");
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
		payload_calender.setBounds(451, 57, 30, 20);
		info_panel.add(payload_calender);

		payload_jar_time_textField = new JTextField("[CURRENT]");
		payload_jar_time_textField.setEnabled(true);
		payload_jar_time_textField.setBounds(217, 56, 222, 20);
		info_panel.add(payload_jar_time_textField);
		payload_jar_time_textField.setColumns(10);

		JLabel lblInstalledJarCreation = new JLabel("Installed APK Creation Date:");
		lblInstalledJarCreation.setBounds(12, 85, 202, 16);
		info_panel.add(lblInstalledJarCreation);

		installed_jar_time_textField = new JTextField("[CURRENT]");
		installed_jar_time_textField.setBounds(217, 84, 222, 20);
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
		installed_calender.setBounds(451, 85, 30, 20);
		info_panel.add(installed_calender);

		JButton btnRandomizeName_1 = new JButton("Randomize");
		btnRandomizeName_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				client_name.setText(subterranean.crimson.universal.Utilities.nameGen(5));
			}
		});
		btnRandomizeName_1.setFont(new Font("Dialog", Font.BOLD, 9));
		btnRandomizeName_1.setBounds(251, 28, 88, 19);
		info_panel.add(btnRandomizeName_1);

		btnRestoreLastValues.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unused")
			@Override
			public void mouseClicked(MouseEvent e) {
				// restore values

				if (true || !savedValues.crimsonDNS) {// disable crimsonDNS for
														// now
					// traditional
					comboBox.setSelectedItem("Traditional");
					textField_server_address.setText(savedValues.primaryAddress.getLocator());
					textField_server_port.setText("" + savedValues.primaryPort.getPort());

				} else {
					// crimsonDNS
					comboBox.setSelectedItem("CrimsonDNS");
					crimson_username.setText(savedValues.crimsonDNS_user);
				}

				delay_field.setText("" + savedValues.executionDelay);
				chckbxInstallWhenUser.setSelected(savedValues.waitForIDLE);

				textField_backup_address.setText(savedValues.backupAddress.getLocator());
				textField_backup_port.setText("" + savedValues.backupPort.getAttempt());

				chckbxErrorHandling.setSelected(savedValues.handleErrors);

				lblStatus.setText("Restored Saved Values!");
				lblStatus.setForeground(Color.BLUE);

			}
		});

		JPanel execution_panel = new JPanel();
		tabbedPane.addTab("Execution", null, execution_panel, null);
		execution_panel.setLayout(new BorderLayout(0, 0));

		JTabbedPane executionPane = new JTabbedPane(JTabbedPane.LEFT);
		execution_panel.add(executionPane);

		JPanel execution_options_panel = new JPanel();
		executionPane.addTab("Options", null, execution_options_panel, null);
		execution_options_panel.setBorder(null);
		execution_options_panel.setLayout(null);

		chckbxAutostart = new JCheckBox("Autostart");
		chckbxAutostart.setEnabled(false);
		chckbxAutostart.setBounds(18, 91, 249, 23);
		execution_options_panel.add(chckbxAutostart);
		chckbxAutostart.setToolTipText("Whether to start on boot.");

		lblExecutionTimer = new JLabel("Execution Delay:");
		lblExecutionTimer.setBounds(8, 12, 124, 16);
		execution_options_panel.add(lblExecutionTimer);

		delay_field = new JTextField();
		delay_field.setToolTipText("How long to wait to install upon execution.");
		delay_field.setText("0");
		delay_field.setBounds(132, 9, 41, 20);
		execution_options_panel.add(delay_field);
		delay_field.setColumns(10);

		lblSeconds = new JLabel("seconds");
		lblSeconds.setBounds(176, 12, 63, 16);
		execution_options_panel.add(lblSeconds);

		chckbxInstallWhenUser = new JCheckBox("Install when host becomes IDLE");
		chckbxInstallWhenUser.setEnabled(false);
		chckbxInstallWhenUser.setToolTipText("Crimson will only install on an IDLE host");
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
		chckbxInstallWhenUser.setBounds(18, 36, 249, 24);
		execution_options_panel.add(chckbxInstallWhenUser);

		chckbxErrorHandling = new JCheckBox("Handle Errors");
		chckbxErrorHandling.setEnabled(false);
		chckbxErrorHandling.setToolTipText("If any stage of the installation fails, Crimson will automatically try to fix it.");
		chckbxErrorHandling.setBounds(18, 64, 249, 23);
		execution_options_panel.add(chckbxErrorHandling);

		JPanel networking_panel = new JPanel();
		tabbedPane.addTab("Networking", null, networking_panel, null);
		networking_panel.setLayout(null);

		JPanel encryption_panel = new JPanel();
		encryption_panel.setBorder(new TitledBorder(null, "Encryption", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		encryption_panel.setBounds(12, 193, 547, 68);
		networking_panel.add(encryption_panel);
		encryption_panel.setLayout(null);

		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(231, 28, 75, 15);
		encryption_panel.add(lblPassword);
		lblPassword.setEnabled(false);

		passwordField = new JPasswordField();
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

		JPanel com_panel = new JPanel();
		com_panel.setBorder(new TitledBorder(null, "Communications", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		com_panel.setBounds(12, 12, 547, 178);
		networking_panel.add(com_panel);
		com_panel.setLayout(null);

		cards = new JPanel();
		cards.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		cards.setBounds(12, 58, 523, 108);
		com_panel.add(cards);
		cards.setLayout(new CardLayout(0, 0));

		JPanel panel = new JPanel();
		cards.add(panel, "Traditional");
		panel.setLayout(null);

		JLabel lblCommandServerAddress = new JLabel("Primary Address:");
		lblCommandServerAddress.setBounds(12, 32, 140, 15);
		panel.add(lblCommandServerAddress);

		textField_server_address = new JTextField();
		textField_server_address.setBounds(156, 30, 235, 19);
		panel.add(textField_server_address);
		textField_server_address.setColumns(10);

		JLabel lblCommandServerPort = new JLabel("Port:");
		lblCommandServerPort.setBounds(409, 32, 36, 15);
		panel.add(lblCommandServerPort);

		textField_server_port = new JTextField();
		textField_server_port.setBounds(463, 30, 44, 19);
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

		JPanel generate_panel = new JPanel();
		tabbedPane.addTab("Generate", null, generate_panel, null);
		generate_panel.setLayout(null);

		btnGenerate = new JButton("GENERATE");
		btnGenerate.setEnabled(false);
		btnGenerate.setBounds(455, 236, 104, 25);
		btnGenerate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
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
						Logger.add("Failed to parse installed apk date");
						parent.addNotification("Failed to parse: \"" + installed_date + "\"; check format. Format: MMM dd, yyyy HH:mm a. Locale must be English as well.");

						return;
					}
				} else {
					Idate = new Date();

				}

				opts.encryptionType = encryption;

				opts.executionDelay = Long.parseLong(delay_field.getText());
				opts.waitForIDLE = chckbxInstallWhenUser.isSelected();

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

				Thread p = new APK(opts, Path.toUnix(textField_output_path.getText()), textField_output_name.getText(), Pdate.getTime());
				p.start();
				//
				parent.dispose();
			}
		});
		generate_panel.add(btnGenerate);

		JPanel output_panel = new JPanel();
		output_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Output", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		output_panel.setBounds(12, 12, 547, 79);
		generate_panel.add(output_panel);
		output_panel.setLayout(null);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setFont(new Font("Dialog", Font.BOLD, 10));
		btnBrowse.setBounds(460, 45, 75, 19);
		output_panel.add(btnBrowse);
		btnBrowse.setToolTipText("Opens a file chooser to select file.");

		JLabel lblOutputPath = new JLabel("Path:");
		lblOutputPath.setHorizontalAlignment(SwingConstants.TRAILING);
		lblOutputPath.setBounds(12, 20, 56, 15);
		output_panel.add(lblOutputPath);

		textField_output_path = new JTextField();
		textField_output_path.setBounds(86, 18, 449, 19);
		output_panel.add(textField_output_path);
		textField_output_path.setToolTipText("Identifies output directory. Does not contain the file name.");
		textField_output_path.setColumns(10);

		JLabel lblJarName = new JLabel("Name:");
		lblJarName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblJarName.setBounds(12, 47, 56, 15);
		output_panel.add(lblJarName);

		textField_output_name = new JTextField();
		textField_output_name.setBounds(86, 45, 114, 19);
		output_panel.add(textField_output_name);
		textField_output_name.setToolTipText("Name of the payload being generated.  If no extension is specified, the default will be used.");
		textField_output_name.setColumns(10);

		JButton btnRandomizeName = new JButton("Randomize");
		btnRandomizeName.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				textField_output_name.setText(subterranean.crimson.universal.Utilities.nameGen(5) + ".apk");

			}
		});
		btnRandomizeName.setFont(new Font("Dialog", Font.BOLD, 9));
		btnRandomizeName.setBounds(212, 45, 88, 19);
		output_panel.add(btnRandomizeName);

		chckbxIWillBe = new JCheckBox("I will use this APK to install Crimson only on devices that I have authorization to do so.");
		chckbxIWillBe.setFont(new Font("Dialog", Font.BOLD, 10));
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
		chckbxIWillBe.setBounds(4, 99, 555, 23);
		generate_panel.add(chckbxIWillBe);

		if (!Version.release) {
			textField_server_address.setText("10.0.2.2");
		}
		if (!Version.release) {
			textField_server_port.setText("2031");
		}

		if (!Version.release) {
			textField_output_path.setText("C:/Users/win/Desktop/");
		}
		if (!Version.release) {
			textField_output_name.setText("payload.apk");
		}

		btnBrowse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int returnVal = fc.showOpenDialog(Main.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					if (file.isDirectory()) {
						parent.dispose();
					}
					// add the file
					String filename = file.getName();
					if (!filename.endsWith(".apk")) {
						filename += ".apk";
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

	}

	public boolean validateFields(Options opts, Path outPATH) {
		try {
			// check essential options
			if (opts.encryptionType == null) {
				parent.addNotification("Encryption Type is NULL");

				return false;
			}

			if (outPATH.getAbsolutePath().isEmpty()) {
				parent.addNotification("Cannot have empty output path");

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

		Server.getSettings().setSavedApkPayloadOptions(opts);
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
}
