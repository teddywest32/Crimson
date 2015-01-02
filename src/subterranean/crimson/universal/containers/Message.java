package subterranean.crimson.universal.containers;

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

import subterranean.crimson.universal.Version;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this interaction
	 */
	private int streamID;

	/**
	 * The name of the action/command
	 */
	private byte name;

	/**
	 * Storage object for data needing to be transmitted
	 */
	public Object[] auxObject;

	public Message(int i, byte n) {
		setStreamID(i);
		name = n;

	}

	public Message(int i, byte n, Object o) {
		setStreamID(i);
		name = n;
		auxObject = new Object[1];
		auxObject[0] = o;
	}

	public byte getName() {
		return name;
	}

	public int getStreamID() {
		return streamID;
	}

	public void setStreamID(int streamID) {
		this.streamID = streamID;
	}

}
