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



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import subterranean.crimson.universal.Logger;

public class Location {

	private static final short timeout = 3000;

	private static float distFrom(float lat1, float lng1, float lat2, float lng2) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = (Math.sin(dLat / 2) * Math.sin(dLat / 2)) + (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		int meterConversion = 1609;

		return (float) (dist * meterConversion);
	}

	public static HashMap<String, String> resolve(String ipAddr) {
		HashMap<String, String> info = new HashMap<String, String>();

		// get info from webserver
		BufferedReader in = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			String u = "http://freegeoip.net/xml/" + ipAddr;
			// Logger.add("URL: " + u);
			URL get = new URL(u);

			in = new BufferedReader(new InputStreamReader(get.openStream()));
			String q = null;
			while ((q = in.readLine()) != null) {
				result.add(q.trim());
			}

		} catch (IOException e) {
			// could have been a 503. What ever the reason, the location data could not be
			// resolved

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {

				}
			}
		}

		// parse the data

		String ip = null;
		String countryCode = null;
		String countryName = null;
		String regionCode = null;
		String regionName = null;
		String city = null;
		String zipCode = null;
		String latitude = null;
		String longitude = null;
		String metroCode = null;
		String areaCode = null;

		for (String s : result) {
			// Logger.add("Parsing line: " + s);
			if (s.contains("<Ip")) {
				if (s.equals("<Ip/>")) {
					ip = "";
				} else {
					ip = s.split("<Ip>")[1].split("</Ip>")[0];
				}
				// Logger.add("IP: " + ip);
			} else if (s.contains("<CountryCode")) {
				if (s.equals("<CountryCode/>")) {
					countryCode = "";
				} else {
					countryCode = s.split("<CountryCode>")[1].split("</CountryCode>")[0];
				}
				// Logger.add("CountryCode: " + countryCode);
			} else if (s.contains("<CountryName")) {
				if (s.equals("<CountryName/>")) {
					countryName = "";
				} else {
					countryName = s.split("<CountryName>")[1].split("</CountryName>")[0];
				}
				// Logger.add("CountryName: " + countryName);
			} else if (s.contains("<RegionCode")) {
				if (s.equals("<RegionCode/>") || s.equals("<RegionCode></RegionCode>")) {
					regionCode = "";
				} else {
					regionCode = s.split("<RegionCode>")[1].split("</RegionCode>")[0];
				}
				// Logger.add("RegionCode: " + regionCode);
			} else if (s.contains("<RegionName")) {
				if (s.equals("<RegionName/>") || s.equals("<RegionName></RegionName>")) {
					regionName = "";
				} else {
					regionName = s.split("<RegionName>")[1].split("</RegionName>")[0];
				}
				// Logger.add("RegionName: " + regionName);
			} else if (s.contains("<City")) {
				if (s.equals("<City/>") || s.equals("<City></City>")) {
					city = "";
				} else {
					city = s.split("<City>")[1].split("</City>")[0];
				}
				// Logger.add("City: " + city);
			} else if (s.contains("<ZipCode")) {
				if (s.equals("<ZipCode/>") || s.equals("<ZipCode></ZipCode>")) {
					zipCode = "";
				} else {
					zipCode = s.split("<ZipCode>")[1].split("</ZipCode>")[0];
				}
				// Logger.add("ZipCode: " + zipCode);
			} else if (s.contains("<Latitude")) {
				if (s.equals("<Latitude/>")) {
					latitude = "";
				} else {
					latitude = s.split("<Latitude>")[1].split("</Latitude>")[0];
				}
				// Logger.add("Latitude: " + latitude);
			} else if (s.contains("<Longitude")) {
				if (s.equals("<Longitude/>")) {
					longitude = "";
				} else {
					longitude = s.split("<Longitude>")[1].split("</Longitude>")[0];
				}
				// Logger.add("Longitude: " + longitude);
			} else if (s.contains("<MetroCode")) {
				if (s.equals("<MetroCode/>") || s.equals("<MetroCode></MetroCode>")) {
					metroCode = "";
				} else {
					metroCode = s.split("<MetroCode>")[1].split("</MetroCode>")[0];
				}
				// Logger.add("MetroCode: " + metroCode);
			} else if (s.contains("<AreaCode")) {
				if (s.equals("<AreaCode/>") || s.equals("<AreaCode></AreaCode>")) {
					areaCode = "";
				} else {
					areaCode = s.split("<AreaCode>")[1].split("</AreaCode>")[0];
				}
				// Logger.add("AreaCode: " + areaCode);
			}

		}

		// add these to the hashmap
		try {
			info.put("Ip", ip);
		} catch (Exception e) {
			info.put("Ip", "unknown");
		}
		try {
			info.put("CountryCode", countryCode.toLowerCase());
		} catch (Exception e) {
			info.put("CountryCode", "unknown");
		}
		try {
			info.put("CountryName", countryName);
		} catch (Exception e) {
			info.put("CountryCode", "unknown");
		}
		try {
			info.put("RegionCode", regionCode);
		} catch (Exception e) {
			info.put("RegionCode", "unknown");
		}
		try {
			info.put("RegionName", regionName);
		} catch (Exception e) {
			info.put("RegionName", "unknown");
		}
		try {
			info.put("City", city);
		} catch (Exception e) {
			info.put("City", "unknown");
		}
		try {
			info.put("ZipCode", zipCode);
		} catch (Exception e) {
			info.put("ZipCode", "unknown");
		}
		try {
			info.put("Latitude", latitude);
		} catch (Exception e) {
			info.put("Latitude", "unknown");
		}
		try {
			info.put("Longitude", longitude);
		} catch (Exception e) {
			info.put("Longitude", "unknown");
		}
		try {
			info.put("MetroCode", metroCode);
		} catch (Exception e) {
			info.put("MetroCode", "unknown");
		}
		try {
			info.put("AreaCode", areaCode);
		} catch (Exception e) {
			info.put("AreaCode", "unknown");
		}

		return info;
	}

	public static HashMap<String, String> resolve_remote(String remoteIP) {

		if (remoteIP.startsWith("127")) {
			// local and remote are actually the same machine
			return Server.locationData;

		}
		// Logger.add("Resolving location data for: " + remoteIP);
		HashMap<String, String> info = new HashMap<String, String>();
		HashMap<String, String> linfo = Server.locationData;

		if ((Server.externalIP == null) || (remoteIP == null)) {
			// this machine is likely not connected to the internet
			return info;
		}

		if (Server.externalIP.equals(remoteIP)) {
			// same
			return Server.locationData;
		} else {
			info = resolve(remoteIP);

			try {
				info.put("Distance", "" + distFrom(Float.parseFloat(info.get("Latitude")), Float.parseFloat(info.get("Longitude")), Float.parseFloat(linfo.get("Latitude")), Float.parseFloat(linfo.get("Longitude"))));
			} catch (NumberFormatException e) {
				// could be unknown
				Logger.add("Could not determine coordinates for a location");
				info.put("Distance", "unknown");
			} catch (NullPointerException e) {
				// the info could not be resolved.. No big deal
				info.put("Distance", "unknown");
			}
		}
		return info;

	}

}
