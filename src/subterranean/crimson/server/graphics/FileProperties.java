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
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class FileProperties extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel filePanel = new JPanel();

	public FileProperties(HashMap<String, Object> info) {
		setTitle("File Properties");
		setResizable(false);
		setBounds(100, 100, 406, 405);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						dispose();
					}
				});

				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}

		JPanel cards = new JPanel();
		getContentPane().add(cards, BorderLayout.CENTER);
		cards.setLayout(new CardLayout(0, 0));
		cards.add(filePanel, "name_38549156341509");
		filePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		filePanel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_1.setIcon((Icon) info.remove("icon"));
		lblNewLabel_1.setBounds(12, 12, 41, 40);
		filePanel.add(lblNewLabel_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 86, 372, 241);
		filePanel.add(scrollPane);

		ArrayList<String[]> data = new ArrayList<String[]>();
		for (String key : info.keySet()) {
			data.add(new String[] { key, info.get(key).toString() });
		}

		DataViewer dv = new DataViewer();
		dv.setList(data);
		dv.setHeaders(new String[] { "Property", "Value" });
		scrollPane.add(dv);
	}
}
