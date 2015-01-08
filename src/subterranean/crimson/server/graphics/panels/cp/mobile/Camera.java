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
package subterranean.crimson.server.graphics.panels.cp.mobile;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class Camera extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Camera() {
		setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);

		JMenu mnMode = new JMenu("Mode");
		menuBar.add(mnMode);

		JRadioButton rdbtnStillImage = new JRadioButton("Still Image");
		mnMode.add(rdbtnStillImage);

		JRadioButton rdbtnVideo = new JRadioButton("Video");
		mnMode.add(rdbtnVideo);

		JMenu mnCameraSelection = new JMenu("Camera Selection");
		menuBar.add(mnCameraSelection);

		JRadioButton rdbtnFront = new JRadioButton("Front Facing");
		mnCameraSelection.add(rdbtnFront);

		JRadioButton rdbtnRear = new JRadioButton("Rear Facing");
		mnCameraSelection.add(rdbtnRear);

		JMenu mnQuality = new JMenu("Quality");
		menuBar.add(mnQuality);

		JRadioButton rdbtnHigh = new JRadioButton("High");
		mnQuality.add(rdbtnHigh);

		JRadioButton rdbtnMedium = new JRadioButton("Medium");
		mnQuality.add(rdbtnMedium);

		JRadioButton rdbtnLow = new JRadioButton("Low");
		mnQuality.add(rdbtnLow);

		JMenu mnExport = new JMenu("Output");
		menuBar.add(mnExport);

		JRadioButton rdbtnStreamOnly = new JRadioButton("Stream Only");
		mnExport.add(rdbtnStreamOnly);

		JRadioButton rdbtnToFile = new JRadioButton("To FIle");
		mnExport.add(rdbtnToFile);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Viewer", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);

		JLabel label = new JLabel("");
		scrollPane.setViewportView(label);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Map", null, panel_2, null);

	}

}
