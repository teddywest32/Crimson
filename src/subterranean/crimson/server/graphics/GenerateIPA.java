package subterranean.crimson.server.graphics;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sl.SLAnimator;
import sl.SLConfig;
import sl.SLKeyframe;
import sl.SLPanel;
import sl.SLSide;
import subterranean.crimson.server.graphics.panels.generate.ipa.Main;
import subterranean.crimson.universal.Platform;

public class GenerateIPA extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public MovingPanel note;
	public MovingPanel main;
	private final SLConfig mainCfg, p1Cfg;

	public GenerateIPA() {

		if (!Platform.osx) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(GenerateIPA.class.getResource("/subterranean/crimson/server/graphics/icons/osx/apple.png")));
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
