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
