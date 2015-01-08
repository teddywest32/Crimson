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

public class NotificationPolicy implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Indicates if notifications should be shown
	 */
	private boolean showNotes;

	/**
	 * Indicates if a notification should be shown when a foreign connection is detected
	 */
	private boolean foreign_connection;

	/**
	 * Indicates if a notification should be shown when a connection is lost
	 */
	private boolean connection_lost;

	/**
	 * Indicates if a notification should be shown when a new connection is made
	 */
	private boolean connection_new;

	/**
	 * Indicates if a notification should be shown when a unique connection is made
	 */
	private boolean connection_unique;

	/**
	 * Indicates if a notification should be shown when an update is available
	 */
	private boolean inform_on_update;

	public NotificationPolicy() {
		setShowNotes(true);
		setForeign_connection(false);
		setConnection_lost(false);
		setConnection_unique(true);
		setConnection_unique(true);
		setInform_on_update(false);

	}

	public boolean isShowingNotes() {
		return showNotes;
	}

	public void setShowNotes(boolean showNotes) {
		this.showNotes = showNotes;
	}

	public boolean isForeign_connection() {
		return foreign_connection;
	}

	public void setForeign_connection(boolean foreign_connection) {
		this.foreign_connection = foreign_connection;
	}

	public boolean isConnection_lost() {
		return connection_lost;
	}

	public void setConnection_lost(boolean connection_lost) {
		this.connection_lost = connection_lost;
	}

	public boolean isConnection_new() {
		return connection_new;
	}

	public void setConnection_new(boolean connection_new) {
		this.connection_new = connection_new;
	}

	public boolean isConnection_unique() {
		return connection_unique;
	}

	public void setConnection_unique(boolean connection_unique) {
		this.connection_unique = connection_unique;
	}

	public boolean isInform_on_update() {
		return inform_on_update;
	}

	public void setInform_on_update(boolean inform_on_update) {
		this.inform_on_update = inform_on_update;
	}

}
