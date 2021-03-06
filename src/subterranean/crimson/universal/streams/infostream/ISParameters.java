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
package subterranean.crimson.universal.streams.infostream;

import subterranean.crimson.universal.streams.Parameters;

public class ISParameters extends Parameters {

	private static final long serialVersionUID = 1L;
	private boolean ramUsage;
	private boolean cpuUsage;

	public boolean isCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(boolean cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public boolean isRamUsage() {
		return ramUsage;
	}

	public void setRamUsage(boolean ramUsage) {
		this.ramUsage = ramUsage;
	}

}
