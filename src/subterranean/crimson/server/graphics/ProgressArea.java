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
package subterranean.crimson.server.graphics;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import sl.SLAnimator;
import sl.SLConfig;
import sl.SLKeyframe;
import sl.SLPanel;
import sl.SLSide;
import subterranean.crimson.server.Server;

public class ProgressArea extends JPanel {

	private static final long serialVersionUID = 1L;
	MovingPanel main;
	MovingPanel note;
	SLPanel slPanel;
	private final SLConfig mainCfg, p1Cfg;

	public ProgressArea() {
		JPanel blank = new JPanel();
		blank.setLayout(new BorderLayout(0, 0));

		main = new MovingPanel(blank);

		pp = new ProcessPanel();

		note = new MovingPanel(pp);
		setLayout(new BorderLayout(0, 0));

		slPanel = new SLPanel();
		add(slPanel);

		// animations
		main.setAction(p1Action);

		//
		p1Cfg = new SLConfig(slPanel).gap(0, 0).row(1f).col(0f).place(0, 0, main).col(10f).place(0, 1, note);

		mainCfg = new SLConfig(slPanel).gap(0, 0).row(1f).col(1f).place(0, 0, main);

		slPanel.setTweenManager(SLAnimator.createTweenManager());
		slPanel.initialize(mainCfg);
	}

	private final Runnable p1Action = new Runnable() {
		@Override
		public void run() {
			slPanel.createTransition().push(new SLKeyframe(p1Cfg, 0.7f).setStartSide(SLSide.RIGHT, note).setCallback(new SLKeyframe.Callback() {
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

	private boolean showingDetail = false;
	public ProcessPanel pp;

	public synchronized void showDetail(String s) {
		if (!Server.getSettings().isShowDetails()) {
			return;
		}

		pp.p.setText(s);
		pp.sl.animate("indeterminate", Color.CYAN, null);
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

		showingDetail = true;
		main.runAction();

	}

	public synchronized void dropDetail() {
		pp.sl.stopAnimation();
		pp.p.setText(" Finished!");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!showingDetail) {
			return;
		}
		showingDetail = false;
		main.runAction();

	}

}
