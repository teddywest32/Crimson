package subterranean.crimson.server.graphics.panels.cp.desktop;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.MessageView;
import subterranean.crimson.server.graphics.models.list.MessageList;
import subterranean.crimson.server.graphics.panels.cp.CPanel;
import subterranean.crimson.server.graphics.renderers.list.MessageListRenderer;
import subterranean.crimson.universal.containers.SystemMessage;

public class SPMessages extends CPanel {

	private static final long serialVersionUID = 1L;
	private JPanel cards;
	private JList<SystemMessage> list;
	private MessageView view_panel;

	public SPMessages(ControlPanel cp) {
		setLayout(new BorderLayout(0, 0));

		JPanel list_panel = new JPanel();
		list_panel.setLayout(new BorderLayout(0, 0));

		MessageListRenderer mlr = new MessageListRenderer();
		MessageList mlm = new MessageList();

		list = new JList<SystemMessage>();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// get message
				SystemMessage sm = list.getSelectedValue();
				if (sm == null) {
					return;
				}
				view_panel = new MessageView(sm);
				view_panel.repaint();

				// switch cards
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.next(cards);

			}
		});
		list.setOpaque(false);
		list.setCellRenderer(mlr);
		list.setModel(mlm);
		list_panel.add(list);

		view_panel = new MessageView();

		cards = new JPanel();
		cards.setLayout(new CardLayout(0, 0));
		cards.add(list_panel, "name_list");
		cards.add(view_panel, "name_view");
		add(cards, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);

		JMenu mnSort = new JMenu("Sort");
		menuBar.add(mnSort);

		JMenuItem mntmDate = new JMenuItem("Date");
		mnSort.add(mntmDate);

		JMenuItem mntmUnreadread = new JMenuItem("Unread/Read");
		mnSort.add(mntmUnreadread);

		JMenuItem mntmType = new JMenuItem("Type");
		mnSort.add(mntmType);

		JPanel panel_1 = new JPanel();
		menuBar.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JButton btnForward = new JButton("Forward");
		panel_1.add(btnForward, BorderLayout.EAST);
		btnForward.setFont(new Font("Dialog", Font.BOLD, 9));

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		JButton btnBack = new JButton("Back");
		panel_2.add(btnBack, BorderLayout.EAST);
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// if in a message, go back
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.next(cards);
			}
		});
		btnBack.setFont(new Font("Dialog", Font.BOLD, 9));

		JLabel status = new JLabel("");
		status.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(status, BorderLayout.CENTER);

		// add messages from settings
		if (cp.c.getProfile().messages.size() == 0) {
			// Display a message
			JLabel lblThereAreNo = new JLabel("There are no Messages from this Client");
			lblThereAreNo.setHorizontalAlignment(SwingConstants.CENTER);
			list_panel.add(lblThereAreNo, BorderLayout.NORTH);

		} else {
			for (SystemMessage sm : cp.c.getProfile().messages) {
				mlm.addElement(sm);

			}
		}
	}

	public void addMessage() {

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
		return "messages";
	}

}
