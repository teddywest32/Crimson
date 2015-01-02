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
