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
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import subterranean.crimson.universal.streams.filestream.FSParameters;

public class FileTransferConfirm extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public FileTransferConfirm(FSParameters param) {
		setTitle("Confirm File Transfer");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 12, 416, 72);
		contentPanel.add(panel);
		panel.setLayout(null);
		{
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(null, "Transfer only when:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_1.setBounds(12, 96, 416, 96);
			contentPanel.add(panel_1);
			panel_1.setLayout(null);

			JCheckBox checkBox = new JCheckBox("");
			checkBox.setBounds(33, 30, 129, 23);
			panel_1.add(checkBox);
			{
				JCheckBox checkBox_1 = new JCheckBox("");
				checkBox_1.setBounds(43, 57, 129, 23);
				panel_1.add(checkBox_1);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
