package subterranean.crimson.server.graphics.panels.cp.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.GraphicUtilities;
import subterranean.crimson.server.graphics.ShellPanel;
import subterranean.crimson.server.graphics.StatusLights;
import subterranean.crimson.server.graphics.panels.cp.CPanel;
import subterranean.crimson.universal.Reporter;
import subterranean.crimson.universal.exceptions.InvalidResponseException;

public class SPShell extends CPanel {

	private static final long serialVersionUID = 1L;
	private JTabbedPane shellpane;
	private JTable env_table;
	private ArrayList<ShellPanel> shells = new ArrayList<ShellPanel>();

	public SPShell(final ControlPanel cp) {
		setLayout(new BorderLayout(0, 0));
		shellpane = new JTabbedPane(JTabbedPane.TOP);
		add(shellpane);

		JPanel shell_info_panel = new JPanel();
		shellpane.addTab("Information", null, shell_info_panel, null);
		shell_info_panel.setLayout(new BoxLayout(shell_info_panel, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(100, 200));
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Shell Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		shell_info_panel.add(panel);
		panel.setLayout(null);

		JLabel lblType = new JLabel("Location:");
		lblType.setBounds(12, 22, 83, 16);
		panel.add(lblType);

		JLabel lblPrivileges = new JLabel("Privileges:");
		lblPrivileges.setBounds(12, 50, 83, 16);
		panel.add(lblPrivileges);

		StatusLights sl = new StatusLights();
		sl.setLocation(382, 22);
		sl.setSize(9, 25);
		sl.light(Color.GREEN, 3);
		sl.setVisible(true);

		panel.add(sl);

		JButton btnAddShell = new JButton("New Shell");
		btnAddShell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// add a new panel to the tabbed pane
				ShellPanel sp = null;
				try {
					sp = new ShellPanel(ClientCommands.shell_init(cp.c), cp.c);
				} catch (InvalidResponseException e1) {
					// failed to initialize shell
					cp.consoleAppend("Failed to initialize a shell");
					Reporter.report(e1);
					return;
				}
				sp.setVisible(true);

				shells.add(sp);
				// find a new shell number
				int i = 1;
				while (true) {
					if (!GraphicUtilities.containsTab(shellpane, "Shell " + i)) {
						shellpane.addTab("Shell " + i, null, sp, null);
						break;
					}
					i++;
				}

			}
		});
		btnAddShell.setBounds(395, 22, 115, 26);
		panel.add(btnAddShell);

		JLabel label = new JLabel("");
		label.setBounds(107, 23, 202, 15);
		panel.add(label);

		JLabel label_1 = new JLabel("");
		label_1.setBounds(107, 51, 202, 15);
		panel.add(label_1);

		JPanel env_panel = new JPanel();
		env_panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Environment Variables", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		shell_info_panel.add(env_panel);
		env_panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_9 = new JScrollPane();
		env_panel.add(scrollPane_9);

		env_table = new JTable();
		scrollPane_9.setViewportView(env_table);

		// env_table.setModel(new EnvTableModel(cp.c.getProfile().info.env));
	}

	@Override
	public void changedConnectionState(boolean connected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deinitialize() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getPanelName() {
		return "shell";
	}
}
