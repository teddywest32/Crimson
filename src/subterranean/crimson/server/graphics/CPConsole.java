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
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import subterranean.crimson.server.graphics.frames.MainScreen;

public class CPConsole extends JPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<String> data = new ArrayList<String>();
	private JTextPane pane = new JTextPane();
	private JScrollPane sp = new JScrollPane(pane);

	private boolean autoscroll = true;

	public CPConsole() {
		pane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
					final String selected = pane.getSelectedText();

					JPopupMenu popup = new JPopupMenu();
					final JMenuItem copy = new JMenuItem();
					copy.setText("Copy");
					copy.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/C.png")));
					copy.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							if (!copy.isEnabled()) {
								return;
							}
							Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(selected), null);

						}

					});
					if (selected == null || selected.isEmpty()) {
						copy.setEnabled(false);
					}

					popup.add(copy);

					popup.show(pane, e.getX(), e.getY());
				}
			}
		});
		pane.setEditable(false);
		pane.setVisible(true);
		pane.setBackground(Color.BLACK);
		MutableAttributeSet set = new SimpleAttributeSet(pane.getParagraphAttributes());
		StyleConstants.setLineSpacing(set, -0.2f);
		pane.setParagraphAttributes(set, true);
		pane.setContentType("text/html");

		setLayout(new BorderLayout());

		add(sp, BorderLayout.CENTER);

	}

	public synchronized void addLine(String s) {
		synchronized (data) {
			data.add("<strong><font color=\"white\">[" + new Date().toString() + "] " + s + "</font></strong>");
			loadData();
		}

	}

	public void loadData() {

		StringBuffer text = new StringBuffer();
		for (String s : data) {
			text.append(s + "<br>");
		}
		pane.setText(text.toString());
		if (autoscroll) {

			JScrollBar vertical = sp.getVerticalScrollBar();
			vertical.setValue(vertical.getMaximum());

		}
	}

	public void clear() {
		data.clear();
		loadData();
	}

}
