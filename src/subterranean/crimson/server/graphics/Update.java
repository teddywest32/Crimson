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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.Utilities;

public class Update extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField remote_address_field;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField_1;
	private JComboBox comboBox;
	private JLabel lblRemoteAddress;
	private JRadioButton rdbtnProxyUpdateThrough;
	private Connection c;
	private JPanel details_panel;

	public Update(Connection con) {
		c = con;
		setTitle("Update Client");
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 440, 235);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Update Method", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 12, 416, 62);
		contentPanel.add(panel);
		panel.setLayout(null);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch ((String) comboBox.getSelectedItem()) {
				case ("Remote Update"): {
					CardLayout cl = (CardLayout) (details_panel.getLayout());
					cl.show(details_panel, "name_4588062627894");

					break;
				}
				case ("Local Update"): {
					CardLayout cl = (CardLayout) (details_panel.getLayout());
					cl.show(details_panel, "name_4601502087113");

					break;
				}
				case ("Automatic Update"): {
					CardLayout cl = (CardLayout) (details_panel.getLayout());
					cl.show(details_panel, "name_4611507522922");

					break;
				}

				}

			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Remote Update", "Local Update", "Automatic Update" }));
		comboBox.setBounds(119, 24, 177, 24);
		panel.add(comboBox);

		details_panel = new JPanel();
		details_panel.setBorder(new TitledBorder(null, "Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		details_panel.setBounds(12, 86, 416, 137);
		contentPanel.add(details_panel);
		details_panel.setLayout(new CardLayout(0, 0));

		JPanel remote_panel = new JPanel();
		details_panel.add(remote_panel, "name_4588062627894");
		remote_panel.setLayout(null);

		lblRemoteAddress = new JLabel("Remote Address:");
		lblRemoteAddress.setBounds(12, 12, 131, 15);
		remote_panel.add(lblRemoteAddress);

		remote_address_field = new JTextField();
		remote_address_field.setBounds(161, 10, 233, 19);
		remote_panel.add(remote_address_field);
		remote_address_field.setColumns(10);

		rdbtnProxyUpdateThrough = new JRadioButton("Proxy Update through Server");
		rdbtnProxyUpdateThrough.setSelected(true);
		buttonGroup.add(rdbtnProxyUpdateThrough);
		rdbtnProxyUpdateThrough.setBounds(22, 35, 246, 23);
		remote_panel.add(rdbtnProxyUpdateThrough);

		JRadioButton rdbtnDirectUpdate = new JRadioButton("Direct Update");
		buttonGroup.add(rdbtnDirectUpdate);
		rdbtnDirectUpdate.setBounds(22, 62, 246, 23);
		remote_panel.add(rdbtnDirectUpdate);

		JPanel local_panel = new JPanel();
		details_panel.add(local_panel, "name_4601502087113");
		local_panel.setLayout(null);

		JLabel lblPathToUpdate = new JLabel("Path to Update:");
		lblPathToUpdate.setBounds(12, 12, 113, 15);
		local_panel.add(lblPathToUpdate);

		textField_1 = new JTextField();
		textField_1.setBounds(143, 10, 170, 19);
		local_panel.add(textField_1);
		textField_1.setColumns(10);
		final JFileChooser fc = new JFileChooser();
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int returnVal = fc.showOpenDialog(Update.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					if (file.isDirectory()) {
						dispose();
					}

					String path = file.getAbsolutePath();
					textField_1.setText(path);

				}
			}
		});
		btnBrowse.setFont(new Font("Dialog", Font.BOLD, 9));
		btnBrowse.setBounds(325, 9, 69, 19);
		local_panel.add(btnBrowse);

		JPanel automatic_panel = new JPanel();
		details_panel.add(automatic_panel, "name_4611507522922");

		JLabel lblComingSoon = new JLabel("Coming Soon");
		automatic_panel.add(lblComingSoon);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 235, 440, 35);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						dispose();
					}
				});
				cancelButton.setBounds(12, 5, 81, 25);
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("Update");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {

						switch ((String) comboBox.getSelectedItem()) {
						case ("Remote Update"): {
							final String l = remote_address_field.getText();
							if (rdbtnProxyUpdateThrough.isSelected()) {

								// proxying this update
								Runnable r = new Runnable() {
									public void run() {
										// download the update first
										byte[] update = Utilities.download(l);

										// send the update to the client
										ClientCommands.client_update(c, update);

									}
								};
								new Thread(r).start();

							} else {
								// direct update
//								ClientCommands.client_dUpdate(c, l);

							}

							break;
						}
						case ("Local Update"): {
							final String l = textField_1.getText();

							Runnable r = new Runnable() {
								public void run() {
									ClientCommands.client_update(c, l);

								}
							};
							new Thread(r).start();

							break;
						}
						case ("Automatic Update"): {
							Runnable r = new Runnable() {
								public void run() {

								}
							};
							new Thread(r).start();

							break;
						}

						}

						dispose();

					}
				});
				okButton.setBounds(341, 5, 94, 25);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
