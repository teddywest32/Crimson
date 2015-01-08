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
package subterranean.crimson.server.graphics.panels.generate.jar;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public InfoPanel() {
		setLayout(null);

		JTextArea txtrTheJarPayload = new JTextArea();
		txtrTheJarPayload.setText("The Jar Installer Generator can generate a runnable Java Archive (Jar) file that installs the Crimson client.  Jar files can be executed by double clicking on them or with: \"java -jar jarfile.jar\" from the command line. The client will then make an attempt to contact the Crimson server based on the set parameters.  The Jar installation vector is typically the fastest and easiest way to install Crimson.  Many options specified here can be adjusted even after Crimson has installed.");
		txtrTheJarPayload.setBackground(new Color(0, 0, 0, 0));
		txtrTheJarPayload.setOpaque(false);

		txtrTheJarPayload.setWrapStyleWord(true);
		txtrTheJarPayload.setLineWrap(true);
		txtrTheJarPayload.setEditable(false);
		txtrTheJarPayload.setBounds(12, 12, 567, 139);
		add(txtrTheJarPayload);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Legal", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(12, 200, 567, 100);
		add(panel_2);
		panel_2.setLayout(null);

		JTextArea txtrInstallingCrimsonOn = new JTextArea();
		txtrInstallingCrimsonOn.setLineWrap(true);
		txtrInstallingCrimsonOn.setWrapStyleWord(true);
		txtrInstallingCrimsonOn.setText("Installing Crimson on systems that you do not have permission to do so on is illegal. Subterranean Security will not be held liable in the event of misuse of Crimson Extended Administration Tool.");
		txtrInstallingCrimsonOn.setOpaque(false);
		txtrInstallingCrimsonOn.setBackground(new Color(0, 0, 0, 0));
		txtrInstallingCrimsonOn.setBounds(12, 23, 543, 64);
		panel_2.add(txtrInstallingCrimsonOn);
	}

}
