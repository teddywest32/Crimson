package subterranean.crimson.server.sdk;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.containers.ConnectionProfile;
import subterranean.crimson.server.containers.PluginEntry;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.server.sdk.interfaces.CPFrame;
import subterranean.crimson.server.sdk.interfaces.GPFrame;
import subterranean.crimson.server.sdk.interfaces.SPFrame;
import subterranean.crimson.universal.containers.PluginMessage;

/*
 * 	Crimson Extended Administration Tool
 *  Copyright (C) 2015 Subterranean Security
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

/**
 * @author globalburning Provides one place for many common tasks plugins need to preform
 *
 */
public class API {

	/**
	 * Returns the PluginSettings object for the given packagename
	 * 
	 * @param pac
	 * @return
	 */
	public static PluginSettings getSettings(String pac) {
		return Server.getSettings().getPluginSettings(pac);
	}

	/**
	 * Sends a message to the given client
	 * 
	 * @param clientID
	 *            ID of client
	 * @param pm
	 *            Message
	 */
	public static void sendMessage(int clientID, PluginMessage pm) {
		for (Connection c : Server.connections) {
			if (c.clientID == clientID) {
				c.send(pm);
				break;
			}
		}
		return;
	}

	/**
	 * Sends a message to the given client and returns the response
	 * 
	 * @param clientID
	 *            ID of client
	 * @param pm
	 *            Message
	 * @return Response
	 */
	public static PluginMessage sendMessageGetResponse(int clientID, PluginMessage pm) {
		for (Connection c : Server.connections) {
			if (c.clientID == clientID) {
				c.send(pm);
				return (PluginMessage) c.i.getId(pm.getStreamID());
			}
		}
		return null;
	}

	public static void registerEvent(String pac, Runnable r, EventEnum e) {

	}

	/**
	 * Gets the Connection profile for the given clientID
	 * 
	 * @param clientID
	 * @return
	 */
	public static ConnectionProfile getCP(int clientID) {
		return Server.getSettings().getProfile(clientID);
	}

}
