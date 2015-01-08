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
package subterranean.crimson.server.sdk.interfaces;

import javax.swing.JMenu;

import subterranean.crimson.universal.containers.PluginMessage;

public interface Manifest {

	public String getVersion();

	public String getLCVersion();

	public String getPluginName();

	public JMenu getPluginMenu();
	
	void execute(PluginMessage pm);

	/**
	 * Runs everytime Crimson starts up
	 */
	void startup();// run every time crimson is started

	/**
	 * Runs once on the plugin's first load
	 */
	void install();// run once on installation

}
