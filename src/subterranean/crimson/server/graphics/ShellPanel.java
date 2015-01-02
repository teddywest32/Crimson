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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.exceptions.InvalidResponseException;

public class ShellPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static ArrayList<String> history = new ArrayList<String>();
	private Connection c;

	public int shellID;// id of shell on permajar
	private JTextField textField;
	private static String prompt;

	private StatusLights sl;

	private JTextPane textPane;

	public ShellPanel(int id, Connection con) {
		c = con;
		prompt = c.getProfile().info.getUsername() + " ~ $ ";

		shellID = id;
		setLayout(new BorderLayout(0, 0));
		setVisible(true);

		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout(0, 0));

		sl = new StatusLights();
		sl.setPreferredSize(new Dimension(8, 23));
		sl.setBounds(new Rectangle(0, 0, 9, 25));
		sl.light(Color.GREEN, 3);
		jp.add(sl, BorderLayout.WEST);

		textField = new JTextField();
		textField.setPreferredSize(new Dimension(330, 19));
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				Runnable r = new Runnable() {

					@Override
					public void run() {
						switch (e.getKeyCode()) {
						case (KeyEvent.VK_ENTER): {
							// change the light to yellow
							sl.replaceLight(Color.YELLOW, 2);
							textPane.setText(textPane.getText() + "\n" + textField.getText());
							String command = textField.getText().substring(prompt.length());
							textField.setText("");
							history.add(command);
							Logger.add("Running: " + command);
							String[] output = null;
							try {
								output = ClientCommands.shell_execute(c, command, shellID);
							} catch (InvalidResponseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							Logger.add("Got output: " + output.length);

							for (String s : output) {
								textPane.setText(textPane.getText() + "\n" + s);
							}
							sl.replaceLight(Color.GREEN, 3);
							break;
						}
						case (KeyEvent.VK_UP): {
							// get the last command

							break;
						}

						}
						if (textField.getCaretPosition() <= prompt.length()) {
							textField.setCaretPosition(prompt.length());
						}

					}

				};
				new Thread(r).start();

			}
		});
		jp.add(textField);
		add(jp, BorderLayout.SOUTH);
		textField.setColumns(10);
		textField.setText(prompt);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		textPane = new JTextPane();
		textPane.setFont(new Font("Dialog", Font.PLAIN, 12));
		textPane.setContentType("text/plain");
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);

		((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				if (offset < prompt.length()) {
					return;
				}
				super.insertString(fb, offset, string, attr);
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				if (offset < prompt.length()) {
					length = Math.max(0, length - prompt.length());
					offset = prompt.length();
				}
				super.replace(fb, offset, length, text, attrs);
			}

			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
				if (offset < prompt.length()) {
					length = Math.max(0, length + offset - prompt.length());
					offset = prompt.length();
				}
				if (length > 0) {
					super.remove(fb, offset, length);
				}
			}
		});

	}

}
