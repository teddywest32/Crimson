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

public class DesktopTabMemory implements Serializable {

	private static final long serialVersionUID = 1L;
	public boolean SPMessages = true;
	public boolean SPClient = true;
	public boolean SPKeylogger = true;
	public boolean SPLocation = true;
	public boolean SPShell = true;
	public boolean SPExplorer = true;
	public boolean SPLog = true;
	public boolean SPDesktop = true;
	public boolean SPClipboard = true;
	public boolean SPInformation = true;

}
