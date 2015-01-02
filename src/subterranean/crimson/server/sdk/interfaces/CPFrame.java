package subterranean.crimson.server.sdk.interfaces;

import javax.swing.ImageIcon;

public interface CPFrame {

	void init();// creates the entire frame

	/**
	 * Returns an ImageIcon to display as this Frame's Icon. The ImageIcon description
	 * will be the name of the tab.
	 * 
	 * @return
	 */
	ImageIcon getTabInfo();

	void setClientID(int cid);

	int getClientID();

}
