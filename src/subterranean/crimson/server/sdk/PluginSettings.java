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
package subterranean.crimson.server.sdk;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author globalburning Provides a simple way for plugins to store settings
 *
 */
public class PluginSettings implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * The unique package name for this plugin
	 */
	public String pac;

	/**
	 * A place where plugins can store data
	 */
	public HashMap<String, Object> settings;

	public PluginSettings() {
		settings = new HashMap<String, Object>();
	}
}
