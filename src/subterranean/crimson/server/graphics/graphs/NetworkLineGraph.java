package subterranean.crimson.server.graphics.graphs;

import java.awt.event.ActionEvent;

import org.jfree.data.time.Millisecond;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.Logger;

public class NetworkLineGraph extends LineChart {

	private static final long serialVersionUID = 1L;

	Connection c;

	public NetworkLineGraph(Connection con) {
		c = con;

	}

	public NetworkLineGraph() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (c != null) {
			s1.add(new Millisecond(), c.g.trafficCounter().lastReadThroughput());
		} else {
			s1.add(new Millisecond(), Server.getThroughput());
		}

	}

}
