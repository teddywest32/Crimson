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
