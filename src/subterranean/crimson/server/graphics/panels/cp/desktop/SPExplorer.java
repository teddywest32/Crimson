package subterranean.crimson.server.graphics.panels.cp.desktop;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import subterranean.crimson.server.commands.ClientCommands;
import subterranean.crimson.server.graphics.ControlPanel;
import subterranean.crimson.server.graphics.models.table.TransferTableModel;
import subterranean.crimson.server.graphics.panels.cp.CPanel;
import subterranean.crimson.universal.transfer.DownloadTransfer;
import subterranean.crimson.universal.transfer.Transfer;
import subterranean.crimson.universal.transfer.UploadTransfer;

public class SPExplorer extends CPanel {

	private static final long serialVersionUID = 1L;
	private JTable transfer_table;
	public TransferTableModel ttm;

	public JTabbedPane explorer_tabbedPane;
	public JPanel transfers_panel;

	public SPExplorer(final ControlPanel cp) {
		setLayout(new CardLayout(0, 0));
		explorer_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(explorer_tabbedPane, "name_3796495812436");
		ttm = new TransferTableModel();

		JPanel explorer_panel = new JPanel();
		explorer_panel.setLayout(new BorderLayout(0, 0));
		SPExplorerFiles files = new SPExplorerFiles(cp, this);
		explorer_panel.add(files, BorderLayout.CENTER);
		explorer_tabbedPane.addTab("File Explorer", null, explorer_panel, null);

		transfer_table = new JTable();
		transfers_panel = new JPanel();
		explorer_tabbedPane.addTab("Transfers", null, transfers_panel, null);
		transfers_panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_10 = new JScrollPane();
		transfer_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				final JTable source = (JTable) e.getSource();
				final int sourceRow = source.rowAtPoint(e.getPoint());
				if (sourceRow == -1) {
					return;
				}

				final Transfer t = ttm.list.get(sourceRow);

				if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {// right
																			// click

					// select row
					if (!source.isRowSelected(sourceRow)) {
						source.changeSelection(sourceRow, 0, false, false);
					}

					JPopupMenu popup = new JPopupMenu();

					JMenuItem pause = new JMenuItem();
					JMenuItem resume = new JMenuItem();
					JMenuItem terminate = new JMenuItem();

					// pause menu
					pause.setText("Pause Transfer");
					pause.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// pause the transfer
							if (t instanceof UploadTransfer) {
								UploadTransfer ut = (UploadTransfer) t;
								ut.pause();
								ClientCommands.transfer_pause(cp.c, t.transferId);
							} else {
								DownloadTransfer dt = (DownloadTransfer) t;
								ClientCommands.transfer_pause(cp.c, t.transferId);
								dt.pause();
							}

						}

					});

					// Resume menu
					resume.setText("Resume Transfer");
					resume.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// resume the transfer
							if (t instanceof UploadTransfer) {
								UploadTransfer ut = (UploadTransfer) t;
								ut.unpause();
								ClientCommands.transfer_resume(cp.c, t.transferId);
							} else {
								DownloadTransfer dt = (DownloadTransfer) t;
								dt.unpause();
								ClientCommands.transfer_resume(cp.c, t.transferId);
							}

						}

					});

					// terminate menu
					terminate.setText("Terminate Transfer");
					terminate.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// stop the transfer
							if (t instanceof UploadTransfer) {
								UploadTransfer ut = (UploadTransfer) t;
								ut.terminate();
								ClientCommands.transfer_terminate(cp.c, t.transferId);
							} else {
								DownloadTransfer dt = (DownloadTransfer) t;
								dt.terminate();
								ClientCommands.transfer_terminate(cp.c, t.transferId);
							}

						}

					});

					if (t.status.equals("PAUSED")) {
						// display resume
						popup.add(resume);
						popup.add(terminate);
					} else if (t.status.equals("RUNNING")) {
						popup.add(pause);
						popup.add(terminate);
					} else if (t.status.equals("COMPLETED")) {
						// completed

					}

					popup.show(transfer_table, e.getX(), e.getY());

				}
			}
		});
		transfers_panel.add(scrollPane_10);

		scrollPane_10.setViewportView(transfer_table);
		transfer_table.setModel(ttm);
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
		return "explorers";
	}

}
