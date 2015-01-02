package subterranean.crimson.server.graphics.dmodules;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import subterranean.crimson.server.graphics.DModule;
import subterranean.crimson.server.graphics.graphs.NetworkLineGraph;
import subterranean.crimson.server.graphics.panels.mainscreen.Main;
import subterranean.crimson.server.network.Connection;

public class NetworkUsage extends DModule {

	private static final long serialVersionUID = 1L;

	NetworkLineGraph nlc;

	public NetworkUsage() {
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Network Usage", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		nlc = new NetworkLineGraph();
		add(nlc);
		setSize(200, 50);

	}

	public void updateGraphics() {
		Main.mainPane.repaint();
		Main.mainPane.validate();
	}

	@Override
	public void changeTarget(Connection c) {
		if (c != null) {
			nlc = new NetworkLineGraph(c);
		} else {
			nlc = null;
		}
	}

	@Override
	public int compareTo(Object o) {
		return (weight - ((DModule) o).weight);
	}

}
