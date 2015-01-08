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

public class AndroidContact implements Serializable {

	private static final long serialVersionUID = 1L;

	private int addressType;
	private String city;
	private String country;
	private ArrayList<String> emailAddresses;
	private long id;
	private ArrayList<String> instantMessaging;
	private long lastContactTime;
	private String name;
	private ArrayList<String> notes;
	private String organization;
	private String organizationPosition;
	private ArrayList<String> phoneNumbers;
	private byte[] photo;
	private String region;
	private boolean starred;
	private String street;
	private int timesContacted;
	private String zipcode;

	// which field to view
	private String view = "Name";

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public int getAddressType() {
		return addressType;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public ArrayList<String> getEmailAddresses() {
		return emailAddresses;
	}

	public long getId() {
		return id;
	}

	public ArrayList<String> getInstantMessaging() {
		return instantMessaging;
	}

	public long getLastContactTime() {
		return lastContactTime;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getNotes() {
		return notes;
	}

	public String getOrganization() {
		return organization;
	}

	public String getOrganizationPosition() {
		return organizationPosition;
	}

	public ArrayList<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public String getRegion() {
		return region;
	}

	public String getStreet() {
		return street;
	}

	public int getTimesContacted() {
		return timesContacted;
	}

	public String getZipcode() {
		return zipcode;
	}

	public boolean isStarred() {
		return starred;
	}

	public void setAddressType(int addressType) {
		this.addressType = addressType;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setEmailAddresses(ArrayList<String> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setInstantMessaging(ArrayList<String> instantMessaging) {
		this.instantMessaging = instantMessaging;
	}

	public void setLastContactTime(long lastContactTime) {
		this.lastContactTime = lastContactTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public void setOrganizationPosition(String organizationPosition) {
		this.organizationPosition = organizationPosition;
	}

	public void setPhoneNumbers(ArrayList<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setStarred(boolean starred) {
		this.starred = starred;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setTimesContacted(int timesContacted) {
		this.timesContacted = timesContacted;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String toString() {
		switch (view) {
		case ("Name"): {
			return getName();
		}
		case ("ZipCode"): {
			return getZipcode();
		}

		}

		// everything else failed
		return super.toString();

	}

}
