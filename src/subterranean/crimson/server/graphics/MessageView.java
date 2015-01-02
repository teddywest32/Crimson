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
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import subterranean.crimson.universal.containers.SystemMessage;

public class MessageView extends JPanel {

	private static final long serialVersionUID = 1L;

	public MessageView(SystemMessage sm) {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblSubject = new JLabel("Subject: " + sm.subject);
		lblSubject.setFont(new Font("Dialog", Font.BOLD, 14));
		panel.add(lblSubject);

		JLabel lblDate = new JLabel("Date: " + sm.date.toString());
		lblDate.setFont(new Font("Dialog", Font.BOLD, 14));
		panel.add(lblDate);

		JPanel content_panel = new JPanel();
		add(content_panel, BorderLayout.CENTER);
		content_panel.setLayout(new BorderLayout(0, 0));

		JTextPane textPane = new JTextPane();
		textPane.setText(sm.message);
		content_panel.add(textPane);

	}

	public MessageView() {

	}

}
