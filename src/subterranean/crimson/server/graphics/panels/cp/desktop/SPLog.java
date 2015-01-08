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

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.graphics.BackgroundProgressLights;
import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.frames.MainScreen;
import subterranean.crimson.server.graphics.panels.cp.CPanel;
import subterranean.crimson.universal.exceptions.InvalidResponseException;

public class SPLog extends CPanel {

	private static final long serialVersionUID = 1L;

	public SPLog(ControlPanel cp) {
		setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane_4 = new JScrollPane();
		add(scrollPane_4);

		JTextArea logarea = new JTextArea();
		logarea.setEditable(false);

		String[] log = new String[] { "Failed to get Client Log" };
		try {
			log = ClientCommands.client_getLog(cp.c);
		} catch (InvalidResponseException e1) {
			// failed to get log from client
			MainScreen.window.addNotification("Failed to open control panel.");

			cp.dispose();
			BackgroundProgressLights.stop("Opening Control Panel");
			return;
		}
		String data = "";
		for (int i = 0; i < log.length; i++) {
			data += log[i] + "\n";
		}

		logarea.setText(data);
		scrollPane_4.setViewportView(logarea);

		JMenuBar menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);

		JMenu mnExport = new JMenu("Export");
		menuBar.add(mnExport);

		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mnExport.add(mntmSaveAs);

		JMenuItem mntmSendToDevelopers = new JMenuItem("Send to Developers");
		mnExport.add(mntmSendToDevelopers);

		JMenu mnManipulate = new JMenu("Manipulate");
		menuBar.add(mnManipulate);

		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mnManipulate.add(mntmRefresh);

		JMenuItem mntmClear = new JMenuItem("Clear");
		mnManipulate.add(mntmClear);

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
		return "log";
	}

}
