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
package subterranean.crimson.server.graphics.panels.mainscreen;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.graphics.BackgroundProgressLights;
import subterranean.crimson.server.graphics.DesktopControlPanel;
import subterranean.crimson.server.graphics.HivePanel;
import subterranean.crimson.server.graphics.MobileControlPanel;
import subterranean.crimson.server.graphics.frames.MainScreen;
import subterranean.crimson.server.graphics.models.table.HostTableModel;
import subterranean.crimson.server.graphics.renderers.table.HostTableRenderer;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.exceptions.NoReplyException;

public class Main extends JPanel {

	private static final long serialVersionUID = 1L;
	public HostTableModel h;
	private JPanel cards;
	public HivePanel hp;
	public static MainPane mainPane;

	public void updateHeaders() {
		h.columnNames = Server.getSettings().getListHeaders();
		h.fireTableStructureChanged();

	}

	public void switchPanes() {
		CardLayout cardLayout = (CardLayout) cards.getLayout();
		cardLayout.next(cards);
	}

	public void listPane() {
		CardLayout cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, "LIST");
	}

	public void graphPane() {
		CardLayout cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, "GRAPH");
	}

	public Main(final MainPane mp) {
		mainPane = mp;
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		cards = new JPanel(new CardLayout());
		add(cards);
		cards.setBorder(null);
		JPanel listCard = new JPanel(null);
		listCard.setLayout(new BorderLayout(0, 0));
		listCard.add(scrollPane);
		JPanel graphCard = new JPanel(null);
		graphCard.setLayout(new BorderLayout(0, 0));
		hp = new HivePanel(mainPane);
		graphCard.add(hp);
		hp.setLayout(new CardLayout(0, 0));

		cards.add(listCard, "LIST");
		cards.add(graphCard, "GRAPH");

		final JTable table = new JTable();
		table.setFillsViewportHeight(true);
		table.setShowVerticalLines(false);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// get source of click
				JTable source = (JTable) e.getSource();
				final int sourceRow = source.rowAtPoint(e.getPoint());
				if (sourceRow == -1) {
					source.clearSelection();
					mainPane.dropDetail();
					return;
				}
				// select row
				if (!source.isRowSelected(sourceRow)) {
					source.changeSelection(sourceRow, 0, false, false);
				}
				final Connection selected = h.getHostConnection(sourceRow);

				if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {

					// right click on table
					JPopupMenu popup = new JPopupMenu();
					JMenuItem control = new JMenuItem();
					control.setText("Control Panel");
					control.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/C.png")));
					control.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {

							new Thread() {
								public void run() {
									BackgroundProgressLights.start("Opening Control Panel");

									try {
										// drop the detail
										mainPane.dropDetail();

										// determine what kind of control panel to open

										if (selected.getProfile().info.getOSname().equals("Android")) {
											final MobileControlPanel mcp = new MobileControlPanel(selected);
											Server.controlPanels.add(mcp);
											mcp.setVisible(true);

										} else {
											DesktopControlPanel frame = new DesktopControlPanel(selected);
											Server.controlPanels.add(frame);
											frame.setVisible(true);
										}
									} finally {
										BackgroundProgressLights.stop("Opening Control Panel");
									}

								}
							}.start();

						}

					});
					popup.add(control);

					JMenu quick = new JMenu();
					quick.setText("Quick Commands");
					quick.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/Q.png")));
					popup.add(quick);

					JMenuItem poweroff = new JMenuItem();
					poweroff.setText("Shutdown");
					poweroff.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/S.png")));
					poweroff.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {

							new Thread() {
								public void run() {
									ClientCommands.client_shutdown(selected);
								}
							}.start();

						}

					});
					quick.add(poweroff);

					JMenuItem restart = new JMenuItem();
					restart.setText("Restart");
					restart.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/R.png")));
					restart.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {

							new Thread() {
								public void run() {
									ClientCommands.client_restart(selected);
								}
							}.start();

						}

					});
					quick.add(restart);

					JMenuItem refresh = new JMenuItem();
					refresh.setText("Refresh Information");
					refresh.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/R.png")));
					refresh.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {

							new Thread() {
								public void run() {
									try {
										ClientCommands.client_refresh(selected);
									} catch (NoReplyException e) {
										MainScreen.window.addNotification("Failed to refresh");
									}
								}
							}.start();

						}

					});
					quick.add(refresh);

					popup.show(table, e.getX(), e.getY());

				} else {
					// open up the detail
					mainPane.showDetail(selected);
				}
			}
		});
		h = new HostTableModel();
		// set renderer
		table.setDefaultRenderer(Object.class, new HostTableRenderer());
		table.setModel(h);
		scrollPane.setViewportView(table);

	}

}
