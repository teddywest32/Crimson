package subterranean.crimson.server.graphics;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import subterranean.crimson.server.graphics.panels.cp.CPanel;
import subterranean.crimson.universal.translation.T;

public class TabComponent extends JPanel {

	private static final long serialVersionUID = 1L;

	public CPanel cpanel;

	public TabComponent(final ControlPanel dcp, String label, CPanel cp) {
		cpanel = cp;
		setPreferredSize(new Dimension(130, 16));
		final TabComponent THIS = this;
		setOpaque(false);
		setLayout(null);
		JLabel tabLabel = new JLabel(label);
		tabLabel.setBounds(20, 0, 90, 16);
		tabLabel.setPreferredSize(new Dimension(200, 15));
		tabLabel.setMinimumSize(new Dimension(200, 15));
		tabLabel.setHorizontalAlignment(JLabel.CENTER);
		add(tabLabel);

		final JPopupMenu tabMenu = new JPopupMenu();
		JMenuItem detach = new JMenuItem(T.t("menu-detach"));
		detach.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dcp.detachTab(THIS);

			}
		});
		tabMenu.add(detach);

		JMenuItem close = new JMenuItem(T.t("menu-close"));
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dcp.closeTab(THIS);

			}
		});
		tabMenu.add(close);

		final JButton tabOptions = new JButton(">");
		tabOptions.setFocusable(false);
		tabOptions.setFont(new Font("Dialog", Font.BOLD, 14));
		tabOptions.setMargin(new Insets(2, 0, 2, 0));
		tabOptions.setBounds(111, 0, 18, 16);
		tabOptions.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				tabMenu.show(tabOptions, 0, 0);

			}
		});

		JLabel label_1 = new JLabel(new ImageIcon(DesktopControlPanel.class.getResource("/subterranean/crimson/server/graphics/icons/cp/" + label.substring(0, 1).toUpperCase() + ".png")));
		label_1.setBounds(0, 0, 19, 16);
		add(label_1);
		add(tabOptions);

	}

}
