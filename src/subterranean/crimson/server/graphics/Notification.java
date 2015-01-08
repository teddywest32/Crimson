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
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class Notification extends JPanel {


	public Notification(String string) {
		this(string, null, "");
	}

	public Notification(String string, final Runnable r, String type) {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BorderLayout(0, 0));

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				new Thread(r).start();

			}
		});
		lblStatus = new JLabel(string);
		lblStatus.setFont(new Font("Dialog", Font.BOLD, 13));
		add(lblStatus, BorderLayout.CENTER);

		JLabel label = new JLabel("");
		switch (type) {
		case ("error"): {
			label.setIcon(new ImageIcon(Notification.class.getResource("/subterranean/crimson/server/graphics/icons/notification/error-40.png")));
			break;
		}
		default: {
			label.setIcon(new ImageIcon(Notification.class.getResource("/subterranean/crimson/server/graphics/icons/C-40.png")));
			break;
		}
		}

		add(label, BorderLayout.WEST);
	}

	private static final long serialVersionUID = 1L;
	private JLabel lblStatus;
}
