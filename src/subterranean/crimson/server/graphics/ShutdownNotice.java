package subterranean.crimson.server.graphics;

/*
 * 	Crimson Extended Administration Tool
 *  Copyright (C) 2015 Subterranean Security
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ShutdownNotice extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblstatus;
	public Thread entertainment = new Thread(new Runnable() {
		public void run() {

			try {
				lblstatus.setText("Saving Data...");
				Thread.sleep(7000);
				lblstatus.setText("Vacuuming Database");
				Thread.sleep(4000);
				lblstatus.setText("Wrangling puppies");
				Thread.sleep(4000);
				lblstatus.setText("Taking a break");
				Thread.sleep(8000);
				lblstatus.setText("Cleaning the Kitchen");
				Thread.sleep(2000);
				lblstatus.setText("Something is wrong");

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	});

	public ShutdownNotice() {
		setResizable(false);
		setTitle("Please wait");
		setBounds(100, 100, 450, 124);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JProgressBar progressBar = new JProgressBar();
			progressBar.setIndeterminate(true);
			progressBar.setBounds(22, 68, 304, 14);
			contentPanel.add(progressBar);
		}
		{
			JLabel lblThePluginHas = new JLabel("Crimson is Closing. Please wait...");
			lblThePluginHas.setHorizontalAlignment(SwingConstants.CENTER);
			lblThePluginHas.setBounds(12, 12, 416, 17);
			lblThePluginHas.setFont(new Font("Dialog", Font.BOLD, 14));
			contentPanel.add(lblThePluginHas);
		}

		lblstatus = new JLabel("Saving data...");
		lblstatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblstatus.setBounds(12, 41, 416, 15);
		contentPanel.add(lblstatus);

		JButton btnBackground = new JButton("Background");
		btnBackground.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
		btnBackground.setMargin(new Insets(0, 5, 0, 5));
		btnBackground.setFont(new Font("Dialog", Font.BOLD, 10));
		btnBackground.setBounds(338, 65, 90, 17);
		contentPanel.add(btnBackground);

	}
}
