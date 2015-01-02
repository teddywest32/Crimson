package subterranean.crimson.server.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class Splash extends JWindow {
	public Splash() {

		setBounds(0, 0, 600, 256);

		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(GenerateAPK.class.getResource("/subterranean/crimson/server/graphics/images/splash.png"))));
		getContentPane().add(label, BorderLayout.CENTER);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		progressBar.setString("Loading");
		UIManager.put("ProgressBar.background", Color.BLACK);
		UIManager.put("ProgressBar.foreground", Color.BLACK);
		progressBar.setForeground(new Color(200, 0, 0));
		getContentPane().add(progressBar, BorderLayout.SOUTH);
	}

	private static final long serialVersionUID = 1L;
}
