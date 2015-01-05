package subterranean.crimson.server.graphics.panels.settings;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSlider;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.graphics.dmodules.SystemInformation;

public class SPDModules extends JPanel {

	private static final long serialVersionUID = 1L;
	private JCheckBox chckbxEnable;
	private JSpinner spinner;
	private JSlider slider;

	public SPDModules() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel detail_panel = new JPanel();
		detail_panel.setBorder(new TitledBorder(null, "Detail Pane", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(detail_panel);

		JCheckBox chckbxEnableDetailPane = new JCheckBox("Enable Detail Pane");
		detail_panel.add(chckbxEnableDetailPane);

		JPanel dmodule_panel = new JPanel();
		dmodule_panel.setBorder(new TitledBorder(null, "Detail Modules", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(dmodule_panel);
		dmodule_panel.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		dmodule_panel.add(tabbedPane, BorderLayout.CENTER);

		JPanel info_panel = new JPanel();
		tabbedPane.addTab("Info", null, info_panel, null);
		info_panel.setLayout(new BoxLayout(info_panel, BoxLayout.Y_AXIS));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		info_panel.add(panel_2);
		panel_2.setLayout(null);

		JTextArea txtrTheInfoDmodule = new JTextArea();
		txtrTheInfoDmodule.setBounds(5, 17, 498, 254);
		txtrTheInfoDmodule.setText("The info DModule streams various information about the system.");
		txtrTheInfoDmodule.setEditable(false);
		txtrTheInfoDmodule.setOpaque(false);
		txtrTheInfoDmodule.setWrapStyleWord(true);
		txtrTheInfoDmodule.setLineWrap(true);
		panel_2.add(txtrTheInfoDmodule);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		info_panel.add(panel_3);
		panel_3.setLayout(null);

		chckbxEnable = new JCheckBox("Enable");
		chckbxEnable.setBounds(8, 20, 98, 23);
		panel_3.add(chckbxEnable);

		JLabel lblWeight = new JLabel("Weight:");
		lblWeight.setBounds(120, 24, 70, 15);
		panel_3.add(lblWeight);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, -10, 10, 1));
		spinner.setBounds(192, 22, 44, 20);
		panel_3.add(spinner);

		slider = new JSlider();
		slider.setBounds(148, 126, 200, 16);
		panel_3.add(slider);

		JLabel lblRefreshPeriod = new JLabel("Refresh Period:");
		lblRefreshPeriod.setBounds(200, 99, 116, 15);
		panel_3.add(lblRefreshPeriod);

		JPanel preview_panel = new JPanel();
		tabbedPane.addTab("Preview", null, preview_panel, null);
	}

	public void save() {
		if (chckbxEnable.isSelected()) {
			Server.getSettings().getDmoduleMemory().sysInfo = new SystemInformation();
			Server.getSettings().getDmoduleMemory().sysInfo.weight = (byte) spinner.getValue();
			Server.getSettings().getDmoduleMemory().sysInfo.period = slider.getValue();
		} else {
			Server.getSettings().getDmoduleMemory().sysInfo = null;
		}
	}
}
