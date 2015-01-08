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
package subterranean.crimson.server.containers;

import java.io.Serializable;

import subterranean.crimson.server.graphics.dmodules.NetworkUsage;
import subterranean.crimson.server.graphics.dmodules.SystemInformation;
import subterranean.crimson.server.graphics.dmodules.Thumbnail;

public class DModuleMemory implements Serializable {

	private static final long serialVersionUID = 1L;
	public Thumbnail thumbnail;
	public SystemInformation sysInfo;
	public NetworkUsage netUsage;

	public DModuleMemory() {
		// add default modules
		sysInfo = new SystemInformation();
		sysInfo.weight = -5;

	}

}
