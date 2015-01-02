package subterranean.crimson.server.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScreenViewer extends JFrame {

	private static final long serialVersionUID = 1L;

	public ScreenViewer(ArrayList<ImageIcon> screens, int i) {
		setTitle("Screenshot Viewer");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PluginManager.class.getResource("/subterranean/crimson/server/graphics/icons/icon.png")));
		setMinimumSize(new Dimension(200, 200));
		setSize(new Dimension(screens.get(i).getIconWidth() + 30, screens.get(i).getIconHeight() + 30));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel(screens.get(i));
		panel.add(label);
	}

}
