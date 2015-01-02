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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import subterranean.crimson.server.PluginOperations;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Platform;

public class InstallLocalPlugin extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	public InstallLocalPlugin() {
		setTitle("Install local Plugin");
		setBounds(100, 100, 446, 196);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(72, 19, 259, 20);
		contentPanel.add(textField);
		textField.setColumns(10);

		JLabel lblPath = new JLabel("Path:");
		lblPath.setBounds(12, 20, 55, 16);
		contentPanel.add(lblPath);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addMouseListener(new MouseAdapter() {
			private JFileChooser fc = new JFileChooser();

			@Override
			public void mousePressed(MouseEvent e) {
				int returnVal = fc.showOpenDialog(InstallLocalPlugin.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					if (file.isDirectory()) {
						dispose();
					}
					// add the file
					String filename = file.getName();
					if (!filename.endsWith(".jar")) {
						Logger.add("Input file must be a .jar");
					}

					String path = file.getAbsolutePath();
					textField.setText(path);

				}

			}
		});
		btnBrowse.setFont(new Font("Dialog", Font.BOLD, 10));
		btnBrowse.setBounds(343, 19, 81, 20);
		contentPanel.add(btnBrowse);

		JTextArea txtrExerciseCautionWhen = new JTextArea();
		txtrExerciseCautionWhen.setLineWrap(true);
		txtrExerciseCautionWhen.setWrapStyleWord(true);
		txtrExerciseCautionWhen.setText("Exercise caution when installing a plugin from a local source. As can any type of executable file, malicious plugins can harm your computer.  Dont install a plugin from a source you dont trust.");
		txtrExerciseCautionWhen.setBackground(new Color(0, 0, 0, 0));
		txtrExerciseCautionWhen.setOpaque(false);
		txtrExerciseCautionWhen.setBounds(22, 48, 402, 71);
		contentPanel.add(txtrExerciseCautionWhen);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Install");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						Runnable r = new Runnable() {
							public void run() {

								File plugin = new File(textField.getText());

								PluginOperations.installLocal(plugin, plugin.getName().split("\\.")[0]);
							}

						};
						new Thread(r).start();

						// display message
						Notice rn = new Notice("Installing Local Plugin");
						rn.setLocationRelativeTo(null);
						rn.setVisible(true);
						dispose();

					}
				});
				{
					JButton cancelButton = new JButton("Cancel");
					cancelButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							dispose();
						}
					});
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
				}
				okButton.setActionCommand("");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
