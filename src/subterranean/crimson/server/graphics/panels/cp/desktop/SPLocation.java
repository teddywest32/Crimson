package subterranean.crimson.server.graphics.panels.cp.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.DataViewer;
import subterranean.crimson.server.graphics.panels.cp.CPanel;

public class SPLocation extends CPanel {

	private static final long serialVersionUID = 1L;

	public SPLocation(ControlPanel cp) {
		HashMap<String, String> l = cp.c.getProfile().info.getLocation();
		HashMap<String, String> g = null;
		ArrayList<String[]> l2 = new ArrayList<String[]>();
		if (l == null) {
			l2.add(new String[] { "Unresolved", "Unresolved" });
		} else {
			for (String key : l.keySet()) {
				l2.add(new String[] { key, l.get(key) });
			}
		}

		ArrayList<String[]> g2 = new ArrayList<String[]>();
		if (g == null) {
			g2.add(new String[] { "Unresolved", "Unresolved" });
		} else {
			for (String key : g.keySet()) {
				g2.add(new String[] { key, g.get(key) });
			}
		}
		setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Information", null, panel, null);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel ip_panel = new JPanel();
		panel.add(ip_panel);
		ip_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Information Deduced from External IP Address", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ip_panel.setLayout(new BorderLayout(0, 0));

		DataViewer dv = new DataViewer();
		dv.setList(l2);
		dv.setHeaders(new String[] { "Property", "Value" });
		ip_panel.add(dv);

		JPanel gps_panel = new JPanel();
		panel.add(gps_panel);
		gps_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Information Deduced from GPS Device", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		gps_panel.setLayout(new BorderLayout(0, 0));

		DataViewer dv2 = new DataViewer();
		dv2.setList(g2);
		dv2.setHeaders(new String[] { "Property", "Value" });
		gps_panel.add(dv2);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Map", null, panel_1, null);

		JXMapViewer mapViewer = new JXMapViewer();
		// Create a TileFactoryInfo for OpenStreetMap
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		mapViewer.setTileFactory(tileFactory);
		// Use 8 threads in parallel to load the tiles
		tileFactory.setThreadPoolSize(8);
		// Set the focus
		GeoPosition position;
		try {
			position = new GeoPosition(Double.parseDouble(cp.c.getProfile().info.getLocation().get("Latitude")), Double.parseDouble(cp.c.getProfile().info.getLocation().get("Longitude")));
		} catch (Exception e) {
			position = new GeoPosition(0, 0);
		}
		panel_1.setLayout(new BorderLayout(0, 0));
		mapViewer.setZoom(2);
		mapViewer.setAddressLocation(position);

		panel_1.add(mapViewer);
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
		return "location";
	}
}
