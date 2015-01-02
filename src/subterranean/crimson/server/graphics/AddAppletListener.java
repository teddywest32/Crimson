package subterranean.crimson.server.graphics;

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

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.graphics.frames.ListenerManager;
import subterranean.crimson.universal.PortSpec;
import subterranean.crimson.universal.Utilities;

public class AddAppletListener extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblPassword;
	private JCheckBox chckbxRememberThisListener;
	private JTextField port_textField;
	private JPanel cards;
	private JButton btnHelp;
	private JButton okButton;
	private String error_whitespace = "                                                ";
	private JLabel errorLabel;

	public AddAppletListener() {
		setPreferredSize(new Dimension(480, 270));
		setMaximumSize(new Dimension(480, 270));
		setBounds(new Rectangle(0, 0, 480, 270));
		setResizable(false);
		setTitle("Add an Applet Listener");
		setAlwaysOnTop(true);
		setBounds(100, 100, 479, 265);
		getContentPane().setLayout(null);

		cards = new JPanel();
		cards.setBounds(0, 0, 469, 199);
		getContentPane().add(cards);
		cards.setLayout(new CardLayout(0, 0));
		cards.add(contentPanel, "name_3036214224377");
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);

		JPanel stats_panel = new JPanel();
		stats_panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		stats_panel.setBounds(12, 12, 445, 58);
		contentPanel.add(stats_panel);
		stats_panel.setLayout(null);

		JLabel lblActiveAppletListeners = new JLabel("Active Applet Listeners:");
		lblActiveAppletListeners.setHorizontalAlignment(SwingConstants.TRAILING);
		lblActiveAppletListeners.setBounds(12, 6, 168, 15);
		stats_panel.add(lblActiveAppletListeners);

		JLabel label = new JLabel("" + Server.getAppletListenerNumber());
		label.setBounds(187, 6, 70, 15);
		stats_panel.add(label);

		JLabel lblCumulativeTrafficup = new JLabel("Cumulative Traffic[UP]:");
		lblCumulativeTrafficup.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCumulativeTrafficup.setBounds(12, 21, 168, 15);
		stats_panel.add(lblCumulativeTrafficup);

		JLabel lblNewLabel = new JLabel("Cumulative Traffic[DN]:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setBounds(12, 36, 168, 15);
		stats_panel.add(lblNewLabel);

		JLabel lblkb = new JLabel(Utilities.familiarize(Server.getAppletTrafficUP(), Utilities.BYTES));
		lblkb.setBounds(187, 21, 70, 15);
		stats_panel.add(lblkb);

		JLabel lblkb_1 = new JLabel(Utilities.familiarize(Server.getAppletTrafficDN(), Utilities.BYTES));
		lblkb_1.setBounds(187, 36, 70, 15);
		stats_panel.add(lblkb_1);

		JPanel info_panel = new JPanel();
		info_panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		info_panel.setBounds(12, 78, 445, 109);
		contentPanel.add(info_panel);
		info_panel.setLayout(null);

		JLabel lblName = new JLabel("Name:");
		lblName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblName.setBounds(132, 33, 58, 16);
		info_panel.add(lblName);

		textField = new JTextField();
		textField.setBounds(208, 32, 119, 20);
		info_panel.add(textField);
		textField.setColumns(10);

		port_textField = new JTextField();
		port_textField.setBounds(208, 58, 46, 20);
		info_panel.add(port_textField);
		port_textField.setColumns(10);

		JLabel lblAccessPort = new JLabel("Access Port:");
		lblAccessPort.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAccessPort.setBounds(94, 59, 96, 16);
		info_panel.add(lblAccessPort);

		passwordField = new JPasswordField();
		passwordField.setBounds(208, 83, 119, 20);
		info_panel.add(passwordField);

		lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPassword.setBounds(102, 84, 88, 16);
		info_panel.add(lblPassword);

		chckbxRememberThisListener = new JCheckBox("Remember this Listener");
		chckbxRememberThisListener.setBounds(132, 2, 211, 24);
		info_panel.add(chckbxRememberThisListener);
		chckbxRememberThisListener.setSelected(true);

		JButton btnView = new JButton("View");
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
		btnView.setBounds(339, 80, 68, 20);
		info_panel.add(btnView);

		JPanel help_panel = new JPanel();
		cards.add(help_panel, "name_3048860839424");
		help_panel.setLayout(null);

		JTextPane txtpnname = new JTextPane();
		txtpnname.setContentType("text/html");
		txtpnname.setText("<strong>Remember</strong> - If checked, the listener will be saved and loaded on startup\n<br>\n<strong>Name</strong> - Optionally, you may associate a text string with this listener.\n<br>\n<strong>Access Port</strong> - The port that will serve the applet to the browser. This port should be accessible from the location you wish to use the viewer.\n<br>\n<strong>Password</strong> - Password used to encrypt communications between the server and the applet\n<br>\n");
		txtpnname.setBackground(new Color(0, 0, 0, 0));
		txtpnname.setOpaque(false);

		txtpnname.setBounds(12, 12, 445, 175);
		help_panel.add(txtpnname);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 199, 469, 36);
			buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			getContentPane().add(buttonPane);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setBounds(10, 6, 81, 24);
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				buttonPane.setLayout(null);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			okButton = new JButton("ADD");
			okButton.setBounds(391, 6, 66, 24);
			okButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
			okButton.setPreferredSize(new Dimension(74, 26));
			okButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int accessPort = 0;
					try {
						accessPort = Integer.parseInt(port_textField.getText());
					} catch (NumberFormatException ex) {
						showError("Not a valid Port!");
						return;
					}
					String name = textField.getText();
					if (name.isEmpty()) {
						name = "Default Applet Listener";

					}

					SecretKeySpec key = null;
					if (!String.valueOf(passwordField.getPassword()).equals("")) {
						String passphrase = String.valueOf(passwordField.getPassword());
						MessageDigest digest;
						try {
							digest = MessageDigest.getInstance("SHA");
							digest.update(passphrase.getBytes());
							key = new SecretKeySpec(digest.digest(), 0, 16, "AES");

						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					} else {
						key = null;
					}

					Server.addAppletListener(name, chckbxRememberThisListener.isSelected(), key, accessPort);

					try {
						// update the table
						ListenerManager.tmodel.fireTableDataChanged();
					} catch (NullPointerException e1) {
						// There must not be a table to update; do nothing
					}

					dispose();
				}
			});

			btnHelp = new JButton("Help");// Careful when changing this
			btnHelp.setBounds(313, 6, 66, 24);
			btnHelp.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					CardLayout cardLayout = (CardLayout) cards.getLayout();
					cardLayout.next(cards);

					if (btnHelp.getText().equals("Help")) {
						btnHelp.setText("Back");
						okButton.setEnabled(false);

					} else {
						btnHelp.setText("Help");
						okButton.setEnabled(true);
					}

				}
			});

			errorLabel = new JLabel(error_whitespace);
			errorLabel.setBounds(96, 13, 205, 15);
			errorLabel.setForeground(Color.RED);
			errorLabel.setPreferredSize(new Dimension(220, 15));
			buttonPane.add(errorLabel);
			buttonPane.add(btnHelp);
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}

	}

	public void showError(String error) {
		errorLabel.setToolTipText(error);
		errorLabel.setText(error);

	}

	public boolean validate_input(String name, int port, String password) {
		PortSpec p = new PortSpec(port);

		if (password.length() < 5) {
			showError("Password too short!");
			return false;
		}
		if (password.length() > 64) {
			showError("Password too long!");
			return false;
		}
		if (!p.isValid()) {
			showError("Invalid port: " + port);
			return false;
		}
		if (!p.isOpen()) {
			showError("Cannot listen on port: " + port);
			return false;
		}
		if (name.length() > 64) {
			showError("Name too long!");
			return false;
		}

		return true;
	}

}
