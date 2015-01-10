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
package subterranean.crimson.server;

import java.io.Serializable;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.ImageIcon;

import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.NativeSystem.Arch;

public class HostInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	// status fields
	private String currentClientOperation;
	private String activityStatus;
	private boolean keylogging;

	// basic information
	private int clientID;
	private String hostname;
	private String version;
	private Arch systemArch;
	private Arch jreArch;
	private String username;
	private String OSname;
	private String OSversion;
	private Boolean[] power;
	private String language;
	private String timezone;
	private String encoding;
	private Date systemStartDate;
	private Date jreStartDate;
	private String jreVersion;
	private String jreDirectory;
	private Enumeration<NetworkInterface> adapters;
	private String virtualization;

	// advanced
	private HashMap<String, String> env;
	private HashMap<String, String> location;
	private ImageIcon preview;

	// mobile specific
	private String phoneNumber;
	private String IMEI;
	private String operatorName;
	private String SIM_operator_code;
	private String SIM_serial;

	public HostInfo(HashMap<String, Object> info) {
		currentClientOperation = "idle";
		try {

			// basic information
			clientID = (Integer) info.get("client_id");
			activityStatus = (String) info.get("activityStatus");
			hostname = (String) info.get("hostname");
			username = (String) info.get("username");
			version = (String) info.get("version");
			systemArch = (Arch) info.get("systemArch");
			jreArch = (Arch) info.get("jreArch");
			power = (Boolean[]) info.get("power_tests");
			OSname = (String) info.get("operating_system");
			OSversion = (String) info.get("os_version");
			jreDirectory = (String) info.get("JREdir");
			jreVersion = (String) info.get("JREversion");
			virtualization = (String) info.get("virtualization");
			adapters = (Enumeration<NetworkInterface>) info.get("adapters");
			language = (String) info.get("language");
			timezone = (String) info.get("timezone");
			encoding = (String) info.get("encoding");
			keylogging = (Boolean) info.get("keylogging");
			jreStartDate = new Date((new Date().getTime()) - (Long) info.get("jvmUptime"));
			systemStartDate = new Date((new Date().getTime()) - (Long) info.get("sysUptime"));

			// advanced information
			preview = (ImageIcon) info.get("preview");

			// mobile specific
			phoneNumber = (String) info.get("phone_number");
			IMEI = (String) info.get("IMEI");
			operatorName = (String) info.get("operator_name");
			SIM_operator_code = (String) info.get("SIM_operator_code");
			SIM_serial = (String) info.get("SIM_serial");

		} catch (Exception e) {
			Logger.add("Failed to parse hostinfo");
		}
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public String getCurrentClientOperation() {
		return currentClientOperation;
	}

	public void setCurrentClientOperation(String currentClientOperation) {
		this.currentClientOperation = currentClientOperation;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Arch getSystemArch() {
		return systemArch;
	}

	public void setSystemArch(Arch systemArch) {
		this.systemArch = systemArch;
	}

	public Arch getJreArch() {
		return jreArch;
	}

	public void setJreArch(Arch jreArch) {
		this.jreArch = jreArch;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOSname() {
		return OSname;
	}

	public void setOSname(String oSname) {
		OSname = oSname;
	}

	public String getOSversion() {
		return OSversion;
	}

	public void setOSversion(String oSversion) {
		OSversion = oSversion;
	}

	public Boolean[] getPower() {
		return power;
	}

	public void setPower(Boolean[] power) {
		this.power = power;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getSystemStartDate() {
		return systemStartDate;
	}

	public void setSystemStartDate(Date systemStartDate) {
		this.systemStartDate = systemStartDate;
	}

	public Date getJreStartDate() {
		return jreStartDate;
	}

	public void setJreStartDate(Date jreStartDate) {
		this.jreStartDate = jreStartDate;
	}

	public String getJreVersion() {
		return jreVersion;
	}

	public void setJreVersion(String jreVersion) {
		this.jreVersion = jreVersion;
	}

	public String getJreDirectory() {
		return jreDirectory;
	}

	public void setJreDirectory(String jreDirectory) {
		this.jreDirectory = jreDirectory;
	}

	public Enumeration<NetworkInterface> getAdapters() {
		return adapters;
	}

	public void setAdapters(Enumeration<NetworkInterface> adapters) {
		this.adapters = adapters;
	}

	public String getVirtualization() {
		return virtualization;
	}

	public boolean isVirtualized() {
		// come up with a yes or no
		String[] bits = getVirtualization().split("");
		if (bits[0].equals("1")) {
			// process name check

		}
		if (bits[1].equals("1")) {
			// class name check

		}
		if (bits[2].equals("1")) {
			// vmx check

		}
		if (bits[3].equals("1")) {
			// cpu id check

		}
		if (bits[4].equals("1")) {
			// cpu core check

		}
		if (bits[5].equals("1")) {
			// registry check

		}
		if (bits[6].equals("1")) {
			// devices check

		}
		if (bits[7].equals("1")) {
			// drivers check

		}

		return false;
	}

	public void setVirtualization(String virtualization) {
		this.virtualization = virtualization;
	}

	public HashMap<String, String> getEnv() {
		return env;
	}

	public void setEnv(HashMap<String, String> env) {
		this.env = env;
	}

	public HashMap<String, String> getLocation() {
		return location;
	}

	public void setLocation(HashMap<String, String> location) {
		this.location = location;
	}

	public ImageIcon getPreview() {
		return preview;
	}

	public void setPreview(ImageIcon preview) {
		this.preview = preview;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getSIM_operator_code() {
		return SIM_operator_code;
	}

	public void setSIM_operator_code(String sIM_operator_code) {
		SIM_operator_code = sIM_operator_code;
	}

	public String getSIM_serial() {
		return SIM_serial;
	}

	public void setSIM_serial(String sIM_serial) {
		SIM_serial = sIM_serial;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean isKeylogging() {
		return keylogging;
	}

	public void setKeylogging(boolean keylogging) {
		this.keylogging = keylogging;
	}

}
