package subterranean.crimson.server.graphics.panels.settings;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;

public class SPDModules extends JPanel{
	public SPDModules() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Detail Pane", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Detail Modules", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		panel_1.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel info_panel = new JPanel();
		tabbedPane.addTab("Info", null, info_panel, null);
		info_panel.setLayout(new BoxLayout(info_panel, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		info_panel.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		info_panel.add(panel_3);
	}
}
