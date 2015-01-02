package subterranean.crimson.server.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ProcessPanel extends JPanel {

	public StatusLights sl;

	public ProcessPanel() {
		setBorder(null);
		setPreferredSize(new Dimension(200, 20));
		setMinimumSize(new Dimension(200, 20));
		setLayout(new BorderLayout(0, 0));
		sl = new StatusLights();
		sl.setPreferredSize(new Dimension(7, 20));
		add(sl, BorderLayout.WEST);

		p = new JLabel("Process...");
		p.setBorder(new LineBorder(new Color(200, 0, 0)));
		add(p, BorderLayout.CENTER);
	}

	private static final long serialVersionUID = 1L;
	public JLabel p;

}
