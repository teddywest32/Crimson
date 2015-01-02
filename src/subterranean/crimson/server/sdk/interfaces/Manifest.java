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
