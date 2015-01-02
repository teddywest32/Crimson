package subterranean.crimson.server.graphics.panels.generate.jar;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public InfoPanel() {
		setLayout(null);

		JTextArea txtrTheJarPayload = new JTextArea();
		txtrTheJarPayload.setText("The Jar Installer Generator can generate a runnable Java Archive (Jar) file that installs the Crimson client.  Jar files can be executed by double clicking on them or with: \"java -jar jarfile.jar\" from the command line. The client will then make an attempt to contact the Crimson server based on the set parameters.  The Jar installation vector is typically the fastest and easiest way to install Crimson.  Many options specified here can be adjusted even after Crimson has installed.");
		txtrTheJarPayload.setBackground(new Color(0, 0, 0, 0));
		txtrTheJarPayload.setOpaque(false);

		txtrTheJarPayload.setWrapStyleWord(true);
		txtrTheJarPayload.setLineWrap(true);
		txtrTheJarPayload.setEditable(false);
		txtrTheJarPayload.setBounds(12, 12, 567, 139);
		add(txtrTheJarPayload);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Legal", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(12, 200, 567, 100);
		add(panel_2);
		panel_2.setLayout(null);

		JTextArea txtrInstallingCrimsonOn = new JTextArea();
		txtrInstallingCrimsonOn.setLineWrap(true);
		txtrInstallingCrimsonOn.setWrapStyleWord(true);
		txtrInstallingCrimsonOn.setText("Installing Crimson on systems that you do not have permission to do so on is illegal. Subterranean Security will not be held liable in the event of misuse of Crimson Extended Administration Tool.");
		txtrInstallingCrimsonOn.setOpaque(false);
		txtrInstallingCrimsonOn.setBackground(new Color(0, 0, 0, 0));
		txtrInstallingCrimsonOn.setBounds(12, 23, 543, 64);
		panel_2.add(txtrInstallingCrimsonOn);
	}

}
