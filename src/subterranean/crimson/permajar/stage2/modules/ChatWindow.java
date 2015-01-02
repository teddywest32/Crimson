package subterranean.crimson.permajar.stage2.modules;

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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import subterranean.crimson.permajar.stage1.network.Communications;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.containers.Message;

public class ChatWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textArea2;
	private JPanel button_panel;
	private JTextArea textArea;
	private JButton btnNewButton;
	private JPanel chat_panel;
	private JPanel screen_panel;
	private JPanel panel_1;

	public ChatWindow() {
		setMinimumSize(new Dimension(450, 300));
		setTitle("Remote Chat");
		setBounds(100, 100, 782, 522);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		chat_panel = new JPanel();
		chat_panel.setPreferredSize(new Dimension(250, 10));
		contentPane.add(chat_panel);
		chat_panel.setLayout(null);

		panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 250, 482);
		chat_panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel_1.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea();
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (textArea.getText().length() > 0) {
					btnNewButton.setEnabled(true);
				} else {
					btnNewButton.setEnabled(false);
				}
			}
		});
		textArea.setRows(3);
		panel.add(textArea, BorderLayout.CENTER);

		button_panel = new JPanel(new FlowLayout(SwingConstants.RIGHT));
		panel.add(button_panel, BorderLayout.SOUTH);

		btnNewButton = new JButton("Send");
		btnNewButton.setEnabled(false);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (textArea.getText().length() < 1) {
					return;
				}
				String message = textArea.getText();
				addMessage(message, "You");
				textArea.setText("");
				Message mreq = new Message(0, BMN.CHAT_message, message);
				Communications.sendHome(mreq);

			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 10));
		button_panel.add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));

		textArea2 = new JTextArea();
		textArea2.setEditable(false);
		scrollPane.setViewportView(textArea2);

		screen_panel = new JPanel();
		screen_panel.setPreferredSize(new Dimension(300, 10));
		contentPane.add(screen_panel);
		screen_panel.setLayout(new BorderLayout(0, 0));
	}

	public void addMessage(String message, String user) {
		Logger.add("Updating text pane");
		textArea2.setText(textArea2.getText() + "\n[" + new Date().toString() + "] (" + user + "): " + message);

	}
}
