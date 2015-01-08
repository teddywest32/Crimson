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
package subterranean.crimson.server.graphics.frames;



import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import subterranean.crimson.server.graphics.AddAppletListener;
import subterranean.crimson.server.graphics.models.table.ListenerTableModel;

public class ListenerManager extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel table_panel;
	private JTable table;
	private JScrollPane scrollPane;
	public static ListenerTableModel tmodel;
	private JButton btnRemove;
	protected int removal;

	public ListenerManager() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ListenerManager.class.getResource("/subterranean/crimson/server/graphics/icons/icon.png")));
		setTitle("Listener Manager");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 718, 294);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(12, 12, 684, 252);
		contentPane.add(panel);
		panel.setLayout(null);

		table_panel = new JPanel();
		table_panel.setBounds(12, 12, 660, 192);
		panel.add(table_panel);
		table_panel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		table_panel.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// get source of click
				JTable source = (JTable) e.getSource();

				final int sourceRow = source.rowAtPoint(e.getPoint());
				if (sourceRow == -1) {

					return;
				}
				// select row
				if (!source.isRowSelected(sourceRow)) {
					source.changeSelection(sourceRow, 0, false, false);
				}

				// enable remove button
				btnRemove.setEnabled(true);
				removal = sourceRow;

			}
		});
		tmodel = new ListenerTableModel();
		table.setModel(tmodel);
		scrollPane.setViewportView(table);

		JButton btnAdd = new JButton("Add Client");
		btnAdd.setFont(new Font("Dialog", Font.BOLD, 11));
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				AddClientListener acl = new AddClientListener();
				acl.setVisible(true);

			}
		});
		btnAdd.setBounds(12, 216, 98, 26);
		panel.add(btnAdd);

		btnRemove = new JButton("Remove");
		btnRemove.setEnabled(false);
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!btnRemove.isEnabled()) {
					return;
				}
				tmodel.remove(removal);
				btnRemove.setEnabled(false);

			}
		});
		btnRemove.setBounds(492, 216, 98, 26);
		panel.add(btnRemove);

		final JButton btnAddApplet = new JButton("Add Applet");
		btnAddApplet.setEnabled(false);
		btnAddApplet.setFont(new Font("Dialog", Font.BOLD, 11));
		btnAddApplet.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!btnAddApplet.isEnabled()) {
					return;
				}
				AddAppletListener aal = new AddAppletListener();
				aal.setVisible(true);

			}
		});
		btnAddApplet.setBounds(122, 216, 98, 26);
		panel.add(btnAddApplet);

		JButton btnOk = new JButton("OK");
		btnOk.setBounds(602, 216, 70, 26);
		panel.add(btnOk);
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
	}
}
