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
import java.util.Date;

public class CallLog implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int type;
	private Date date;
	private long duration;
	private String phoneNumber;
	private String name;
	// which to view
	private String view = "Date";
	private String newCall;

	public String getNew() {
		return newCall;
	}

	public void setNew(String s) {
		newCall = s;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			this.name = "Unknown";
		} else {
			this.name = name;
		}

	}

	public String toString() {
		switch (view) {
		case ("Name"): {
			return getName();
		}
		case ("Number"): {
			return getPhoneNumber();
		}
		case ("Date"): {
			return getDate().toString();
		}
		case ("Duration"): {
			return "" + getDuration();
		}

		}

		// everything else failed
		return super.toString();

	}

}
