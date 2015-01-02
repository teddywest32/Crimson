package subterranean.crimson.server.graphics.panels.cp;

import javax.swing.JPanel;

public abstract class CPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public abstract void changedConnectionState(boolean connected);

	public abstract void refresh();

	public abstract void deinitialize();
	
	public abstract String getPanelName();
}
