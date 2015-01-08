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
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ProcessPanel extends JPanel {

	public StatusLights sl;

	public ProcessPanel() {
		setBorder(null);
		setPreferredSize(new Dimension(200, 20));
		setMinimumSize(new Dimension(200, 20));
		setLayout(new BorderLayout(0, 0));
		sl = new StatusLights();
		sl.setPreferredSize(new Dimension(7, 20));
		add(sl, BorderLayout.WEST);

		p = new JLabel("Process...");
		p.setBorder(new LineBorder(new Color(200, 0, 0)));
		add(p, BorderLayout.CENTER);
	}

	private static final long serialVersionUID = 1L;
	public JLabel p;

}
