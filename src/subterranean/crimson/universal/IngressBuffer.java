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
package subterranean.crimson.universal;

import java.util.ArrayList;

import subterranean.crimson.universal.containers.Message;

public class IngressBuffer {
	public ArrayList<Message> ingress;

	public IngressBuffer() {
		ingress = new ArrayList<Message>();
	}

	public void add(Message r) {
		ingress.add(r);
	}

	public Message getId(int id) {
		int slept = 0;
		while (true) {

			if (ingress.size() == 0) {
				// wait
			} else {
				// find one with id
				for (Message c : ingress) {
					if (c.getStreamID() == id) {
						// found right one
						ingress.remove(c);
						return c;
					}
				}
				// wait
			}
			if (slept > 5) {
				Logger.add("No response received for id: " + id + " Ingress Buffer size: " + ingress.size());

				return null;
			}
			// sleep
			slept++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Logger.add("Terminated while waiting for response from client");
			}
		}
	}

}
