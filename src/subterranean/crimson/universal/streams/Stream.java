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
package subterranean.crimson.universal.streams;

import java.util.Timer;
import java.util.TimerTask;

import subterranean.crimson.permajar.stage1.network.Communications;
import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.StreamStore;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.Message;

/**
 * @author globalburning This class is a base for full duplex network streams
 */
public abstract class Stream {

	private static final long serialVersionUID = 1L;
	protected Parameters param;
	private Timer timer = new Timer();
	protected Connection c;// for servers

	private TimerTask sendTask = new TimerTask() {
		@Override
		public void run() {
			send();
		}

	};

	public Stream(Parameters p) {
		param = p;
		param.setSender(!param.isSender());// switch the sender field
		StreamStore.addStream(this);
	}

	public Stream(Parameters p, Connection connection) {
		param = p;
		c = connection;
		StreamStore.addStream(this);
	}

	public void start() {
		if (isServer()) {
			c.send(new Message(Utilities.randId(), BMN.STREAMCONTROL_start, param));
		}
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		timer.schedule(sendTask, 0, param.getPeriod());
	}

	public short getStreamID() {
		return param.getStreamID();
	}
	
	public void sendMessage(Message m) {

		if (c == null) {
			Communications.sendHome(m);

		} else {
			c.send(m);

		}
	}

	public void stop() {
		timer.cancel();
		if (isServer()) {
			ClientCommands.stopStream(c, getStreamID());

		}

	}

	public void pause() {
		timer.cancel();
		if (isServer()) {
			c.send(new Message(Utilities.randId(), BMN.STREAMCONTROL_pause, param.getStreamID()));
		} else {

		}
	}

	public void unpause() {
		if (isServer()) {
			c.send(new Message(Utilities.randId(), BMN.STREAMCONTROL_pause, param.getStreamID()));
		} else {

		}
		timer.schedule(sendTask, 0, param.getPeriod());
	}

	protected boolean isServer() {
		return c != null;
	}

	/**
	 * Called when data arrives
	 */
	public abstract void received(Message m);

	/**
	 * Called to pump the stream
	 */
	public abstract void send();

}
