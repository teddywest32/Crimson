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
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import subterranean.crimson.server.MarketPluginInfo;
import subterranean.crimson.server.PluginOperations;
import subterranean.crimson.server.Server;
import subterranean.crimson.server.ServerCommands;
import subterranean.crimson.server.graphics.frames.MainScreen;
import subterranean.crimson.server.graphics.models.table.PluginTableModel;
import subterranean.crimson.server.graphics.renderers.table.PluginTableRenderer;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.exceptions.NoReplyException;
import javax.swing.border.TitledBorder;
import java.awt.Insets;

public class PluginManager extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private PluginTableModel ptm;
	private JLabel image2;
	private JTextArea txtrDescription;
	private JLabel lblPrice;
	private JLabel lblName;
	private JButton btnBuy;
	private MarketPluginInfo selected;
	private ArrayList<MarketPluginInfo> index;
	private JLabel image1;
	private JPanel img_1;
	private JPanel image_panel1;
	private JPanel image_panel2;
	private StatusLights loading1Lights;
	private StatusLights loading2Lights;
	private StatusLights loading3Lights;
	private StatusLights loading4Lights;
	private JPanel image_panel3;
	private JPanel image_panel4;
	private JLabel image4;
	private JLabel image3;
	private JPanel panel_3;
	private JLabel lblCategory;

	public PluginManager() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PluginManager.class.getResource("/subterranean/crimson/server/graphics/icons/icon.png")));
		setResizable(false);

		setTitle("Plugin Manager");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 749, 527);

		// add some other info
		for (MarketPluginInfo mpi : Server.marketInfo.getPlugins()) {

			if (Server.wp == null) {
				Logger.add("NULL WP");
			}

			if (mpi.getPackagename() == null) {
				Logger.add("NULL Packagename");
			}

			mpi.setPurchased(Server.wp.isPurchased(mpi.getPackagename()));

			mpi.setInstalled(Server.installed(mpi.getPackagename()));

			try {
				mpi.setOutdated(subterranean.crimson.universal.Utilities.laterVersion(mpi.getVersion(), Server.getPluginVersion(mpi.getPackagename())));
			} catch (Exception e) {
				// no big deal; this plugin is probably not installed
			}

		}

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		ptm = new PluginTableModel();
		contentPane.setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		contentPane.add(menuBar, BorderLayout.NORTH);

		JMenu mnCrimson = new JMenu("Crimson");
		menuBar.add(mnCrimson);

		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Server.getSettings().setUserEmail("");
				Server.getSettings().setUserPassword("");
				Server.loggedIN = false;

				dispose();

			}
		});
		mnCrimson.add(mntmLogout);

		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);

		JRadioButton mntmInstalledPlugins = new JRadioButton("Installed Plugins");
		mntmInstalledPlugins.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lblCategory = new JLabel("Category: Installed | Plugins: " + Server.marketInfo.getPlugins().size());

				ArrayList<MarketPluginInfo> n = new ArrayList<MarketPluginInfo>();
				for (MarketPluginInfo mpi : index) {
					if (Server.installed(mpi.getPackagename())) {
						n.add(mpi);
					}
				}

				ptm.add(n);
			}
		});
		mnView.add(mntmInstalledPlugins);

		JRadioButton mntmAvailablePlugins = new JRadioButton("Available Plugins");
		mntmAvailablePlugins.setSelected(true);
		mntmAvailablePlugins.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lblCategory = new JLabel("Category: Available | Plugins: " + Server.marketInfo.getPlugins().size());

				ArrayList<MarketPluginInfo> n = new ArrayList<MarketPluginInfo>();
				for (MarketPluginInfo mpi : index) {
					if (!Server.installed(mpi.getPackagename())) {
						n.add(mpi);
					}
				}

				ptm.add(n);
			}
		});
		mnView.add(mntmAvailablePlugins);

		JRadioButton mntmOfficialPlugins = new JRadioButton("Official Plugins");
		mntmOfficialPlugins.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lblCategory = new JLabel("Category: Official | Plugins: " + Server.marketInfo.getPlugins().size());

				ArrayList<MarketPluginInfo> n = new ArrayList<MarketPluginInfo>();
				for (MarketPluginInfo mpi : index) {
					if (mpi.getType().equals("Official")) {
						n.add(mpi);
					}
				}

				ptm.add(n);
			}
		});
		mnView.add(mntmOfficialPlugins);

		JRadioButton mntmrdPartyPlugins = new JRadioButton("3rd Party Plugins");
		mntmrdPartyPlugins.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lblCategory = new JLabel("Category: 3rd Party | Plugins: " + Server.marketInfo.getPlugins().size());

				ArrayList<MarketPluginInfo> n = new ArrayList<MarketPluginInfo>();
				for (MarketPluginInfo mpi : index) {
					if (mpi.getType().equals("3rd Party")) {
						n.add(mpi);
					}
				}

				ptm.add(n);
			}
		});
		mnView.add(mntmrdPartyPlugins);

		ButtonGroup bg = new ButtonGroup();
		bg.add(mntmrdPartyPlugins);
		bg.add(mntmOfficialPlugins);
		bg.add(mntmAvailablePlugins);
		bg.add(mntmInstalledPlugins);

		JMenu mnLocal = new JMenu("Local");
		menuBar.add(mnLocal);

		JMenuItem mntmInstallLocalPlugin = new JMenuItem("Install Local Plugin");
		mntmInstallLocalPlugin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// open new install dialog
				InstallLocalPlugin ilp = new InstallLocalPlugin();
				ilp.setVisible(true);

			}
		});
		mnLocal.add(mntmInstallLocalPlugin);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		lblCategory = new JLabel("Category: Available | Plugins: " + Server.marketInfo.getPlugins().size());
		lblCategory.setFont(new Font("URW Gothic", Font.BOLD, 13));
		lblCategory.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblCategory);

		JPanel selection_panel = new JPanel();
		panel.add(selection_panel);
		selection_panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		selection_panel.add(scrollPane);

		table = new JTable(ptm);
		table.setFillsViewportHeight(true);
		table.setDefaultRenderer(String.class, new PluginTableRenderer());
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int source = table.getSelectedRow();
				if (source == -1) {
					clearFields();

					return;
				}
				selected = ptm.getRow(source);

				// start screenshots loading in a new thread
				new Thread(new Runnable() {
					public void run() {

						cycleScreenshots();

						ArrayList<ImageIcon> imgs = null;
						if (selected.getScreens() == null) {
							try {
								imgs = ServerCommands.getScreenshots(selected.getPackagename());
							} catch (NoReplyException e) {
								cycleScreenshots();
								return;
							}
							selected.setScreens(imgs);

						} else {
							imgs = selected.getScreens();

						}

						if (imgs != null && imgs.size() != 0) {
							image1.setIcon(GraphicUtilities.resize(imgs.get(0), 125, 75));
							image2.setIcon(GraphicUtilities.resize(imgs.get(1), 125, 75));
							image3.setIcon(GraphicUtilities.resize(imgs.get(2), 125, 75));
							image4.setIcon(GraphicUtilities.resize(imgs.get(3), 125, 75));
						}

						cycleScreenshots();

					}

				}).start();

				lblName.setText(selected.getName());
				panel_3.setBorder(new TitledBorder(new LineBorder(new Color(64, 64, 64)), selected.getName(), TitledBorder.CENTER, TitledBorder.TOP, null, null));

				if (!selected.isInstalled()) {
					// fill in fields

					if (selected.getPrice() != 0) {
						if (selected.isPurchased()) {
							lblPrice.setText("Purchased");
							btnBuy.setText("Install");
						} else {
							lblPrice.setText("$" + selected.getPrice());
							btnBuy.setText("Buy");
						}

					} else {
						lblPrice.setText("Free");
						btnBuy.setText("Install");
					}
					txtrDescription.setText(selected.getDescription());

				} else {
					// This plugin is already installed
					// fill in fields
					lblPrice.setText("Installed");
					txtrDescription.setText(selected.getDescription());
					// change buy button text
					if (selected.isOutdated()) {
						btnBuy.setText("Update");
					} else {
						btnBuy.setText("Uninstall");
					}

				}
				btnBuy.setEnabled(true);

			}
		});
		scrollPane.setViewportView(table);

		JPanel cards = new JPanel();
		cards.setPreferredSize(new Dimension(500, 700));
		panel.add(cards);
		cards.setLayout(new CardLayout(0, 0));

		JPanel details_panel = new JPanel();
		cards.add(details_panel, "name_8662250962402");
		details_panel.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 60, 432, 205);
		details_panel.add(scrollPane_1);

		txtrDescription = new JTextArea();
		txtrDescription.setEditable(false);
		txtrDescription.setLineWrap(true);
		txtrDescription.setWrapStyleWord(true);
		scrollPane_1.setViewportView(txtrDescription);

		image_panel1 = new JPanel();
		image_panel1.setBorder(new LineBorder(new Color(0, 0, 0)));
		image_panel1.setBounds(456, 105, 125, 75);
		details_panel.add(image_panel1);
		image_panel1.setLayout(new CardLayout(0, 0));

		img_1 = new JPanel();
		img_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// open this image in a viewer
				ScreenViewer sv = new ScreenViewer(selected.getScreens(), 0);
				sv.setVisible(true);
			}
		});
		image_panel1.add(img_1, "name_3310496464006");
		img_1.setLayout(new BorderLayout(0, 0));

		image1 = new JLabel("");
		img_1.add(image1, BorderLayout.SOUTH);

		JPanel loading1 = new JPanel();
		image_panel1.add(loading1, "name_3305984894308");

		loading1Lights = new StatusLights();
		loading1Lights.setPreferredSize(new Dimension(9, 25));
		loading1.add(loading1Lights);

		image_panel2 = new JPanel();
		image_panel2.setBorder(new LineBorder(new Color(0, 0, 0)));
		image_panel2.setBounds(593, 105, 125, 75);
		details_panel.add(image_panel2);
		image_panel2.setLayout(new CardLayout(0, 0));

		JPanel img2 = new JPanel();
		img2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// open this image in a viewer
				ScreenViewer sv = new ScreenViewer(selected.getScreens(), 1);
				sv.setVisible(true);
			}
		});
		image_panel2.add(img2, "name_3374526318631");
		img2.setLayout(new BorderLayout(0, 0));

		image2 = new JLabel("");
		img2.add(image2, BorderLayout.NORTH);

		JPanel loading2 = new JPanel();
		image_panel2.add(loading2, "name_3370877331678");

		loading2Lights = new StatusLights();
		loading2Lights.setPreferredSize(new Dimension(9, 25));
		loading2.add(loading2Lights);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.DARK_GRAY));
		panel_2.setBounds(12, 12, 432, 36);
		details_panel.add(panel_2);
		panel_2.setLayout(null);

		lblName = new JLabel("");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblName.setFont(new Font("URW Gothic", Font.BOLD, 15));
		lblName.setBounds(0, 0, 191, 36);
		panel_2.add(lblName);

		btnBuy = new JButton("Buy");
		btnBuy.setMargin(new Insets(2, 2, 2, 2));
		btnBuy.setBounds(355, 9, 65, 19);
		panel_2.add(btnBuy);
		btnBuy.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!btnBuy.isEnabled()) {
					return;
				}
				switch (btnBuy.getText()) {
				case ("Buy"): {
					// take user to website where they can get the plugin
					if (Desktop.isDesktopSupported()) {
						try {
							Desktop.getDesktop().browse(new URI("https://subterranean-security.com/store/index.php?route=product/product&product_id=" + selected.getModel()));
							return;
						} catch (IOException | URISyntaxException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
					// could not navigate to page

					break;
				}
				case ("Install"): {
					Runnable r = new Runnable() {

						@Override
						public void run() {
							// Install the plugin
							Notice rn = new Notice("Downloading and Installing: " + selected.getName());
							rn.setLocationRelativeTo(null);
							rn.setVisible(true);
							dispose();
							Logger.add("Downloading plugin...");
							byte[] unpacked = null;
							try {
								unpacked = ServerCommands.downloadPlugin(selected.getPackagename());
							} catch (NoReplyException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							rn.setVisible(false);
							rn = null;
							if (unpacked != null) {
								Logger.add("Done");

								PluginOperations.installRemote(unpacked, selected.getPackagename());

							} else {
								MainScreen.window.addNotification("Failed to download plugin");
								Logger.add("Failed to download plugin");
							}

						}

					};

					new Thread(r).start();

					break;
				}
				case ("Uninstall"): {
					// Just remove the plugin info
					for (int i = 0; i < Server.getSettings().getPlugins().size(); i++) {
						if (Server.getSettings().getPlugins().get(i).packagename.equals(selected.getPackagename())) {
							Server.getSettings().getPlugins().remove(i);
							break;
						}
					}

					// update the gui
					btnBuy.setText("Install");
					lblPrice.setText("Uninstalled");

					break;
				}
				case ("Update"): {
					// update the plugin
					Notice rn = new Notice("Updating: " + selected.getName());
					rn.setLocationRelativeTo(null);
					rn.setVisible(true);
					dispose();
					Logger.add("Downloading plugin...");
					byte[] unpacked = null;
					try {
						unpacked = ServerCommands.downloadPlugin(selected.getPackagename());
					} catch (NoReplyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Logger.add("Done");

					PluginOperations.updateRemote(unpacked, selected.getPackagename());

					break;
				}

				}

			}
		});
		btnBuy.setEnabled(false);

		JButton btnWebsite = new JButton("Website");
		btnWebsite.setMargin(new Insets(2, 2, 2, 2));
		btnWebsite.setEnabled(false);
		btnWebsite.setBounds(286, 9, 65, 19);
		panel_2.add(btnWebsite);
		btnWebsite.setFont(new Font("Dialog", Font.BOLD, 11));

		lblPrice = new JLabel("");
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setBounds(193, 9, 88, 19);
		panel_2.add(lblPrice);

		image_panel3 = new JPanel();
		image_panel3.setBorder(new LineBorder(new Color(0, 0, 0)));
		image_panel3.setBounds(456, 190, 125, 75);
		details_panel.add(image_panel3);
		image_panel3.setLayout(new CardLayout(0, 0));

		JPanel img3 = new JPanel();
		img3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// open this image in a viewer
				ScreenViewer sv = new ScreenViewer(selected.getScreens(), 2);
				sv.setVisible(true);
			}
		});
		image_panel3.add(img3, "name_26701962112142");
		img3.setLayout(new BorderLayout(0, 0));

		image3 = new JLabel("");
		img3.add(image3, BorderLayout.CENTER);

		JPanel panel_6 = new JPanel();
		loading3Lights = new StatusLights();
		loading3Lights.setPreferredSize(new Dimension(9, 25));
		panel_6.add(loading3Lights);
		image_panel3.add(panel_6, "name_26718276848760");

		image_panel4 = new JPanel();
		image_panel4.setBorder(new LineBorder(new Color(0, 0, 0)));
		image_panel4.setBounds(593, 190, 125, 75);
		details_panel.add(image_panel4);
		image_panel4.setLayout(new CardLayout(0, 0));

		JPanel img4 = new JPanel();
		img4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// open this image in a viewer
				ScreenViewer sv = new ScreenViewer(selected.getScreens(), 3);
				sv.setVisible(true);
			}
		});
		image_panel4.add(img4, "name_26728241773318");
		img4.setLayout(new BorderLayout(0, 0));

		image4 = new JLabel("");
		img4.add(image4, BorderLayout.CENTER);

		JPanel panel_8 = new JPanel();
		loading4Lights = new StatusLights();
		loading4Lights.setPreferredSize(new Dimension(9, 25));
		panel_8.add(loading4Lights);
		image_panel4.add(panel_8, "name_26745822278090");

		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(64, 64, 64)), "", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_3.setBounds(456, 12, 261, 84);
		details_panel.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setFont(new Font("Dialog", Font.BOLD, 11));
		lblAuthor.setBounds(12, 20, 53, 15);
		panel_3.add(lblAuthor);

		JLabel author = new JLabel("");
		author.setFont(new Font("Dialog", Font.BOLD, 11));
		author.setBounds(115, 19, 134, 15);
		panel_3.add(author);

		JLabel lblReleased = new JLabel("Last Release:");
		lblReleased.setFont(new Font("Dialog", Font.BOLD, 11));
		lblReleased.setBounds(12, 38, 91, 15);
		panel_3.add(lblReleased);

		JLabel date = new JLabel("");
		date.setFont(new Font("Dialog", Font.BOLD, 11));
		date.setBounds(115, 38, 134, 15);
		panel_3.add(date);

		JLabel lblSize = new JLabel("Size:");
		lblSize.setFont(new Font("Dialog", Font.BOLD, 11));
		lblSize.setBounds(12, 56, 70, 15);
		panel_3.add(lblSize);

		JLabel size = new JLabel("");
		size.setBounds(115, 56, 134, 15);
		panel_3.add(size);

		JPanel unselected = new JPanel();
		cards.add(unselected, "name_6309822349377");

		JLabel lblLoading = new JLabel("Loading");
		unselected.add(lblLoading);

		retrievePluginList();
	}

	public void retrievePluginList() {
		index = Server.marketInfo.getPlugins();
		ptm.add(index);

	}

	public void clearFields() {
		table.clearSelection();
		lblName.setText("");
		lblPrice.setText("");
		txtrDescription.setText("");
		btnBuy.setText("Buy");
		btnBuy.setEnabled(false);
		ptm.fireTableDataChanged();
	}

	public void cycleScreenshots() {

		CardLayout cardLayout = (CardLayout) image_panel1.getLayout();
		cardLayout.next(image_panel1);
		cardLayout = (CardLayout) image_panel2.getLayout();
		cardLayout.next(image_panel2);
		cardLayout = (CardLayout) image_panel3.getLayout();
		cardLayout.next(image_panel3);
		cardLayout = (CardLayout) image_panel4.getLayout();
		cardLayout.next(image_panel4);

		if (loading1Lights.isAnimating()) {
			loading1Lights.stopAnimation();
			loading2Lights.stopAnimation();
			loading3Lights.stopAnimation();
			loading4Lights.stopAnimation();
		} else {
			loading1Lights.animate("indeterminate", Color.CYAN, null);
			loading2Lights.animate("indeterminate", Color.CYAN, null);
			loading3Lights.animate("indeterminate", Color.CYAN, null);
			loading4Lights.animate("indeterminate", Color.CYAN, null);
		}

	}
}
