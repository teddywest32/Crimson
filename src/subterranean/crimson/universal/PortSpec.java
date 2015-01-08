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
package subterranean.crimson.universal;



import java.io.Serializable;
import java.net.ServerSocket;

public class PortSpec implements Serializable {

	private static final long serialVersionUID = 1L;
	private int port;
	private boolean valid;
	private boolean ephemeral;

	private String attempt;

	public PortSpec(int p) {
		port = p;
		if (p < 1 || p > 65535) {
			valid = false;
		} else {
			valid = true;
		}
		if (p > 49152 && p < 65535) {
			ephemeral = true;
		} else {
			ephemeral = false;
		}
	}

	public PortSpec(String p) {
		attempt = p;
		try {
			port = Integer.parseInt(p);
		} catch (NumberFormatException w) {
			valid = false;
			return;
		}
		if (port < 1 || port > 65535) {
			valid = false;
		} else {
			valid = true;
		}
		if (port > 49152 && port < 65535) {
			ephemeral = true;
		} else {
			ephemeral = false;
		}
	}

	public String getAttempt() {
		return attempt;
	}

	public boolean isOpen() {
		if (!valid) {
			return false;
		}
		ServerSocket sock = null;
		try {
			sock = new ServerSocket(port);
			sock.close();
			return true;
		} catch (Throwable ex) {
			return false;
		}
	}

	public boolean isEphemeral() {
		return ephemeral;
	}

	public boolean isValid() {
		return valid;
	}

	public int getPort() {
		return port;
	}

}
