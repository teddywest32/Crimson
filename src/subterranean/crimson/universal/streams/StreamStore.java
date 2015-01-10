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
package subterranean.crimson.universal.streams;



import java.util.ArrayList;

import subterranean.crimson.universal.streams.Stream;

public enum StreamStore {
	;
	private static volatile ArrayList<Stream> streams = new ArrayList<Stream>();

	public static Stream getStream(int id) {
		for (Stream s : streams) {
			if (s.getStreamID() == id) {
				return s;
			}
		}
		return null;
	}

	public static void removeStream(int id) {
		for (int i = 0; i < streams.size(); i++) {
			if (streams.get(i).getStreamID() == id) {
				streams.remove(i).stop();
				return;
			}
		}
	}

	public static void addStream(Stream s) {
		streams.add(s);
	}

}
