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
package subterranean.crimson.server;



import java.util.ArrayList;

import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.FileLocking;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.translation.T;

public class ServerShutdownHook extends Thread {

	@Override
	public void run() {
		Logger.add(T.t("misc-shutdownhook"));

		// send disconnection notices to all clients
		ArrayList<Connection> temp = Server.connections;
		for (Connection c : temp) {
			ClientCommands.client_disconnect(c);
		}

		FileLocking.unlock();

		Server.database.close();

	}

}
