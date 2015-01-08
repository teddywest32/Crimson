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

import javax.crypto.spec.SecretKeySpec;

import subterranean.crimson.universal.EncType;
import subterranean.crimson.universal.Path;
import subterranean.crimson.universal.PortSpec;

public class Options implements Serializable {

	private static final long serialVersionUID = 1L;
	public EncType encryptionType;
	public SecretKeySpec key;
	public boolean melt;
	public boolean crimsonDNS;
	public String crimsonDNS_user;
	public String crimsonDNS_hash;

	public AutostartWINDOWS win_autostart_method;
	public String unix_autostart_method;

	public boolean ssl;
	
	public AddressSpec primaryAddress;
	public AddressSpec backupAddress;

	public PortSpec primaryPort;
	public PortSpec backupPort;

	public long connectPeriod = 60000;

	public boolean packed_feature_keylogger;

	public Path windows;
	public Path linux;
	public Path osx;

	// for bootstrapper
	public Date permaJarCreationDate;
	public boolean waitForIDLE;
	public long executionDelay;
	public boolean handleErrors;
	public InstallationDetails details;// gets populated by bootstrapper

}
