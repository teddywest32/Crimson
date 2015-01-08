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
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressSpec implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;
	private String locator;

	public String getLocator() {
		return locator;
	}

	public AddressSpec(String in) {
		if (validIP(in)) {
			// ip address
			type = "IP";
			locator = in;
		} else {
			if (validDNS(in)) {
				type = "DNS";
				locator = in;
			} else {
				type = "INVALID";
			}

		}
	}

	public boolean validIP(final String ip) {
		Matcher matcher = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$").matcher(ip);
		return matcher.matches();
	}

	public boolean validDNS(final String dns) {
		Matcher matcher = Pattern.compile("^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$").matcher(dns);
		return matcher.matches();
	}

	public String getIPAddress() {
		if (type.equals("IP")) {
			return locator;
		} else {
			try {
				return InetAddress.getByName(locator).getHostAddress();
			} catch (Exception e) {
				return null;
			}
		}
	}

	public boolean isValid() {
		if (type.equals("INVALID")) {
			return false;
		}
		return true;
	}

}
