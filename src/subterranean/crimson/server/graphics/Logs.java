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
package subterranean.crimson.server.graphics;



import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Reporter;

public class Logs extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;

	public Logs() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Logs.class.getResource("/subterranean/crimson/server/graphics/icons/icon.png")));
		setMinimumSize(new Dimension(625, 370));
		setPreferredSize(new Dimension(625, 370));
		setBounds(new Rectangle(0, 0, 625, 370));
		setTitle("Logs");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 625, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);

		JPanel log_panel = new JPanel();
		tabbedPane.addTab("Logs", null, log_panel, null);
		log_panel.setLayout(new CardLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		log_panel.add(scrollPane_1, "name_36216020966488");

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		scrollPane_1.setViewportView(textArea_1);

		String content = null;
		for (String s : Logger.log) {
			content += s + "\n";
		}
		textArea_1.setText(content);

		JPanel contact_panel = new JPanel();
		tabbedPane.addTab("Contact", null, contact_panel, null);
		contact_panel.setLayout(null);

		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String comment = "[EMAIL]" + textField.getText();
				comment += "[/EMAIL]";
				comment += textArea.getText();
				Reporter.report(comment);

				textField.setText("");
				textArea.setText("");
			}
		});
		btnSend.setBounds(471, 266, 117, 25);
		contact_panel.add(btnSend);

		JLabel lblEmailoptional = new JLabel("Email:(Optional)");
		lblEmailoptional.setBounds(22, 39, 117, 15);
		contact_panel.add(lblEmailoptional);

		textField = new JTextField();
		textField.setBounds(157, 37, 114, 19);
		contact_panel.add(textField);
		textField.setColumns(10);

		textArea = new JTextArea();
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBounds(32, 93, 556, 147);
		contact_panel.add(textArea);

		JLabel lblFeelFreeTo = new JLabel("Feel free to contact us about anything. ");
		lblFeelFreeTo.setBounds(12, 12, 576, 15);
		contact_panel.add(lblFeelFreeTo);

		JLabel lblComment = new JLabel("Comment:");
		lblComment.setBounds(22, 66, 82, 15);
		contact_panel.add(lblComment);
	}
}
