package subterranean.crimson.server.containers;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.spec.SecretKeySpec;

import subterranean.crimson.server.HostInfo;
import subterranean.crimson.universal.containers.KeyloggerLog;
import subterranean.crimson.universal.containers.SystemMessage;

public class ConnectionProfile implements Serializable {

	private static final long serialVersionUID = 1L;
	public ArrayList<String> cliplog;
	public HashMap<String, String> details;
	public boolean encrypted;
	public HostInfo info;
	public SecretKeySpec key;

	public KeyloggerLog log;
	public ArrayList<SystemMessage> messages;

	public ConnectionProfile() {
		messages = new ArrayList<SystemMessage>();
		cliplog = new ArrayList<String>();
		log = new KeyloggerLog();
		info = null;

		key = null;
		encrypted = false;

	}

}
