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
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import subterranean.crimson.universal.Version;
import subterranean.crimson.universal.translation.T;

import java.awt.Dimension;

public class Welcome extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public Welcome() {
		setTitle("Welcome to Crimson " + Version.version + "!");
		setResizable(false);
		setBounds(100, 100, 413, 492);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			contentPanel.add(tabbedPane, BorderLayout.CENTER);
			{
				JPanel welcome = new JPanel();
				tabbedPane.addTab(T.t("misc-welcome"), null, welcome, null);
				welcome.setLayout(new BorderLayout(0, 0));
				{
					JLabel lblImage = new JLabel("");
					lblImage.setIcon(new ImageIcon(Welcome.class.getResource("/subterranean/crimson/server/graphics/images/welcome.png")));
					welcome.add(lblImage, BorderLayout.CENTER);
				}
			}
			{
				JPanel changelog = new JPanel();
				tabbedPane.addTab("Changelog", null, changelog, null);
				changelog.setLayout(new BorderLayout(0, 0));
				{
					JScrollPane scrollPane = new JScrollPane();
					changelog.add(scrollPane, BorderLayout.CENTER);
					{
						JTextArea textArea = new JTextArea();
						textArea.setEditable(false);
						scrollPane.setViewportView(textArea);
						// read the changelog and write it to the pane
						InputStream in = getClass().getResourceAsStream("changelog");
						Scanner sc = new Scanner(in);
						String clog = "";
						while (sc.hasNextLine()) {
							clog += sc.nextLine() + "\n";
						}
						sc.close();
						textArea.setText(clog);

					}
				}
			}
			{
				JPanel known_issues = new JPanel();
				tabbedPane.addTab("Issues", null, known_issues, null);
				known_issues.setLayout(new BorderLayout(0, 0));
				{
					JScrollPane scrollPane = new JScrollPane();
					known_issues.add(scrollPane, BorderLayout.CENTER);
					{
						JTextArea textArea = new JTextArea();
						textArea.setEditable(false);
						scrollPane.setViewportView(textArea);
						// read the known issues and write it to the pane
						InputStream in = getClass().getResourceAsStream("issues");
						Scanner sc = new Scanner(in);
						String clog = "";
						while (sc.hasNextLine()) {
							clog += sc.nextLine() + "\n";
						}
						sc.close();
						textArea.setText(clog);
					}
				}
				{
					JPanel panel = new JPanel();
					known_issues.add(panel, BorderLayout.NORTH);
					{
						StatusLights l = new StatusLights();
						l.setPreferredSize(new Dimension(9, 26));
						l.replaceLight(Color.RED, 3);
						{
							JLabel lblStablityLevel = new JLabel("Stablity Level:");
							panel.add(lblStablityLevel);
						}
						panel.add(l);

						JLabel lblBad = new JLabel("Mediocre");
						panel.add(lblBad);

					}
				}
			}
			{
				JPanel attribution_panel = new JPanel();
				tabbedPane.addTab("Attribution", null, attribution_panel, null);
				attribution_panel.setLayout(new BorderLayout(0, 0));
				{
					JPanel panel_1 = new JPanel();
					attribution_panel.add(panel_1, BorderLayout.NORTH);
					{
						JLabel lblMadePossibleIn = new JLabel("Made possible in part by:");
						panel_1.add(lblMadePossibleIn);
					}
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					attribution_panel.add(scrollPane, BorderLayout.CENTER);
					{
						JTextArea textArea = new JTextArea();
						textArea.setEditable(false);
						scrollPane.setViewportView(textArea);
						// read the attribution and write it to the pane
						InputStream in = getClass().getResourceAsStream("attribution");
						Scanner sc = new Scanner(in);
						String clog = "";
						while (sc.hasNextLine()) {
							clog += sc.nextLine() + "\n";
						}
						sc.close();
						textArea.setText(clog);
					}
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
