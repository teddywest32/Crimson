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
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

public class GenerationReport extends JFrame {

	private static final long serialVersionUID = 1L;

	public GenerationReport(ArrayList<String[]> v, String[] h) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GenerationReport.class.getResource("/subterranean/crimson/server/graphics/icons/icon.png")));
		setPreferredSize(new Dimension(200, 300));
		setMinimumSize(new Dimension(200, 300));
		setBounds(new Rectangle(0, 0, 200, 300));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Generation Report");
		setResizable(true);
		getContentPane().setLayout(new BorderLayout(0, 0));

		DataViewer dv = new DataViewer();
		dv.setList(v);
		dv.setHeaders(h);

		getContentPane().add(dv);

	}
}
