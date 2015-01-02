package subterranean.crimson.server.containers;

import java.io.Serializable;
import java.util.ArrayList;

import subterranean.crimson.server.graphics.DModule;
import subterranean.crimson.server.graphics.dmodules.NetworkUsage;

public class DModuleMemory implements Serializable{


	private static final long serialVersionUID = 1L;
	public ArrayList<DModule> dms = new ArrayList<DModule>();
	
	public DModuleMemory(){
		//add default modules
		NetworkUsage nu = new NetworkUsage();
		nu.weight = -5;
		dms.add(nu);
		
	}

}
