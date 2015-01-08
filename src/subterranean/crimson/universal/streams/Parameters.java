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
package subterranean.crimson.universal.streams;

import java.io.Serializable;

import subterranean.crimson.universal.Utilities;

public abstract class Parameters implements Serializable {

	private static final long serialVersionUID = 1L;
	private short streamID = Utilities.randStreamId();
	private String streamName;
	private long period;
	private boolean sender;
	private long progress;
	private String status;

	public short getStreamID() {
		return streamID;
	}

	public String getStreamName() {
		return streamName;
	}

	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	public boolean isSender() {
		return sender;
	}

	public void setSender(boolean sender) {
		this.sender = sender;
	}

	public long getProgress() {
		return progress;
	}

	public void setProgress(long progress) {
		this.progress = progress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
