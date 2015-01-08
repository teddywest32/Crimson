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
package subterranean.crimson.server.commands;



import java.util.ArrayList;
import java.util.Random;

import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.containers.AndroidContact;
import subterranean.crimson.universal.containers.CallLog;

public enum AndroidCommands {
	;

	public static int randId() {
		return new Random().nextInt(Integer.MAX_VALUE);
	}

	public static ArrayList<AndroidContact> getContacts(Connection c) {
		return null;
		// Logger.add("Sending contact request");
		// Message mreq = new Message(randId(), MN.ANDROID_gatherContacts);
		// c.send(mreq);
		// Message mres = c.i.getId(mreq.getStreamID());
		// return (ArrayList<AndroidContact>) mres.auxObject[0];
	}

	public static ArrayList<CallLog> getCallLog(Connection c) {
		return null;
		// Logger.add("Sending call log request");
		// Message mreq = new Message(randId(), MN.ANDROID_gatherCallLog);
		// c.send(mreq);
		// Message mres = c.i.getId(mreq.getStreamID());
		// return (ArrayList<CallLog>) mres.auxObject[0];
	}

	public static void writeCallLog(Connection c, CallLog cl) {
		// Logger.add("Writing to call log");
		// Message mreq = new Message(randId(), MN.ANDROID_writeCallLog, cl);
		// c.send(mreq);
	}

}
