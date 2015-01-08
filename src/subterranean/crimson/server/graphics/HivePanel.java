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



import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.graphics.panels.mainscreen.MainPane;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.RoutingTable;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class HivePanel extends JPanel implements MouseWheelListener {

	private static final long serialVersionUID = 1L;
	public mxGraph graph;
	public Object parent;

	public Object serverVertex;
	private mxGraphComponent graphComponent;

	public HashMap<Object, Integer> vertexes = new HashMap<Object, Integer>();

	public int vX = 80;
	public int vY = 30;

	public HivePanel(final MainPane mainPane) {

		graph = new mxGraph();
		parent = graph.getDefaultParent();

		try {
			graph.getModel().beginUpdate();
			graph.setCellsEditable(false);
			graph.setCellsResizable(false);
			graph.setAllowDanglingEdges(false);
			graph.setConnectableEdges(false);
			graph.setAllowNegativeCoordinates(false);
			serverVertex = graph.insertVertex(parent, null, "\n\n\nServer", 260, 135, 80, 30, "shape=image;image=/subterranean/crimson/server/graphics/icons/server.png");

		} finally {
			graph.getModel().endUpdate();
		}

		graphComponent = new mxGraphComponent(graph);
		this.add(graphComponent);

		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {

				Object cell = graphComponent.getCellAt(e.getX(), e.getY());

				if (cell != null && cell != serverVertex) {

					// get client id
					int id = vertexes.get(cell);
					Connection cc = null;
					for (Connection ccc : Server.connections) {
						if (ccc.getProfile().info.getClientID() == id) {
							// found the connection
							cc = ccc;
							break;

						}
					}
					final Connection c = cc;

					if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {

						JPopupMenu popup = new JPopupMenu();
						JMenuItem control = new JMenuItem();
						control.setText("Control Panel");
						control.addMouseListener(new MouseAdapter() {
							@Override
							public void mousePressed(MouseEvent e) {

								new Thread() {
									public void run() {

										BackgroundProgressLights.start("Opening Control Panel");
										// send to control panel
										DesktopControlPanel frame = new DesktopControlPanel(c);
										Server.controlPanels.add(frame);
										frame.setVisible(true);
										BackgroundProgressLights.stop("Opening Control Panel");
									}
								}.start();
							}

						});
						// select the cell
						graph.setSelectionCell(cell);

						popup.add(control);
						popup.show(graphComponent, e.getX(), e.getY());

					} else {
						// left click
						// dev: refresh
						refreshEdges();
						mainPane.showDetail(c);
					}

				} else {
					mainPane.dropDetail();
				}
			}

		});

	}

	public void addConnection(Connection c) {
		Logger.add("Adding connection to map");
		// generate coordinates for the new vertex
		int xMin = 0;
		int xMax = 600;
		int yMin = 0;
		int yMax = 300;
		int x = 0;
		int y = 0;

		while (true) {
			x = xMin + (int) (Math.random() * ((xMax - xMin) + 1));
			y = yMin + (int) (Math.random() * ((yMax - yMin) + 1));

			Object corner1 = graphComponent.getCellAt(x, y);
			Object corner2 = graphComponent.getCellAt(x + 80, y);
			Object corner3 = graphComponent.getCellAt(x, y + 30);
			Object corner4 = graphComponent.getCellAt(x + 80, y + 30);

			if (corner1 == null && corner2 == null && corner3 == null && corner4 == null) {

				break;
			}

		}
		graph.getModel().beginUpdate();
		String text = "";
		switch (Server.getSettings().getGraphText()) {
		case ("Location"): {
			text = "unknown";
			break;
		}
		case ("Hostname"): {
			text = c.getProfile().info.getHostname();
			break;
		}
		case ("Client Version"): {
			text = c.getProfile().info.getVersion();
			break;
		}
		case ("Activity Status"): {
			text = c.getProfile().info.getActivityStatus();
			break;
		}
		case ("External IP"): {
			text = c.rAddress;
			break;
		}
		case ("Operating System"): {
			text = c.getProfile().info.getOSname();
			break;
		}
		case ("Sent"): {
			text = "" + c.getWrittenBytes();
			break;
		}
		case ("Received"): {
			text = "" + c.getReadBytes();
			break;
		}
		case ("Java Version"): {
			text = "unknown";
			break;
		}
		case ("Internal IP"): {
			text = "do it later";
			break;
		}
		case ("Priveleges"): {
			text = "unknown";
			break;
		}

		}

		try {

			Object v = graph.insertVertex(parent, null, "\n\n\n" + text, x, y, 80, 30, "shape=image;image=/subterranean/crimson/server/graphics/icons/client.png");
			vertexes.put(v, c.getProfile().info.getClientID());

			// all connections start connected to the server
			graph.insertEdge(parent, null, "", serverVertex, v);

		} finally {
			graph.getModel().endUpdate();
		}
	}

	public void refreshEdges() {
		Logger.add("Refreshing edges in graph");

		graph.getModel().beginUpdate();
		try {
			// look at the routing table to determine how to connect hosts
			for (Integer id : RoutingTable.routes.keySet()) {
				// find the vertex
				Object v = null;
				for (Object o : vertexes.keySet()) {
					if (vertexes.get(o) == id) {
						v = o;
						break;
					}
				}
				if (id == RoutingTable.routes.get(id)) {

					// this is a real connection and is direct to the server
					graph.insertEdge(parent, null, "", serverVertex, v);
				} else {
					// connecting two vertexes
					// find the 2nd vertex
					Object v2 = null;
					for (Object o : vertexes.keySet()) {
						if (vertexes.get(o) == RoutingTable.routes.get(id)) {
							v2 = o;
							break;
						}
					}
					graph.insertEdge(parent, null, "", v, v2);

				}

			}
		} finally {
			graph.getModel().endUpdate();
		}
		Logger.add("Refresh Complete");
	}

	public void removeConnection(Connection c) {
		if (c == null) {
			Logger.add("[removeConnection] No null connection to remove!");
			return;
		}
		graph.getModel().beginUpdate();

		Object target = null;

		for (Entry<Object, Integer> entry : vertexes.entrySet()) {
			if (entry.getValue() == c.clientID) {
				// found the connection to remove
				target = entry.getKey();

				break;
			}

		}

		vertexes.remove(target);

		try {
			Object[] ob = new Object[1];
			ob[0] = target;
			graph.removeCells(ob, true);
		} finally {
			graph.getModel().endUpdate();
		}

	}

	/* Spreads out the vertexes so none of them overlap */
	public void spreadOut() {

	}

	public void mouseWheelMoved(MouseWheelEvent e) {

		int notches = e.getWheelRotation();
		if (notches < 0) {
			zoomOut(notches);
		} else {
			zoomIn(notches);
		}

	}

	public void zoomIn(int notches) {
		// make all the vertices bigger by a scale
		Logger.add("Zooming in");

	}

	public void zoomOut(int notches) {
		// make all the vertices smaller by a scale
		Logger.add("Zooming out");

	}

}
