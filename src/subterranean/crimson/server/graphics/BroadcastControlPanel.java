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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class BroadcastControlPanel extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public BroadcastControlPanel() {
		setTitle("Broadcast Control Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 725, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel host_filters_panel = new JPanel();
		host_filters_panel.setBorder(new TitledBorder(null, "Host Filter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(host_filters_panel, BorderLayout.NORTH);
		host_filters_panel.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		contentPane.add(tabbedPane);

		JPanel update_panel = new JPanel();
		tabbedPane.addTab("Auto Update", null, update_panel, null);
		update_panel.setLayout(null);

		JPanel map_panel = new JPanel();
		tabbedPane.addTab("Map", null, map_panel, null);
		map_panel.setLayout(null);

		JPanel stats_panel = new JPanel();
		tabbedPane.addTab("Statistics", null, stats_panel, null);

		JPanel queue_panel = new JPanel();
		tabbedPane.addTab("Queue", null, queue_panel, null);
		queue_panel.setLayout(null);

		JPanel execution_panel = new JPanel();
		tabbedPane.addTab("Execution", null, execution_panel, null);

		JPanel client_actions_panel = new JPanel();
		tabbedPane.addTab("Actions", null, client_actions_panel, null);
	}

}
