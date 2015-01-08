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
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

public class ImageScroller extends JPanel {

	private static final long serialVersionUID = 1L;

	public ImageScroller(ImageIcon[] images, int lr, int ud) {

		Component form[][] = new Component[ud][lr];
		JPanel p = new JPanel();
		p.setSize(600, 400);
		p.setLayout(new GridLayout(ud, lr, 10, 10));
		int imgIndex = 0;
		for (int row = 0; row < ud; row++) {
			for (int col = 0; col < lr; col++) {

				if (images[imgIndex] == null) {
					JProgressBar jpb = new JProgressBar();
					jpb.setIndeterminate(true);
					jpb.setString("Loading Image");
					jpb.setStringPainted(true);

					form[row - 1][col - 1] = jpb;
				} else {
					form[row - 1][col - 1] = new JLabel(images[imgIndex]);
				}

				p.add(form[row - 1][col - 1]);
				imgIndex++;
			}
		}
		setLayout(new BorderLayout(0, 0));
		JScrollPane scrollpane = new JScrollPane(p);
		add(scrollpane, BorderLayout.CENTER);

	}

}
