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
