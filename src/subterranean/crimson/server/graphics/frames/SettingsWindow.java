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
package subterranean.crimson.server.graphics.frames;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.containers.PluginEntry;
import subterranean.crimson.server.containers.ServerSettings;
import subterranean.crimson.server.graphics.panels.settings.SPDModules;
import subterranean.crimson.server.sdk.interfaces.SPFrame;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.translation.T;

public class SettingsWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JCheckBox chckbxReportData;
	private JCheckBox chckbxShowInformationPanels;

	private JCheckBox chckbxCountry;
	private JCheckBox chckbxHostname;
	private JCheckBox chckbxVersion;
	private JCheckBox chckbxUserName;
	private JCheckBox chckbxHostStatus;
	private JCheckBox chckbxIpAddress;
	private JCheckBox chckbxOperatingSystem;
	private JCheckBox chckbxSentData;
	private JCheckBox chckbxReceivedData;
	private JCheckBox chckbxPrivileges;
	private JCheckBox chckbxJavaVersion;
	private JCheckBox chckbxExternalIpAddress;
	private JCheckBox chckbxUptime;
	private JPanel graphics_panel;
	private JComboBox comboBox;
	private JComboBox themeBox;
	private JCheckBox chckbxInformWhenForeign;
	private JCheckBox chckbxInformWhenA;
	private JCheckBox chckbxEULA;
	private JPanel policy_panel;
	private JPanel plugins_panel;
	private JTabbedPane plugin_tabbedPane;
	private JCheckBox chckbxNewConnection;
	private JCheckBox chckbxLostConnection;
	private JCheckBox chckbxCloseOnSystem;
	private JCheckBox chckbxNeverShowNotifications;
	private JPanel dmodules_panel;
	private JPanel button_panel;
	private JSeparator separator;
	private JCheckBox chckbxUniqueConnection;
	private JComboBox lang_box;
	private JCheckBox chckbxUpSpeed;
	private JCheckBox chckbxDownSpeed;
	private JCheckBox chckbxCurrentProcess;
	private JCheckBox chckbxPhoneNumber;
	private JCheckBox chckbxIMEI;
	private JCheckBox chckbxEncoding;
	private JCheckBox chckbxOperator;
	private JCheckBox chckbxSIMSerial;
	private JCheckBox chckbxSIMCode;
	private JCheckBox chckbxLanguage;
	private JCheckBox chckbxTimezone;
	private JCheckBox chckbxClientId;
	private SPDModules spdmodules;

	public SettingsWindow() {

		setIconImage(Toolkit.getDefaultToolkit().getImage(SettingsWindow.class.getResource("/subterranean/crimson/server/graphics/icons/icon.png")));

		setTitle("Settings");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 586, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel general_panel = new JPanel();
		tabbedPane.addTab("General", null, general_panel, null);
		general_panel.setLayout(null);

		JPanel settings_panel = new JPanel();
		settings_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		settings_panel.setBounds(12, 12, 547, 186);
		general_panel.add(settings_panel);
		settings_panel.setLayout(null);

		chckbxShowInformationPanels = new JCheckBox("Show Help Panels");
		chckbxShowInformationPanels.setSelected(true);
		chckbxShowInformationPanels.setBounds(18, 104, 195, 24);
		settings_panel.add(chckbxShowInformationPanels);

		chckbxEULA = new JCheckBox("Always Show EULA");
		chckbxEULA.setBounds(18, 78, 195, 23);
		chckbxEULA.setSelected(Server.getSettings().isShowEULA());
		settings_panel.add(chckbxEULA);

		JLabel lblLanguage = new JLabel("Language:");
		lblLanguage.setBounds(18, 51, 87, 15);
		settings_panel.add(lblLanguage);

		lang_box = new JComboBox();
		lang_box.setEnabled(false);
		lang_box.setModel(new DefaultComboBoxModel(new String[] { "English", "Deutsch", "Español" }));
		lang_box.setEditable(false);
		lang_box.setBounds(104, 46, 109, 24);
		lang_box.setSelectedItem(Server.getSettings().getLang());
		settings_panel.add(lang_box);

		chckbxCloseOnSystem = new JCheckBox("Always Close on System Tray");
		chckbxCloseOnSystem.setSelected(Server.getSettings().isCloseOnTray());
		chckbxCloseOnSystem.setBounds(18, 132, 238, 23);
		settings_panel.add(chckbxCloseOnSystem);

		chckbxNeverShowNotifications = new JCheckBox("Never Show Notifications");
		chckbxNeverShowNotifications.setSelected(!Server.getSettings().getNotePolicy().isShowingNotes());
		chckbxNeverShowNotifications.setBounds(255, 77, 203, 24);
		settings_panel.add(chckbxNeverShowNotifications);

		JPanel error_panel = new JPanel();
		error_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Error Reporting", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		error_panel.setBounds(12, 210, 547, 65);
		general_panel.add(error_panel);
		error_panel.setLayout(null);

		chckbxReportData = new JCheckBox("Report Data");
		chckbxReportData.setSelected(Server.getSettings().isErrorReporting());
		chckbxReportData.setBounds(8, 22, 112, 24);
		error_panel.add(chckbxReportData);

		JLabel lblReportsSent = new JLabel("Reports Sent:");
		lblReportsSent.setBounds(384, 26, 106, 16);
		error_panel.add(lblReportsSent);

		JLabel reports_sent = new JLabel("" + Server.getSettings().getErrorReportsSent());
		reports_sent.setBounds(502, 26, 33, 16);
		error_panel.add(reports_sent);

		policy_panel = new JPanel();
		policy_panel.setEnabled(false);
		tabbedPane.addTab("Policy", null, policy_panel, null);
		policy_panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Connection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 12, 547, 128);
		policy_panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblWhenAClient = new JLabel("When a Client Disconnects");
		lblWhenAClient.setBounds(12, 47, 207, 15);
		panel_1.add(lblWhenAClient);

		JRadioButton rdbtnRemoveHostFrom = new JRadioButton("Remove Host from lists and close control panels.");
		rdbtnRemoveHostFrom.setEnabled(false);
		rdbtnRemoveHostFrom.setSelected(true);
		rdbtnRemoveHostFrom.setBounds(22, 70, 513, 23);
		panel_1.add(rdbtnRemoveHostFrom);

		JRadioButton rdbtnDontRemoveHost = new JRadioButton("Dont remove host from lists");
		rdbtnDontRemoveHost.setEnabled(false);
		rdbtnDontRemoveHost.setBounds(22, 97, 513, 23);
		panel_1.add(rdbtnDontRemoveHost);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Inform On:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(12, 152, 547, 129);
		policy_panel.add(panel_2);
		panel_2.setLayout(null);

		chckbxInformWhenForeign = new JCheckBox("Foreign Connection Attempt");
		chckbxInformWhenForeign.setBounds(8, 20, 232, 23);
		panel_2.add(chckbxInformWhenForeign);
		chckbxInformWhenForeign.setSelected(Server.getSettings().getNotePolicy().isForeign_connection());

		chckbxInformWhenA = new JCheckBox("Crimson Update available");
		chckbxInformWhenA.setBounds(8, 48, 287, 23);
		panel_2.add(chckbxInformWhenA);
		chckbxInformWhenA.setSelected(Server.getSettings().getNotePolicy().isInform_on_update());

		chckbxNewConnection = new JCheckBox("New Connection");
		chckbxNewConnection.setSelected(Server.getSettings().getNotePolicy().isConnection_new());
		chckbxNewConnection.setBounds(8, 75, 232, 23);
		panel_2.add(chckbxNewConnection);

		chckbxLostConnection = new JCheckBox("Lost Connection");
		chckbxLostConnection.setSelected(Server.getSettings().getNotePolicy().isConnection_lost());
		chckbxLostConnection.setBounds(8, 102, 232, 23);
		panel_2.add(chckbxLostConnection);

		chckbxUniqueConnection = new JCheckBox("Unique Connection");
		chckbxUniqueConnection.setSelected(Server.getSettings().getNotePolicy().isConnection_unique());
		chckbxUniqueConnection.setBounds(284, 20, 255, 23);
		panel_2.add(chckbxUniqueConnection);

		JPanel list_panel = new JPanel();
		tabbedPane.addTab("Host List", null, list_panel, null);
		list_panel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Headers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		list_panel.add(panel);
		panel.setLayout(new GridLayout(10, 3, 0, 0));

		chckbxCountry = new JCheckBox("Location");
		chckbxCountry.setSelected(settingsContainHeader("Location"));
		panel.add(chckbxCountry);

		chckbxSentData = new JCheckBox("Sent Data");
		chckbxSentData.setSelected(settingsContainHeader("Sent Data"));
		panel.add(chckbxSentData);

		chckbxReceivedData = new JCheckBox("Received Data");
		chckbxReceivedData.setSelected(settingsContainHeader("Received Data"));
		panel.add(chckbxReceivedData);

		chckbxHostname = new JCheckBox("Hostname");
		chckbxHostname.setSelected(settingsContainHeader("Hostname"));
		panel.add(chckbxHostname);

		chckbxVersion = new JCheckBox("Client Version");
		chckbxVersion.setSelected(settingsContainHeader("Client Version"));
		panel.add(chckbxVersion);

		chckbxPrivileges = new JCheckBox("Privileges");
		chckbxPrivileges.setSelected(settingsContainHeader("Privileges"));
		panel.add(chckbxPrivileges);

		chckbxUserName = new JCheckBox("Username");
		chckbxUserName.setSelected(settingsContainHeader("Username"));
		panel.add(chckbxUserName);

		chckbxJavaVersion = new JCheckBox("Java Version");
		chckbxJavaVersion.setSelected(settingsContainHeader("Java Version"));
		panel.add(chckbxJavaVersion);

		chckbxHostStatus = new JCheckBox("Activity Status");
		chckbxHostStatus.setSelected(settingsContainHeader("Activity Status"));
		panel.add(chckbxHostStatus);

		chckbxUptime = new JCheckBox("System Uptime");
		chckbxUptime.setSelected(settingsContainHeader("System Uptime"));
		panel.add(chckbxUptime);

		chckbxIpAddress = new JCheckBox("Internal IP Address");
		chckbxIpAddress.setSelected(settingsContainHeader("Internal IP"));
		panel.add(chckbxIpAddress);

		chckbxExternalIpAddress = new JCheckBox("External IP Address");
		chckbxExternalIpAddress.setSelected(settingsContainHeader("External IP"));
		panel.add(chckbxExternalIpAddress);

		chckbxCurrentProcess = new JCheckBox("Current Process");
		chckbxCurrentProcess.setSelected(settingsContainHeader("Current Process"));
		panel.add(chckbxCurrentProcess);

		chckbxPhoneNumber = new JCheckBox("Phone Number");
		chckbxPhoneNumber.setSelected(settingsContainHeader("Phone Number"));
		panel.add(chckbxPhoneNumber);

		chckbxIMEI = new JCheckBox("IMEI");
		chckbxIMEI.setSelected(settingsContainHeader("IMEI"));
		panel.add(chckbxIMEI);

		chckbxOperator = new JCheckBox("Operator");
		chckbxOperator.setSelected(settingsContainHeader("Operator"));
		panel.add(chckbxOperator);

		chckbxSIMSerial = new JCheckBox("SIM Serial");
		chckbxSIMSerial.setSelected(settingsContainHeader("SIM Serial"));
		panel.add(chckbxSIMSerial);

		chckbxSIMCode = new JCheckBox("SIM Code");
		chckbxSIMCode.setSelected(settingsContainHeader("SIM Code"));
		panel.add(chckbxSIMCode);

		chckbxDownSpeed = new JCheckBox("Down Speed");
		chckbxDownSpeed.setSelected(settingsContainHeader("Down Speed"));
		panel.add(chckbxDownSpeed);

		chckbxUpSpeed = new JCheckBox("Up Speed");
		chckbxUpSpeed.setSelected(settingsContainHeader("Up Speed"));
		panel.add(chckbxUpSpeed);

		chckbxOperatingSystem = new JCheckBox("Operating System");
		chckbxOperatingSystem.setSelected(settingsContainHeader("Operating System"));
		panel.add(chckbxOperatingSystem);

		chckbxEncoding = new JCheckBox("Encoding");
		chckbxEncoding.setSelected(settingsContainHeader("Encoding"));
		panel.add(chckbxEncoding);

		chckbxLanguage = new JCheckBox("Language");
		chckbxLanguage.setSelected(settingsContainHeader("Language"));
		panel.add(chckbxLanguage);

		chckbxTimezone = new JCheckBox("Timezone");
		chckbxTimezone.setSelected(settingsContainHeader("Timezone"));
		panel.add(chckbxTimezone);

		chckbxClientId = new JCheckBox("Client ID");
		chckbxClientId.setSelected(settingsContainHeader("Client ID"));
		panel.add(chckbxClientId);

		JPanel graph_panel = new JPanel();
		tabbedPane.addTab("Host Graph", null, graph_panel, null);
		graph_panel.setLayout(null);

		graphics_panel = new JPanel();
		graphics_panel.setBorder(new TitledBorder(null, "Graphic", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		graphics_panel.setBounds(12, 12, 547, 99);
		graph_panel.add(graphics_panel);
		graphics_panel.setLayout(null);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Country", "Hostname", "Version", "Host Status", "External IP", "Operating System", "Sent", "Received", "Java Version", "Priveleges", "Internal IP" }));
		comboBox.setBounds(78, 16, 107, 24);
		comboBox.setSelectedItem(Server.getSettings().getGraphText());
		graphics_panel.add(comboBox);

		JLabel lblText = new JLabel("Text:");
		lblText.setBounds(12, 21, 48, 15);
		graphics_panel.add(lblText);

		plugins_panel = new JPanel();
		tabbedPane.addTab("Plugins", null, plugins_panel, null);
		plugins_panel.setLayout(new BorderLayout(0, 0));

		plugin_tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

		plugins_panel.add(plugin_tabbedPane, BorderLayout.CENTER);

		dmodules_panel = new JPanel();
		tabbedPane.addTab("DModules", null, dmodules_panel, null);
		dmodules_panel.setLayout(new BoxLayout(dmodules_panel, BoxLayout.Y_AXIS));

		spdmodules = new SPDModules();
		dmodules_panel.add(spdmodules);

		button_panel = new JPanel();
		contentPane.add(button_panel, BorderLayout.SOUTH);

		JButton btnClearSettings = new JButton("Reset Settings");
		button_panel.add(btnClearSettings);
		btnClearSettings.setFont(new Font("Dialog", Font.BOLD, 10));

		separator = new JSeparator();
		separator.setPreferredSize(new Dimension(270, 0));
		button_panel.add(separator);

		JButton btnCancel = new JButton("Cancel");
		button_panel.add(btnCancel);

		JButton btnSave = new JButton("Save");
		button_panel.add(btnSave);
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				spdmodules.save();

				Server.getSettings().setErrorReporting(chckbxReportData.isSelected());
				Server.getSettings().setInformationPanels(chckbxShowInformationPanels.isSelected());
				Server.getSettings().setTheme((String) themeBox.getSelectedItem());
				Server.getSettings().getNotePolicy().setForeign_connection(chckbxInformWhenForeign.isSelected());
				Server.getSettings().getNotePolicy().setConnection_unique(chckbxUniqueConnection.isSelected());
				Server.getSettings().getNotePolicy().setInform_on_update(chckbxInformWhenA.isSelected());
				Server.getSettings().setShowEULA(chckbxEULA.isSelected());
				Server.getSettings().getNotePolicy().setConnection_new(chckbxNewConnection.isSelected());
				Server.getSettings().getNotePolicy().setConnection_lost(chckbxLostConnection.isSelected());
				Server.getSettings().setCloseOnTray(chckbxCloseOnSystem.isSelected());
				Server.getSettings().getNotePolicy().setShowNotes(!chckbxNeverShowNotifications.isSelected());

				// reload the list headers
				ArrayList<String> headers = new ArrayList<String>();
				if (chckbxCountry.isSelected()) {
					headers.add("Location");
				}
				if (chckbxHostname.isSelected()) {
					headers.add("Hostname");
				}
				if (chckbxVersion.isSelected()) {
					headers.add("Client Version");
				}
				if (chckbxUserName.isSelected()) {
					headers.add("Username");
				}
				if (chckbxHostStatus.isSelected()) {
					headers.add("Activity Status");
				}
				if (chckbxIpAddress.isSelected()) {
					headers.add("Internal IP");
				}
				if (chckbxOperatingSystem.isSelected()) {
					headers.add("Operating System");
				}
				if (chckbxSentData.isSelected()) {
					headers.add("Sent Data");
				}
				if (chckbxReceivedData.isSelected()) {
					headers.add("Received Data");
				}
				if (chckbxPrivileges.isSelected()) {
					headers.add("Privileges");
				}
				if (chckbxJavaVersion.isSelected()) {
					headers.add("Java Version");
				}
				if (chckbxExternalIpAddress.isSelected()) {
					headers.add("External IP");
				}
				if (chckbxUptime.isSelected()) {
					headers.add("System Uptime");
				}
				if (chckbxUpSpeed.isSelected()) {
					headers.add("Up Speed");
				}
				if (chckbxDownSpeed.isSelected()) {
					headers.add("Down Speed");
				}
				if (chckbxCurrentProcess.isSelected()) {
					headers.add("Current Process");
				}
				if (chckbxEncoding.isSelected()) {
					headers.add("Encoding");
				}
				if (chckbxLanguage.isSelected()) {
					headers.add("Language");
				}
				if (chckbxTimezone.isSelected()) {
					headers.add("Timezone");
				}
				if (chckbxPhoneNumber.isSelected()) {
					headers.add("Phone Number");
				}
				if (chckbxIMEI.isSelected()) {
					headers.add("IMEI");
				}
				if (chckbxOperator.isSelected()) {
					headers.add("Operator");
				}
				if (chckbxSIMSerial.isSelected()) {
					headers.add("SIM Serial");
				}
				if (chckbxSIMCode.isSelected()) {
					headers.add("SIM Code");
				}
				if (chckbxClientId.isSelected()) {
					headers.add("Client ID");
				}

				String[] h = new String[headers.size()];
				for (int i = 0; i < h.length; i++) {
					h[i] = headers.get(i);
				}

				Server.getSettings().setListHeaders(h);
				MainScreen.window.updateHeaders();

				SwingUtilities.updateComponentTreeUI(MainScreen.window);

				String lang = (String) lang_box.getSelectedItem();
				if (!Server.getSettings().getLang().equals(lang)) {
					// language change
					Server.getSettings().setLang(lang);
					T.loadTranslation(Server.getSettings().getLang());
					MainScreen.window.addNotification(T.t("notification-language_change"));
				}

				dispose();
			}
		});
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnClearSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// retain a few settings
				int infoID = Server.getSettings().getInfoId();

				ServerSettings newSettings = new ServerSettings();
				newSettings.setInfoId(infoID);

				Server.database.storeObject(newSettings, (short) 1);
				dispose();
			}
		});

	}

	public boolean settingsContainHeader(String s) {
		for (String t : Server.getSettings().getListHeaders()) {
			if (t.equals(s)) {
				return true;
			}
		}
		return false;

	}

	public void update_LF() {
		SwingUtilities.updateComponentTreeUI(this);
	}
}
