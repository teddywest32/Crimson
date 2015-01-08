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
package subterranean.crimson.server.graphics.panels.cp.desktop;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.panels.cp.CPanel;

public class SPInformation extends CPanel {

	private static final long serialVersionUID = 1L;

	public SPInformation(ControlPanel cp) {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

			}
		});
		setLayout(new CardLayout(0, 0));

		JPanel loading_panel = new JPanel();
		add(loading_panel, "name_5834841042560");
		loading_panel.setLayout(null);

		JLabel lblGatheringInformation = new JLabel("Gathering Information...");
		lblGatheringInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblGatheringInformation.setBounds(227, 152, 194, 15);
		loading_panel.add(lblGatheringInformation);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setBounds(247, 179, 148, 14);
		loading_panel.add(progressBar);

		JPanel infos = new JPanel();
		add(infos, "name_5825364463343");
		infos.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		infos.add(tabbedPane, BorderLayout.CENTER);

		JPanel info_system_panel = new JPanel();
		tabbedPane.addTab("System", null, info_system_panel, null);
		info_system_panel.setLayout(new BoxLayout(info_system_panel, BoxLayout.Y_AXIS));

		JPanel mem_panel = new JPanel();
		mem_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Memory", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		info_system_panel.add(mem_panel);
		mem_panel.setLayout(new BorderLayout(0, 0));

		JPanel cpu_panel = new JPanel();
		cpu_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "CPU General", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		info_system_panel.add(cpu_panel);
		cpu_panel.setLayout(new BorderLayout(0, 0));

		JPanel virtual_panel = new JPanel();
		virtual_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Virtualization", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		info_system_panel.add(virtual_panel);
		virtual_panel.setLayout(new BorderLayout(0, 0));

		JPanel info_security_panel = new JPanel();
		tabbedPane.addTab("Security", null, info_security_panel, null);

		JPanel info_network_panel = new JPanel();
		tabbedPane.addTab("Network", null, info_network_panel, null);
	}

	@Override
	public void changedConnectionState(boolean connected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deinitialize() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getPanelName() {
		return "information";
	}
}
