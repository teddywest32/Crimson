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
