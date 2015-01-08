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
package subterranean.crimson.server.network;

import io.netty.handler.traffic.ChannelTrafficShapingHandler;

import java.net.InetSocketAddress;

import javax.crypto.spec.SecretKeySpec;

import subterranean.crimson.server.Executor;
import subterranean.crimson.server.HostInfo;
import subterranean.crimson.server.Location;
import subterranean.crimson.server.PrimordialConnectionCommands;
import subterranean.crimson.server.Server;
import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.containers.ConnectionProfile;
import subterranean.crimson.server.containers.StreamData;
import subterranean.crimson.server.graphics.BackgroundProgressLights;
import subterranean.crimson.server.graphics.frames.MainScreen;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.EncType;
import subterranean.crimson.universal.IngressBuffer;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Reporter;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.exceptions.NoReplyException;
import subterranean.crimson.universal.translation.T;

public class Connection extends Thread {

	public EncType encryption;
	public SecretKeySpec key;

	public int clientID;

	public boolean connected;
	public IngressBuffer i = new IngressBuffer();

	public ServerHandler sh;
	public String rAddress;
	private Connection parent;

	public StreamData sd = new StreamData();

	public ChannelTrafficShapingHandler g;

	public Connection(SecretKeySpec k, EncType e, ServerHandler handler, ChannelTrafficShapingHandler gtsh, InetSocketAddress inetSocketAddress) {
		g = gtsh;
		rAddress = inetSocketAddress.getAddress().getHostAddress();
		encryption = e;
		key = k;
		sh = handler;
		// give the handler a reference to this object
		sh.parent = this;
	}

	public void disconnect() {
		Server.connections.remove(this);
		MainScreen.window.removeHost(this);
		connected = false;
	}

	public void send(Message m) {
		if (m == null) {
			// why is it sending null
			Logger.add("Attempted to send null message. Something is wrong.");
			Reporter.report("Tried to send null message");
		}
		if (isVirtual()) {
			Message mm = new Message(m.getStreamID(), BMN.PROXY_message);

			Object[] o = { 0, clientID, m };
			mm.auxObject = o;
			sh.send(mm);
		} else {
			sh.send(m);
		}

	}

	public ConnectionProfile getProfile() {
		return (ConnectionProfile) Server.database.retrieveObject(Server.getSettings().getConnectionProfilePointers().get(clientID));
	}

	public void handleMessage(Message msg) {
		Executor.execute(msg, this);

	}

	public void handshake() {

		// get stage number
		int stage = 0;
		try {
			stage = ClientCommands.getStage(this);
		} catch (NoReplyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (stage == 1) {
			Logger.add("Sending Stage2");
			ClientCommands.sendStage2(this);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String waitMessage = T.t("background-connecting") + " " + rAddress;
		BackgroundProgressLights.start(waitMessage);

		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			HostInfo info = null;
			try {
				info = ClientCommands.getInfo(this);
			} catch (NoReplyException e) {
				// This is likely not a crimson connection; terminate
				Logger.add("The connection failed");

				if (Server.getSettings().getNotePolicy().isForeign_connection()) {
					MainScreen.window.addNotification("Connection failed from: " + rAddress + "; The host didnt respond.");

				}
				return;
			}

			// add host to settings
			if (Server.getSettings().getConnectionProfilePointers().containsKey(info.getClientID())) {
				// this client has connected before
				Logger.add("ClientID: " + info.getClientID() + " has connected");

				clientID = info.getClientID();
				info.setLocation(getProfile().info.getLocation());// keep location
				getProfile().info = info;// replace saved info with the new

			} else {
				// new connection
				// assign a id
				int id;

				while (true) {
					// get a random id
					id = ClientCommands.randId();
					if (Server.getSettings().getConnectionProfilePointers().containsKey(id)) {
						continue;
					} else {
						break;
					}
				}
				Logger.add("Generated ID: " + id + " for client");
				ClientCommands.client_assignID(id, this);
				info.setLocation(Location.resolve_remote(rAddress));
				info.setClientID(id);
				clientID = id;
				Server.getSettings().getConnectionProfilePointers().put(id, Server.database.storeObject(new ConnectionProfile()));
				Server.getSettings().getProfile(id).info = info;

			}

			// add it
			Server.connections.add(this);
			MainScreen.window.addHost(this);

		} finally {
			BackgroundProgressLights.stop(waitMessage);
		}

		// connection is now primordial; execute commands
		PrimordialConnectionCommands.run(this);
		connected = true;
	}

	public long getWrittenBytes() {
		return g.trafficCounter().cumulativeWrittenBytes();
	}

	public long getReadBytes() {
		return g.trafficCounter().cumulativeReadBytes();
	}

	public long getWriteSpeed() {
		return g.trafficCounter().lastWriteThroughput();
	}

	public long getReadSpeed() {
		return g.trafficCounter().lastReadThroughput();
	}

	public void imposeReadThrottle(long s) {
		g.setReadLimit(s);
	}

	public void imposeWriteThrottle(long s) {
		g.setWriteLimit(s);
	}

	public boolean isVirtual() {
		return parent != null;
	}

}
