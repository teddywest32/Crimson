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



import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class GenerateShellcode extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public GenerateShellcode() {
		setTitle("Generate Shellcode");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 607, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 440, 270);
		contentPane.add(tabbedPane);

		JPanel information_panel = new JPanel();
		tabbedPane.addTab("Information", null, information_panel, null);
		information_panel.setLayout(null);

		JTextArea txtrTheShellcodeGenerator = new JTextArea();
		txtrTheShellcodeGenerator.setText("The Shellcode generator can generate a downloader as shellcode which can be used to install Crimson when Jar delivery is not an option. Shellcode can be crafted to meet your delivery needs. This method is more flexible and highly platform specific.\n\nCURRENTLY not fully implemented. ");
		txtrTheShellcodeGenerator.setWrapStyleWord(true);
		txtrTheShellcodeGenerator.setLineWrap(true);
		txtrTheShellcodeGenerator.setBackground(new Color(0, 0, 0, 0));
		txtrTheShellcodeGenerator.setOpaque(false);

		txtrTheShellcodeGenerator.setBounds(12, 12, 387, 194);
		information_panel.add(txtrTheShellcodeGenerator);

		JPanel platform_panel = new JPanel();
		tabbedPane.addTab("Platform", null, platform_panel, null);
	}
}
