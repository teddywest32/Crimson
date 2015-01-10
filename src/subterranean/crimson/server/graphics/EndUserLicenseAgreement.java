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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import subterranean.crimson.server.Frontend;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.Version;
import subterranean.crimson.universal.translation.T;

public class EndUserLicenseAgreement extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private boolean exitOnDisp;

	public Date start;
	private JButton okButton;

	@Override
	public void dispose() {
		if (exitOnDisp) {
			if (Frontend.EULAaccepted) {
				super.dispose();
			} else {
				System.exit(0);
			}

		} else {
			super.dispose();
		}
	}

	public EndUserLicenseAgreement(String version, boolean exitOnDispose) {
		exitOnDisp = exitOnDispose;
		setPreferredSize(new Dimension(650, 310));
		setSize(new Dimension(650, 310));
		setMinimumSize(new Dimension(650, 310));
		setMaximumSize(new Dimension(650, 310));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(EndUserLicenseAgreement.class.getResource("/subterranean/crimson/server/graphics/icons/icon.png")));

		setResizable(false);

		setTitle(T.t("misc-crimson") + " " + Version.version);

		setBounds(100, 100, 650, 310);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 640, 245);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				int extent = scrollPane.getVerticalScrollBar().getModel().getExtent();
				if (scrollPane.getVerticalScrollBar().getValue() + extent == scrollPane.getVerticalScrollBar().getMaximum()) {

					okButton.setEnabled(true);
				}

			}

		});
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(12, 12, 618, 221);
		contentPanel.add(scrollPane);

		// load the license from file
		Scanner sc = new Scanner(getClass().getResourceAsStream("license"));
		StringBuffer license = new StringBuffer();
		while (sc.hasNextLine()) {
			license.append(sc.nextLine());

		}
		sc.close();

		final JTextPane txtpnCrimsonRemoteAdministration = new JTextPane();
		txtpnCrimsonRemoteAdministration.setContentType("text/html");
		txtpnCrimsonRemoteAdministration.setEditable(false);
		txtpnCrimsonRemoteAdministration.setText(license.toString());

		scrollPane.setViewportView(txtpnCrimsonRemoteAdministration);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 244, 640, 36);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				okButton = new JButton(T.t("button-agree"));
				okButton.setEnabled(false);
				okButton.setMnemonic('A');
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (!okButton.isEnabled()) {
							return;
						}
						// accepted
						Frontend.EULAaccepted = true;
						dispose();

					}
				});
				okButton.setPreferredSize(new Dimension(103, 26));
				okButton.setBounds(12, 5, 120, 26);

				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton(T.t("button-disagree"));
				cancelButton.setMnemonic('D');
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						// declined
						dispose();
						System.exit(0);
					}
				});
				cancelButton.setBounds(508, 5, 120, 26);

				buttonPane.add(cancelButton);
			}

			final StatusLights statusLights = new StatusLights();

			statusLights.setBounds(306, 5, 9, 25);
			statusLights.animate("random");
			statusLights.setToolTipText("Shhhh. It's thinking.");
			buttonPane.add(statusLights);

			final StatusLights statusLights_1 = new StatusLights();

			statusLights_1.animate("random");
			statusLights_1.setBounds(315, 5, 9, 25);
			statusLights_1.setToolTipText("Shhhh. It's thinking.");
			buttonPane.add(statusLights_1);

			final StatusLights statusLights_2 = new StatusLights();
			statusLights_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (statusLights_2.isAnimating()) {
						statusLights_2.stopAnimation();
						statusLights_1.stopAnimation();
						statusLights.stopAnimation();
					} else {
						statusLights_2.animate("random");
						statusLights_1.animate("random");
						statusLights.animate("random");
					}
				}
			});
			statusLights_2.animate("random");
			statusLights_2.setBounds(324, 5, 9, 25);
			statusLights_2.setToolTipText("Shhhh. It's thinking.");
			buttonPane.add(statusLights_2);

			statusLights.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (statusLights_2.isAnimating()) {
						statusLights_2.stopAnimation();
						statusLights_1.stopAnimation();
						statusLights.stopAnimation();
					} else {
						statusLights_2.animate("random");
						statusLights_1.animate("random");
						statusLights.animate("random");
					}

				}
			});

			statusLights_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (statusLights_2.isAnimating()) {
						statusLights_2.stopAnimation();
						statusLights_1.stopAnimation();
						statusLights.stopAnimation();
					} else {
						statusLights_2.animate("random");
						statusLights_1.animate("random");
						statusLights.animate("random");
					}
				}
			});
		}
		start = new Date();
	}
}
