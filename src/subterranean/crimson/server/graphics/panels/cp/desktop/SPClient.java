package subterranean.crimson.server.graphics.panels.cp.desktop;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.StatusLights;
import subterranean.crimson.server.graphics.UninstallConfirmation;
import subterranean.crimson.server.graphics.Update;
import subterranean.crimson.server.graphics.panels.cp.CPanel;
import subterranean.crimson.universal.Version;
import subterranean.crimson.universal.translation.T;

public class SPClient extends CPanel {

	private static final long serialVersionUID = 1L;
	private StatusLights restart_lights;
	private StatusLights shutdown_lights;
	private StatusLights hibernate_lights;

	private StatusLights updateLights;
	private StatusLights standby_lights;
	private JButton btnRestartHost;
	private JButton btnShutdownHost;
	private JButton btnHibernateHost;
	private JButton btnLogoff;
	private JButton btnStandby;

	public SPClient(final ControlPanel cp) {

		boolean r = cp.c.getProfile().info.getPower()[0];
		boolean sh = cp.c.getProfile().info.getPower()[2];
		boolean h = cp.c.getProfile().info.getPower()[1];
		boolean l = cp.c.getProfile().info.getPower()[3];
		boolean st = cp.c.getProfile().info.getPower()[4];

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel host_power_control_panel = new JPanel();
		host_power_control_panel.setLayout(null);
		host_power_control_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), T.t("title-host_controls"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(host_power_control_panel);

		btnShutdownHost = new JButton(T.t("misc-shutdown"));
		btnShutdownHost.setFont(new Font("Dialog", Font.BOLD, 11));
		btnShutdownHost.setBounds(25, 63, 100, 25);
		btnShutdownHost.setToolTipText(T.t("tooltip-shutdown"));
		btnShutdownHost.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!btnShutdownHost.isEnabled()) {
					return;
				}
				shutdown_lights.replaceLight(Color.yellow, 2);
				cp.consoleAppend(T.t("console-shutdown"));

				boolean result = ClientCommands.host_power_shutdown(cp.c);
				if (result) {
					// success
					// logoff_lights.replaceLight(Color.green, 3);
				} else {
					// failure
					// logoff_lights.replaceLight(Color.red, 1);

				}
			}
		});
		host_power_control_panel.add(btnShutdownHost);

		btnRestartHost = new JButton(T.t("misc-restart"));
		btnRestartHost.setFont(new Font("Dialog", Font.BOLD, 11));
		btnRestartHost.setBounds(25, 26, 100, 25);
		btnRestartHost.setToolTipText(T.t("tooltip-restart"));
		btnRestartHost.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!btnRestartHost.isEnabled()) {
					return;
				}
				restart_lights.replaceLight(Color.yellow, 2);
				cp.consoleAppend(T.t("console-restart"));

				boolean result = ClientCommands.host_power_restart(cp.c);
				if (result) {
					// success
					// logoff_lights.replaceLight(Color.green, 3);
				} else {
					// failure
					// logoff_lights.replaceLight(Color.red, 1);

				}
			}
		});
		host_power_control_panel.add(btnRestartHost);

		btnHibernateHost = new JButton(T.t("misc-hibernate"));
		btnHibernateHost.setFont(new Font("Dialog", Font.BOLD, 11));
		btnHibernateHost.setBounds(150, 26, 100, 25);
		btnRestartHost.setToolTipText(T.t("tooltip-hibernate"));
		btnHibernateHost.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnHibernateHost.isEnabled()) {
					return;
				}
				hibernate_lights.replaceLight(Color.yellow, 2);
				cp.consoleAppend(T.t("console-hibernate"));

				boolean result = ClientCommands.host_power_hibernate(cp.c);
				if (result) {
					// success
					// logoff_lights.replaceLight(Color.green, 3);
				} else {
					// failure
					// logoff_lights.replaceLight(Color.red, 1);

				}
			}
		});

		host_power_control_panel.add(btnHibernateHost);
		btnRestartHost.setToolTipText(T.t("tooltip-logoff"));

		btnStandby = new JButton(T.t("misc-standby"));
		btnStandby.setFont(new Font("Dialog", Font.BOLD, 11));
		btnStandby.setBounds(150, 62, 100, 26);
		btnRestartHost.setToolTipText(T.t("tooltip-standby"));
		btnStandby.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!btnStandby.isEnabled()) {
					return;
				}
				standby_lights.replaceLight(Color.yellow, 2);
				cp.consoleAppend(T.t("console-standby"));

				boolean result = ClientCommands.host_power_standby(cp.c);
				if (result) {
					// success
					// logoff_lights.replaceLight(Color.green, 3);
				} else {
					// failure
					// logoff_lights.replaceLight(Color.red, 1);

				}
			}
		});

		host_power_control_panel.add(btnStandby);

		// status lights
		restart_lights = new StatusLights();
		restart_lights.setBounds(12, 26, 9, 25);
		host_power_control_panel.add(restart_lights);

		shutdown_lights = new StatusLights();
		shutdown_lights.setBounds(12, 63, 9, 25);
		host_power_control_panel.add(shutdown_lights);

		hibernate_lights = new StatusLights();
		hibernate_lights.setBounds(137, 26, 9, 25);
		host_power_control_panel.add(hibernate_lights);

		standby_lights = new StatusLights();
		standby_lights.setBounds(137, 62, 9, 25);
		host_power_control_panel.add(standby_lights);

		restart_lights.light(r ? Color.GREEN : Color.RED, r ? 3 : 1);
		shutdown_lights.light(sh ? Color.GREEN : Color.RED, sh ? 3 : 1);
		hibernate_lights.light(h ? Color.GREEN : Color.RED, h ? 3 : 1);
		standby_lights.light(st ? Color.GREEN : Color.RED, st ? 3 : 1);

		JPanel client_actions_panel = new JPanel();
		client_actions_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), T.t("title-client_controls"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(client_actions_panel);
		client_actions_panel.setLayout(null);

		JButton btnUninstallClient = new JButton("Uninstall");
		btnUninstallClient.setFont(new Font("Dialog", Font.BOLD, 11));
		btnUninstallClient.setBounds(12, 63, 100, 25);
		client_actions_panel.add(btnUninstallClient);
		btnUninstallClient.setToolTipText(T.t("tooltip-uninstall"));
		btnUninstallClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UninstallConfirmation uc = new UninstallConfirmation(cp.c);
				uc.setVisible(true);
				cp.dispose();
			}
		});
		btnUninstallClient.setVerticalAlignment(SwingConstants.BOTTOM);
		btnUninstallClient.setVerticalTextPosition(SwingConstants.BOTTOM);

		JButton btnRestartClient = new JButton(T.t("misc-restart"));
		btnRestartClient.setFont(new Font("Dialog", Font.BOLD, 11));
		btnRestartClient.setBounds(12, 26, 100, 25);
		client_actions_panel.add(btnRestartClient);
		btnRestartClient.setToolTipText("Restarts Crimson on host machine");

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setEnabled(false);
		btnUpdate.setBounds(137, 26, 98, 26);
		client_actions_panel.add(btnUpdate);
		btnUpdate.setFont(new Font("Dialog", Font.BOLD, 11));

		updateLights = new StatusLights();
		updateLights.setBounds(124, 26, 9, 25);
		client_actions_panel.add(updateLights);

		JSpinner spinner = new JSpinner();
		spinner.setEnabled(false);
		spinner.setBounds(621, 28, 28, 20);
		client_actions_panel.add(spinner);
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// show the dialog and exit the cp
				Update u = new Update(cp.c);
				u.setVisible(true);
				u.setLocationRelativeTo(null);
				cp.dispose();

			}
		});

		JPanel update_panel = new JPanel();
		update_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), T.t("title-users"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(update_panel);
		update_panel.setLayout(null);

		btnLogoff = new JButton(T.t("misc-logoff"));
		btnLogoff.setEnabled(false);
		btnLogoff.setBounds(12, 20, 100, 26);
		update_panel.add(btnLogoff);
		btnLogoff.setFont(new Font("Dialog", Font.BOLD, 11));

		JButton btnLogon = new JButton("Logon");
		btnLogon.setEnabled(false);
		btnLogon.setBounds(12, 58, 100, 25);
		update_panel.add(btnLogon);

		JButton btnElevate = new JButton("Elevate");
		btnElevate.setEnabled(false);
		btnElevate.setBounds(532, 20, 117, 25);
		update_panel.add(btnElevate);

		JButton btnDelevate = new JButton("Delevate");
		btnDelevate.setEnabled(false);
		btnDelevate.setBounds(532, 58, 117, 25);
		update_panel.add(btnDelevate);

		JButton btnCreate = new JButton("Create");
		btnCreate.setEnabled(false);
		btnCreate.setBounds(412, 20, 117, 25);
		update_panel.add(btnCreate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.setBounds(412, 58, 117, 25);
		update_panel.add(btnDelete);
		btnLogoff.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!btnLogoff.isEnabled()) {
					return;
				}
				cp.consoleAppend(T.t("console-logoff"));

				boolean result = ClientCommands.host_power_logoff(cp.c);
				if (result) {
					// success
					// logoff_lights.replaceLight(Color.green, 3);
				} else {
					// failure
					// logoff_lights.replaceLight(Color.red, 1);

				}
			}
		});

		btnRestartClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ClientCommands.client_restart(cp.c);
			}
		});

		// // set lights

		if (!r) {
			btnRestartHost.setEnabled(false);

		}
		if (!sh) {
			btnShutdownHost.setEnabled(false);
		}
		if (!h) {
			btnHibernateHost.setEnabled(false);
		}
		if (!l) {
			btnLogoff.setEnabled(false);
		}
		if (!st) {
			btnStandby.setEnabled(false);
		}

		String text = "";
		// determine status text
		if (Version.version.equals(cp.c.getProfile().info.getVersion())) {
			// these versions are the same
			text = "The client version is the same as the server!";

		} else {
			// these versions are different
			text = "The client version is different than the server!";

		}
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
		return "client";
	}
}
