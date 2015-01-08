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
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScreenViewer extends JFrame {

	private static final long serialVersionUID = 1L;

	public ScreenViewer(ArrayList<ImageIcon> screens, int i) {
		setTitle("Screenshot Viewer");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PluginManager.class.getResource("/subterranean/crimson/server/graphics/icons/icon.png")));
		setMinimumSize(new Dimension(200, 200));
		setSize(new Dimension(screens.get(i).getIconWidth() + 30, screens.get(i).getIconHeight() + 30));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel(screens.get(i));
		panel.add(label);
	}

}
