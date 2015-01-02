package subterranean.crimson.universal;

/*
 * 	Crimson Extended Administration Tool
 *  Copyright (C) 2015 Subterranean Security
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

import java.util.HashMap;
import java.util.Map;

public class SigarCollection {
	// private static Sigar sigar = new Sigar();

	public static Map getCPU() {
		return null;
		// processor
		// Cpu cpu = new Cpu();

		// try {
		// cpu.gather(sigar);
		// } catch (SigarException e) {
		// / // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// return cpu.toMap();

	}

	public static Map getDirStats(String file) {
		return null;
		// DirStat ds = new DirStat();
		// try {
		// ds.gather(sigar, file);
		// } catch (SigarException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// return ds.toMap();

	}

	public static Map getFileAttr(String file) {
		return null;
		// FileInfo f = new FileInfo();
		// try {
		// f.gather(sigar, file);
		// } catch (SigarException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// return f.toMap();
	}

	public static Map<String, String> getMemory() {
		return null;

		// memory
		// Mem mem = new Mem();
		// try {
		// mem.gather(sigar);
		// } catch (SigarException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// return mem.toMap();
	}

	public static Map getNet() {
		return null;

		// NetInfo netinfo = new NetInfo();
		// try {
		// netinfo.gather(sigar);
		// } catch (SigarException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// return netinfo.toMap();

	}

	public static Map getSystem() {
		return null;

		// SysInfo sysinfo = new SysInfo();
		// try {
		// sysinfo.gather(sigar);
		// } catch (SigarException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// return sysinfo.toMap();
	}

	public static HashMap<String, Map> run() {
		HashMap<String, Map> i = new HashMap<String, Map>();
		i.put("memory", getMemory());
		i.put("cpu", getCPU());
		i.put("netInfo", getNet());
		i.put("system", getSystem());

		return i;
	}

}
