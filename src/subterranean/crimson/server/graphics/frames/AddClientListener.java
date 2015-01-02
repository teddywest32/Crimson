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

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.WebserverCommands;
import subterranean.crimson.server.graphics.StatusLights;
import subterranean.crimson.server.graphics.graphs.NetworkLineGraph;
import subterranean.crimson.server.network.ClientListener;
import subterranean.crimson.server.network.ListenerContainer;
import subterranean.crimson.universal.CompressionLevel;
import subterranean.crimson.universal.Cryptography;
import subterranean.crimson.universal.EncType;
import subterranean.crimson.universal.PortSpec;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.objects.InvalidObjectException;
import subterranean.crimson.universal.objects.ObjectTransfer;
import subterranean.crimson.universal.upnp.PortMapper;
import java.awt.Insets;

public class AddClientListener extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel main_panel = new JPanel();
	private JTextField listener_name;
	private JPasswordField passwordField;
	private JCheckBox chckbxRememberThisListener;
	private JTextField port;
	private JPanel cards;
	private JButton btnHelp;
	private JButton okButton;
	private JLabel messageLabel;
	private JCheckBox chckbxUpnp;
	private JComboBox algorithm;
	private JButton btnView;
	private StatusLights portLights;
	private StatusLights upnpLights;
	private JButton btnCheckPort;
	private JButton btnCheckUpnp;
	private JLabel upnpStatus;
	private JLabel portStatus;
	private JComboBox op_mode;
	private JLabel lblPassword;
	private JButton btnRandomize_1;

	/**
	 * Create the dialog.
	 */
	public AddClientListener() {
		setPreferredSize(new Dimension(480, 270));
		setMaximumSize(new Dimension(480, 270));
		setBounds(new Rectangle(0, 0, 480, 270));
		setResizable(false);
		setTitle("Add a Client Listener");
		setAlwaysOnTop(true);
		setBounds(100, 100, 734, 421);
		getContentPane().setLayout(null);

		cards = new JPanel();
		cards.setBounds(0, 0, 724, 356);
		getContentPane().add(cards);
		cards.setLayout(new CardLayout(0, 0));
		cards.add(main_panel, "name_3036214224377");
		main_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		main_panel.setLayout(null);

		JPanel stats_panel = new JPanel(new BorderLayout());
		stats_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// do nothing
			}
		});
		stats_panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		stats_panel.setBounds(12, 12, 700, 60);

		main_panel.add(stats_panel);

		NetworkLineGraph networkLineChart = new NetworkLineGraph();
		networkLineChart.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// do nothing
			}
		});
		stats_panel.add(networkLineChart, BorderLayout.CENTER);

		JPanel info_panel = new JPanel();
		info_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Metadata", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		info_panel.setBounds(12, 78, 254, 165);
		main_panel.add(info_panel);
		info_panel.setLayout(null);

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Dialog", Font.BOLD, 11));
		lblName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblName.setBounds(9, 20, 58, 16);
		info_panel.add(lblName);

		listener_name = new JTextField();
		listener_name.setToolTipText("Optional name for this listener");
		listener_name.setText("default listener");
		listener_name.setBounds(80, 19, 162, 20);
		info_panel.add(listener_name);
		listener_name.setColumns(10);

		port = new JTextField();
		port.setToolTipText("Port on which to listen");
		port.requestFocusInWindow();
		port.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updatePortInfo();
			}
		});
		port.setBounds(80, 47, 51, 20);
		info_panel.add(port);
		port.setColumns(10);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Dialog", Font.BOLD, 11));
		lblPort.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPort.setBounds(9, 48, 58, 16);
		info_panel.add(lblPort);

		chckbxRememberThisListener = new JCheckBox("Remember this Listener");
		chckbxRememberThisListener.setFont(new Font("Dialog", Font.BOLD, 11));
		chckbxRememberThisListener.setToolTipText("Remembered listeners will be restored on startup");
		chckbxRememberThisListener.setBounds(9, 75, 233, 24);
		info_panel.add(chckbxRememberThisListener);
		chckbxRememberThisListener.setSelected(true);

		chckbxUpnp = new JCheckBox("Use UPnP");
		chckbxUpnp.setFont(new Font("Dialog", Font.BOLD, 11));
		chckbxUpnp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int p = 0;
				try {
					p = Integer.parseInt(port.getText());
				} catch (NumberFormatException e) {
					p = 0;
				}
				if (chckbxUpnp.isSelected() && p > 0 && p <= 65525) {
					upnpLights.replaceLight(Color.GREEN, 3);
					upnpStatus.setText("UPnP is ready to be tested");

					btnCheckUpnp.setEnabled(true);

				} else {
					upnpLights.replaceLight(Color.RED, 1);
					upnpStatus.setText("UPnP is not ready to be tested");
					btnCheckUpnp.setEnabled(false);

				}
				upnpStatus.setForeground(Color.BLACK);
			}
		});
		chckbxUpnp.setBounds(9, 96, 233, 23);
		info_panel.add(chckbxUpnp);

		JButton btnRandomize = new JButton("Randomize");
		btnRandomize.setToolTipText("Randomize the port number");
		btnRandomize.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				port.setText("" + subterranean.crimson.universal.Utilities.randPort());
				updatePortInfo();
			}
		});
		btnRandomize.setFont(new Font("Dialog", Font.BOLD, 10));
		btnRandomize.setBounds(146, 47, 96, 20);
		info_panel.add(btnRandomize);

		JPanel testing_panel = new JPanel();
		testing_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Analysis", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		testing_panel.setBounds(12, 245, 434, 107);
		main_panel.add(testing_panel);
		testing_panel.setLayout(null);

		portLights = new StatusLights();

		portLights.setSize(9, 26);
		portLights.replaceLight(Color.RED, 1);
		upnpLights = new StatusLights();
		portLights.setLocation(9, 40);
		upnpLights.setSize(9, 26);
		upnpLights.replaceLight(Color.RED, 1);
		upnpLights.setLocation(9, 75);
		testing_panel.add(upnpLights);
		testing_panel.add(portLights);

		btnCheckPort = new JButton("Test Port Visibility");
		btnCheckPort.setMargin(new Insets(2, 1, 2, 1));
		btnCheckPort.setFont(new Font("Dialog", Font.BOLD, 11));
		btnCheckPort.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!btnCheckPort.isEnabled()) {
					return;
				}
				Runnable r = new Runnable() {
					public void run() {
						if (!btnCheckPort.isEnabled()) {
							return;
						}
						int pt = 0;
						try {
							pt = Integer.parseInt(port.getText());
						} catch (NumberFormatException e) {
							return;
						}

						portLights.animate("indeterminate", Color.CYAN, null);
						portStatus.setForeground(Color.BLACK);
						portStatus.setText("Testing Port...");

						if (WebserverCommands.testPort(subterranean.crimson.universal.Utilities.getExternalIp(), pt)) {
							portStatus.setText("Success! The port is visible!");
							portStatus.setForeground(new Color(0, 102, 0));
							portLights.stopAnimation();
							portLights.light(Color.GREEN, 3);
						} else {
							portStatus.setText("Failed! The port is not visible!");
							portStatus.setForeground(Color.RED);
							portLights.stopAnimation();
							portLights.light(Color.RED, 1);
						}
					}
				};
				new Thread(r).start();

			}
		});
		btnCheckPort.setEnabled(false);
		btnCheckPort.setBounds(25, 40, 125, 25);
		testing_panel.add(btnCheckPort);

		btnCheckUpnp = new JButton("Test UPnP");
		btnCheckUpnp.setFont(new Font("Dialog", Font.BOLD, 11));
		btnCheckUpnp.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!btnCheckUpnp.isEnabled()) {
					return;
				}

				Runnable r = new Runnable() {

					@Override
					public void run() {
						upnpLights.animate("indeterminate", Color.CYAN, null);
						int p = Integer.parseInt(port.getText());
						upnpStatus.setForeground(Color.BLACK);
						upnpStatus.setText("Trying to forward port: " + p);
						boolean s = false;
						try {
							s = PortMapper.test(p);
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						upnpLights.stopAnimation();

						if (s) {
							upnpLights.light(Color.GREEN, 3);
							upnpStatus.setText("UPnP port forwarding succeeded!");
							upnpStatus.setForeground(Color.BLACK);
						} else {
							upnpLights.light(Color.RED, 1);
							upnpStatus.setText("UPnP port forwarding failed.");
							upnpStatus.setForeground(Color.RED);
						}

					}

				};
				new Thread(r).start();

			}

		});
		btnCheckUpnp.setEnabled(false);
		btnCheckUpnp.setBounds(25, 75, 125, 25);
		testing_panel.add(btnCheckUpnp);

		portStatus = new JLabel("The port is not ready to be tested");
		portStatus.setFont(new Font("URW Gothic", Font.BOLD, 12));
		portStatus.setBounds(153, 45, 283, 15);
		testing_panel.add(portStatus);

		upnpStatus = new JLabel("UPnP is not ready to be tested");
		upnpStatus.setFont(new Font("URW Gothic", Font.BOLD, 12));
		upnpStatus.setBounds(153, 80, 283, 15);
		testing_panel.add(upnpStatus);

		JLabel lblExternalIpAddress = new JLabel("External IP: " + Utilities.getExternalIp());
		lblExternalIpAddress.setFont(new Font("Dialog", Font.BOLD, 10));
		lblExternalIpAddress.setBounds(16, 17, 193, 16);
		testing_panel.add(lblExternalIpAddress);

		JLabel lblIntIp = new JLabel("Internal IP: " + Utilities.getIntenalIp());
		lblIntIp.setFont(new Font("Dialog", Font.BOLD, 10));
		lblIntIp.setBounds(221, 17, 213, 16);
		testing_panel.add(lblIntIp);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229), 1, true), "Statistics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(458, 245, 254, 107);
		main_panel.add(panel);
		panel.setLayout(null);

		JLabel lblClientListeners = new JLabel("Listeners: " + Server.listeners.size());
		lblClientListeners.setFont(new Font("Dialog", Font.BOLD, 11));
		lblClientListeners.setBounds(12, 22, 100, 15);
		panel.add(lblClientListeners);

		JLabel lblClients = new JLabel("Clients: " + Server.connections.size());
		lblClients.setFont(new Font("Dialog", Font.BOLD, 11));
		lblClients.setBounds(138, 22, 104, 15);
		panel.add(lblClients);

		JPanel encryption_panel = new JPanel();
		encryption_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Security", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		encryption_panel.setBounds(278, 78, 434, 165);
		main_panel.add(encryption_panel);
		encryption_panel.setLayout(null);
		
		JPanel security_options_panel = new JPanel();
		security_options_panel.setBorder(new TitledBorder(null, "Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		security_options_panel.setBounds(3, 120, 428, 40);
		encryption_panel.add(security_options_panel);
				security_options_panel.setLayout(null);
		
				final JCheckBox chckbxUseSsl = new JCheckBox("Use SSL");
				chckbxUseSsl.setFont(new Font("Dialog", Font.BOLD, 11));
				chckbxUseSsl.setBounds(42, 17, 166, 15);
				security_options_panel.add(chckbxUseSsl);
				
						JCheckBox chckbxNewCheckBox = new JCheckBox("Use Port Knocking");
						chckbxNewCheckBox.setFont(new Font("Dialog", Font.BOLD, 11));
						chckbxNewCheckBox.setBounds(213, 17, 154, 15);
						security_options_panel.add(chckbxNewCheckBox);
						chckbxNewCheckBox.setEnabled(false);
						
						JPanel security_symmetric_panel = new JPanel();
						security_symmetric_panel.setBorder(new TitledBorder(null, "Symmetric Encryption", TitledBorder.LEADING, TitledBorder.TOP, null, null));
						security_symmetric_panel.setBounds(3, 15, 428, 68);
						encryption_panel.add(security_symmetric_panel);
						security_symmetric_panel.setLayout(null);
						
								JLabel lblSymmetric = new JLabel("Algorithm:");
								lblSymmetric.setFont(new Font("Dialog", Font.BOLD, 11));
								lblSymmetric.setBounds(7, 17, 114, 15);
								security_symmetric_panel.add(lblSymmetric);
								
										algorithm = new JComboBox();
										algorithm.setFont(new Font("Dialog", Font.BOLD, 11));
										algorithm.setBounds(80, 15, 160, 20);
										security_symmetric_panel.add(algorithm);
										algorithm.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {
												switch ((EncType) algorithm.getSelectedItem()) {
												case None: {
													// disable password and async areas
													lblPassword.setEnabled(false);
													passwordField.setEnabled(false);
													passwordField.setText("");
													btnView.setEnabled(false);
													op_mode.setEnabled(false);
													btnRandomize_1.setEnabled(false);

													break;
												}
												default: {
													lblPassword.setEnabled(true);
													passwordField.setEnabled(true);
													btnRandomize_1.setEnabled(true);

													btnView.setEnabled(true);
													op_mode.setEnabled(true);

													break;
												}

												}
											}
										});
										algorithm.setModel(new DefaultComboBoxModel(EncType.values()));
										
												op_mode = new JComboBox();
												op_mode.setFont(new Font("Dialog", Font.BOLD, 11));
												op_mode.setBounds(252, 15, 62, 20);
												security_symmetric_panel.add(op_mode);
												op_mode.setEnabled(false);
												op_mode.setModel(new DefaultComboBoxModel(new String[] { "ECB" }));
												
														btnView = new JButton("View");
														btnView.setFont(new Font("Dialog", Font.BOLD, 10));
														btnView.setBounds(252, 40, 62, 20);
														security_symmetric_panel.add(btnView);
														btnView.setEnabled(false);
														
																btnRandomize_1 = new JButton("Randomize");
																btnRandomize_1.setBounds(326, 40, 88, 20);
																security_symmetric_panel.add(btnRandomize_1);
																btnRandomize_1.setEnabled(false);
																btnRandomize_1.addActionListener(new ActionListener() {
																	public void actionPerformed(ActionEvent arg0) {
																		if (!btnRandomize_1.isEnabled()) {
																			return;
																		}
																		passwordField.setText(Utilities.nameGen(Utilities.rand(8, 25)));
																	}
																});
																btnRandomize_1.setFont(new Font("Dialog", Font.BOLD, 9));
																
																		passwordField = new JPasswordField();
																		passwordField.setBounds(80, 40, 160, 20);
																		security_symmetric_panel.add(passwordField);
																		passwordField.setEnabled(false);
																		
																				lblPassword = new JLabel("Password:");
																				lblPassword.setFont(new Font("Dialog", Font.BOLD, 11));
																				lblPassword.setBounds(7, 41, 80, 16);
																				security_symmetric_panel.add(lblPassword);
																				lblPassword.setEnabled(false);
																				
																				JButton btnInformation = new JButton("Information");
																				btnInformation.setMargin(new Insets(2, 4, 2, 4));
																				btnInformation.setFont(new Font("Dialog", Font.BOLD, 9));
																				btnInformation.setBounds(326, 15, 88, 20);
																				security_symmetric_panel.add(btnInformation);
																				
																				JPanel panel_1 = new JPanel();
																				panel_1.setBorder(new TitledBorder(null, "Compression", TitledBorder.LEADING, TitledBorder.TOP, null, null));
																				panel_1.setBounds(3, 81, 428, 40);
																				encryption_panel.add(panel_1);
																				panel_1.setLayout(null);
																				
																						JLabel lblAlgorithm = new JLabel("Algorithm:");
																						lblAlgorithm.setBounds(7, 16, 169, 15);
																						panel_1.add(lblAlgorithm);
																						lblAlgorithm.setFont(new Font("Dialog", Font.BOLD, 11));
																						lblAlgorithm.setEnabled(false);
																						
																								final JComboBox comp_algorithm = new JComboBox();
																								comp_algorithm.setBounds(270, 13, 150, 20);
																								panel_1.add(comp_algorithm);
																								comp_algorithm.setEnabled(false);

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

		JPanel help_panel = new JPanel();
		cards.add(help_panel, "name_3048860839424");
		help_panel.setLayout(null);

		JLabel lblComingSoon = new JLabel("Coming Soon");
		lblComingSoon.setBounds(314, 156, 121, 15);
		help_panel.add(lblComingSoon);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			buttonPane.setBounds(0, 355, 724, 36);
			getContentPane().add(buttonPane);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setBounds(12, 5, 81, 24);
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
			okButton.setEnabled(false);
			okButton.setBounds(646, 5, 66, 24);
			okButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
			okButton.setPreferredSize(new Dimension(74, 26));
			okButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (!okButton.isEnabled()) {
						return;
					}
					showError("");// reset it
					int accessPort = 0;
					try {
						accessPort = Integer.parseInt(port.getText().trim());
					} catch (NumberFormatException ex) {
						showError("Invalid port detected!");
						return;
					}
					String name = listener_name.getText().trim();
					if (name.isEmpty()) {
						name = "Default Client Listener";

					}

					boolean upnp = chckbxUpnp.isSelected();
					boolean ssl = chckbxUseSsl.isSelected();
					boolean remember = chckbxRememberThisListener.isSelected();
					EncType encryption = (EncType) algorithm.getSelectedItem();

					SecretKeySpec key = null;
					String passphrase = String.valueOf(passwordField.getPassword());

					if (!validate_input(name, accessPort, passphrase, encryption)) {
						return;
					}

					key = Cryptography.makeKey(passphrase, encryption);

					Server.addClientListener(name, accessPort, remember, encryption, key, upnp, ssl, (CompressionLevel) comp_algorithm.getSelectedItem());

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
			btnHelp.setBounds(568, 5, 66, 24);
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
						if (port.getText().length() == 0) {
							okButton.setEnabled(false);
							return;
						}
						try {
							if (Integer.parseInt(port.getText()) <= 65536 && Integer.parseInt(port.getText()) > 0) {
								okButton.setEnabled(true);
							} else {
								okButton.setEnabled(false);
							}

						} catch (NumberFormatException f) {
							okButton.setEnabled(false);
						}

					}

				}
			});

			messageLabel = new JLabel("");
			messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
			messageLabel.setBounds(99, 10, 463, 15);
			messageLabel.setForeground(Color.RED);
			messageLabel.setPreferredSize(new Dimension(220, 15));
			buttonPane.add(messageLabel);
			buttonPane.add(btnHelp);
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
	}

	public void showError(String error) {
		messageLabel.setToolTipText(error);
		messageLabel.setText(error);

	}

	public boolean validate_input(String name, int port, String password, EncType encryption) {
		for (String s : Server.getSettings().getListeners()) {

			ListenerContainer lc = null;
			try {
				lc = (ListenerContainer) ObjectTransfer.fromString(s);
			} catch (InvalidObjectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (lc.portNumber == port) {
				showError("A listener already exists on the port");
				return false;
			}
		}

		PortSpec p = new PortSpec(port);

		if (encryption != EncType.None) {
			if (password.length() < 5) {
				showError("Password too short!");
				return false;
			}
			if (password.length() > 64) {
				showError("Password too long!");
				return false;
			}

		}
		if (!p.isValid()) {
			showError("Invalid port: " + port);
			return false;
		}

		if (name.length() > 64) {
			showError("Name too long!");
			return false;
		}
		for (ClientListener l : Server.listeners) {
			if (port == l.getPORT()) {
				// already listening
				showError("You already have a listener on that port");
				return false;
			}

		}
		if (!p.isOpen()) {
			showError("Cannot listen on port: " + port);
			return false;
		}

		return true;
	}

	public void updatePortInfo() {
		portStatus.setForeground(Color.BLACK);
		upnpStatus.setForeground(Color.BLACK);

		int p = 0;
		try {
			p = Integer.parseInt(port.getText());
			if (p <= 0 || p > 65525) {
				throw new Exception();
			}
		} catch (Exception e2) {
			// set light to red

			portLights.replaceLight(Color.RED, 1);
			portStatus.setText("The port is not ready to be tested");
			btnCheckPort.setEnabled(false);
			upnpLights.replaceLight(Color.RED, 1);

			upnpStatus.setText("UPnP is not ready to be tested");
			btnCheckUpnp.setEnabled(false);

			// disable add button
			okButton.setEnabled(false);
			return;
		}

		portLights.replaceLight(Color.GREEN, 3);
		portStatus.setText("The port is ready to be tested");
		btnCheckPort.setEnabled(true);

		// enable add button
		okButton.setEnabled(true);

		if (chckbxUpnp.isSelected()) {
			upnpLights.replaceLight(Color.GREEN, 3);
			btnCheckUpnp.setEnabled(true);
		}
	}
}
