package subterranean.crimson.server.graphics;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import subterranean.crimson.server.graphics.frames.About;

public class SeparateWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel mainPanel;

	public SeparateWindow(JPanel panel) {
		mainPanel = panel;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/subterranean/crimson/server/graphics/icons/C-40.png")));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(panel);
		setContentPane(contentPane);
	}

	@Override
	public void dispose() {
		super.dispose();

	}

}
