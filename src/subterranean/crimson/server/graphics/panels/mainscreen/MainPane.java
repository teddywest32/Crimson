/*******************************************************************************
 *              Crimson Extended Administration Tool (CrimsonXAT)              *
 *                   Copyright (C) 2015 Subterranean Security                  *
 *                                                                             *
 *     This program is free software: you can redistribute it and/or modify    *
 *     it under the terms of the GNU General Public License as published by    *
 *      the Free Software Foundation, either version 3 of the License, or      *
 *                      (at your option) any later version.                    *
 *                                                                             *
 *       This program is distributed in the hope that it will be useful,       *
 *        but WITHOUT ANY WARRANTY; without even the implied warranty of       *
 *        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        *
 *                 GNU General Public License for more details.                *
 *                                                                             *
 *      You should have received a copy of the GNU General Public License      *
 *      along with this program.  If not, see http://www.gnu.org/licenses      *
 *******************************************************************************/
package subterranean.crimson.server.graphics.panels.mainscreen;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import sl.SLAnimator;
import sl.SLConfig;
import sl.SLKeyframe;
import sl.SLPanel;
import sl.SLSide;
import subterranean.crimson.server.Server;
import subterranean.crimson.server.containers.ConnectionProfile;
import subterranean.crimson.server.graphics.DModule;
import subterranean.crimson.server.graphics.DetailPane;
import subterranean.crimson.server.graphics.MovingPanel;
import subterranean.crimson.server.graphics.dmodules.SystemInformation;
import subterranean.crimson.server.graphics.dmodules.Thumbnail;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.Logger;

public class MainPane extends JPanel {

	private static final long serialVersionUID = 1L;
	MovingPanel main;
	MovingPanel note;
	public Main rootPanel;
	SLPanel slPanel;
	private final SLConfig mainCfg, p1Cfg;

	public static DetailPane dp;

	public MainPane() {
		rootPanel = new Main(this);
		main = new MovingPanel(rootPanel);
		dp = new DetailPane();
		// add modules
		dp.refreshModules();

		note = new MovingPanel(dp);
		setLayout(new BorderLayout(0, 0));

		slPanel = new SLPanel();
		add(slPanel);

		// animations
		main.setAction(p1Action);

		p1Cfg = new SLConfig(slPanel).gap(0, 0).row(6f).col(4f).place(0, 0, main).col(1f).place(0, 1, note);

		mainCfg = new SLConfig(slPanel).gap(0, 0).row(1f).col(1f).place(0, 0, main);

		slPanel.setTweenManager(SLAnimator.createTweenManager());
		slPanel.initialize(mainCfg);

	}

	private final Runnable p1Action = new Runnable() {
		@Override
		public void run() {

			slPanel.createTransition().push(new SLKeyframe(p1Cfg, 0.5f).setStartSide(SLSide.RIGHT, note).setCallback(new SLKeyframe.Callback() {
				@Override
				public void done() {
					main.setAction(p1BackAction);
					main.enableAction();
				}
			})).play();
		}
	};

	private final Runnable p1BackAction = new Runnable() {
		@Override
		public void run() {

			slPanel.createTransition().push(new SLKeyframe(mainCfg, 0.5f).setEndSide(SLSide.RIGHT, note).setCallback(new SLKeyframe.Callback() {
				@Override
				public void done() {
					main.setAction(p1Action);

				}
			})).play();
		}
	};

	private ConnectionProfile lastCP;
	private boolean showingDetail = false;

	public synchronized void showDetail(Connection c) {
		if (!Server.getSettings().isShowDetails()) {
			return;
		}
		ConnectionProfile cp = c.getProfile();
		if (lastCP != null && cp == lastCP) {
			// no change needed
			return;
		}
		dp.updateTarget(c);
		if (showingDetail) {
			// drop this one and put another up
			main.runAction();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		lastCP = cp;

		showingDetail = true;
		main.runAction();

	}

	public synchronized void dropDetail() {
		if (!showingDetail) {
			return;
		}
		showingDetail = false;
		dp.stopStreams();
		lastCP = null;
		main.runAction();

	}

}
