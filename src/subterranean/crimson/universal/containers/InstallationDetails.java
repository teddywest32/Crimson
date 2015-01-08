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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/** This container is passed from the bootstrapper to the permajar on execution **/
public class InstallationDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	public HashMap<String, Object> details = new HashMap<String, Object>();
	public ArrayList<SystemMessage> sms = new ArrayList<SystemMessage>();

	public Date installDate;

	public int waitedIDLEfor = 0;

}
