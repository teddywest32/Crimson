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
package subterranean.crimson.server.graphics.frames;



import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import subterranean.crimson.universal.Version;
import subterranean.crimson.universal.translation.T;

public class About extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static final String btcAddress = "1K5KfrLgfPSmiA6WP9h7BwMahzYXptDdRT";

	public About() {

		setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/subterranean/crimson/server/graphics/icons/C-40.png")));
		setResizable(false);
		setTitle("About " + T.t("misc-crimson") + " " + Version.version);
		setBounds(100, 100, 630, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 620, 234);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{

			ImageIcon gif = new ImageIcon(this.getClass().getClassLoader().getResource("subterranean/crimson/server/graphics/images/Crimson.gif"));

			JLabel lblImage = new JLabel(gif, SwingUtilities.CENTER);

			gif.setImageObserver(lblImage);
			lblImage.setBounds(12, 10, 596, 148);
			lblImage.setVisible(true);
			contentPanel.add(lblImage);

		}

		JTextArea about = new JTextArea();
		about.setOpaque(false);
		about.setFont(new Font("URW Gothic", Font.BOLD, 12));
		about.setEditable(false);
		about.setLineWrap(true);
		about.setWrapStyleWord(true);
		about.setText(T.t("misc-about"));
		about.setBackground(new Color(0, 0, 0, 0));
		about.setBounds(12, 158, 596, 76);
		contentPanel.add(about);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 234, 620, 36);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton(T.t("button-ok"));
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						dispose();
					}
				});
				{
					JButton btnVisitSubterraneansecuritycom = new JButton("Subterranean-Security.com");
					btnVisitSubterraneansecuritycom.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {

							if (Desktop.isDesktopSupported()) {
								try {
									Desktop.getDesktop().browse(new URI("https://www.subterranean-security.com"));
									return;
								} catch (IOException | URISyntaxException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}
							// could not navigate to page

						}
					});
					btnVisitSubterraneansecuritycom.setFont(new Font("Dialog", Font.BOLD, 10));
					buttonPane.add(btnVisitSubterraneansecuritycom);
				}
				{
					JButton btnVisitCrimsonratorg = new JButton("CrimsonRAT.org");
					btnVisitCrimsonratorg.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {

							if (Desktop.isDesktopSupported()) {
								try {
									Desktop.getDesktop().browse(new URI("https://www.crimsonrat.org"));
									return;
								} catch (IOException | URISyntaxException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}
							// could not navigate to page

						}
					});
					btnVisitCrimsonratorg.setFont(new Font("Dialog", Font.BOLD, 10));
					buttonPane.add(btnVisitCrimsonratorg);
				}
				{
					JButton btnCopyDonationAddress = new JButton(T.t("button-copy_btc"));
					btnCopyDonationAddress.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(btcAddress), null);
						}
					});
					btnCopyDonationAddress.setFont(new Font("Dialog", Font.BOLD, 10));
					buttonPane.add(btnCopyDonationAddress);
				}
				okButton.setActionCommand(T.t("button-ok"));
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
