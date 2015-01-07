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
