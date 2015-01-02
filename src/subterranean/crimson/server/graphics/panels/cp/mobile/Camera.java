package subterranean.crimson.server.graphics.panels.cp.mobile;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class Camera extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Camera() {
		setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);

		JMenu mnMode = new JMenu("Mode");
		menuBar.add(mnMode);

		JRadioButton rdbtnStillImage = new JRadioButton("Still Image");
		mnMode.add(rdbtnStillImage);

		JRadioButton rdbtnVideo = new JRadioButton("Video");
		mnMode.add(rdbtnVideo);

		JMenu mnCameraSelection = new JMenu("Camera Selection");
		menuBar.add(mnCameraSelection);

		JRadioButton rdbtnFront = new JRadioButton("Front Facing");
		mnCameraSelection.add(rdbtnFront);

		JRadioButton rdbtnRear = new JRadioButton("Rear Facing");
		mnCameraSelection.add(rdbtnRear);

		JMenu mnQuality = new JMenu("Quality");
		menuBar.add(mnQuality);

		JRadioButton rdbtnHigh = new JRadioButton("High");
		mnQuality.add(rdbtnHigh);

		JRadioButton rdbtnMedium = new JRadioButton("Medium");
		mnQuality.add(rdbtnMedium);

		JRadioButton rdbtnLow = new JRadioButton("Low");
		mnQuality.add(rdbtnLow);

		JMenu mnExport = new JMenu("Output");
		menuBar.add(mnExport);

		JRadioButton rdbtnStreamOnly = new JRadioButton("Stream Only");
		mnExport.add(rdbtnStreamOnly);

		JRadioButton rdbtnToFile = new JRadioButton("To FIle");
		mnExport.add(rdbtnToFile);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Viewer", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);

		JLabel label = new JLabel("");
		scrollPane.setViewportView(label);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Map", null, panel_2, null);

	}

}
