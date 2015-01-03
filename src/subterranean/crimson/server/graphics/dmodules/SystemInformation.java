package subterranean.crimson.server.graphics.dmodules;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import subterranean.crimson.server.containers.ConnectionProfile;
import subterranean.crimson.server.graphics.DModule;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.StreamStore;
import subterranean.crimson.universal.streams.infostream.ISParameters;
import subterranean.crimson.universal.streams.infostream.InfoStream;

public class SystemInformation extends DModule {

	private static final long serialVersionUID = 1L;
	ConnectionProfile cp;
	private JLabel lblCpuVAL;
	private JLabel lblClientIDVAL;
	private JLabel label_1;

	private int streamID;
	private JLabel label;

	public SystemInformation() {
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Basic Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(0, 0, 211, 126);
		setLayout(new BorderLayout(0, 0));

		JPanel container = new JPanel();
		add(container);
		container.setLayout(new GridLayout(0, 2, 0, 0));

		// clientID
		JLabel lblClientID = new JLabel("ClientID:");
		container.add(lblClientID);

		lblClientIDVAL = new JLabel("");
		container.add(lblClientIDVAL);

		// platform
		JLabel lblCpu = new JLabel("CPU Usage:");
		container.add(lblCpu);

		lblCpuVAL = new JLabel("");
		container.add(lblCpuVAL);

		JLabel lblRamUsage = new JLabel("RAM Usage:");
		container.add(lblRamUsage);

		label = new JLabel("");
		container.add(label);

		JLabel lblFreeSpace = new JLabel("Free Space:");
		container.add(lblFreeSpace);

		label_1 = new JLabel("");
		container.add(label_1);

		if (cp != null) {
			updateGraphics();
		}

	}

	public void updateGraphics() {
		// lblCpuVAL.setText(cp.info.cpuUsage);
		// lblClientIDVAL.setText("" + cp.info.clientID);
		// label_1.setText(cp.info.freeSpace);
		// label.setText(cp.info.memUsage);

	}

	// @Override
	public void changeTarget(Connection c) {
		if (c != null) {
			this.cp = c.getProfile();
			StreamStore.removeStream(streamID);
			ISParameters param = new ISParameters();
			
			InfoStream ps = new InfoStream(param, c);
			streamID = ps.getStreamID();
			updateGraphics();
		} else {
			StreamStore.removeStream(streamID);
		}
	}
	
	@Override
	public int compareTo(Object o) {
		return (weight - ((DModule)o).weight);
	}
}
