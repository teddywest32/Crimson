package subterranean.crimson.server.graphics;

/*
 * 	Crimson Extended Administration Tool
 *  Copyright (C) 2015 Subterranean Security
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import subterranean.crimson.server.graphics.panels.generate.apk.Main;
import subterranean.crimson.universal.Platform;
import subterranean.slidinglayout.SLAnimator;
import subterranean.slidinglayout.SLConfig;
import subterranean.slidinglayout.SLKeyframe;
import subterranean.slidinglayout.SLPanel;
import subterranean.slidinglayout.SLSide;

public class GenerateAPK extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public MovingPanel note;
	public MovingPanel main;
	private final SLConfig mainCfg, p1Cfg;

	public GenerateAPK() {

		if (!Platform.osx) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(GenerateAPK.class.getResource("/subterranean/crimson/server/graphics/icons/menu/apk.png")));
		}
		setResizable(false);
		setTitle("Generate Android APK");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 607, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		slPanel = new SLPanel();
		contentPane.add(slPanel, BorderLayout.CENTER);

		Notification notification = new Notification("Welcome to the APK Generator!");

		note = new MovingPanel(notification);
		main = new MovingPanel(new Main(this));

		main.setAction(p1Action);

		mainCfg = new SLConfig(slPanel).gap(0, 0).row(6f).row(1f).col(1f).place(0, 0, main).place(1, 0, note);

		p1Cfg = new SLConfig(slPanel).gap(0, 0).row(2f).col(1f).place(0, 0, main);

		slPanel.setTweenManager(SLAnimator.createTweenManager());
		slPanel.initialize(mainCfg);

	}

	private final Runnable p1Action = new Runnable() {
		@Override
		public void run() {

			slPanel.createTransition().push(new SLKeyframe(p1Cfg, 0.8f).setEndSide(SLSide.BOTTOM, note).setCallback(new SLKeyframe.Callback() {
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

			slPanel.createTransition().push(new SLKeyframe(mainCfg, 0.8f).setStartSide(SLSide.BOTTOM, note).setCallback(new SLKeyframe.Callback() {
				@Override
				public void done() {
					main.setAction(p1Action);

				}
			})).play();
		}
	};
	private SLPanel slPanel;

	public void addNotification(final String s) {
		Runnable r = new Runnable() {
			public void run() {
				displayNotification(s);
			}
		};
		new Thread(r).start();
	}

	private synchronized void displayNotification(String s) {

		Notification n = new Notification(s);

		note.removeAll();
		note.add(n);
		// move the note panel up
		main.runAction();

		try {
			Thread.sleep(7000);

		} catch (InterruptedException e) {

		} finally {
			// move the note back down
			main.runAction();
			try {
				Thread.sleep(900);
			} catch (InterruptedException e) {

			}
		}

	}

}
