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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.ServerCommands;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.exceptions.NoReplyException;

public class Login extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel login_panel = new JPanel();
	private final JPanel create_panel = new JPanel();
	private JTextField username_field;
	private JPasswordField passwordField;
	private JTextField create_username_field;
	private JTextField email_field;
	private JPasswordField create_password_field;
	private JPasswordField create_password2_field;
	private JPanel card_panel;
	private JLabel lblLoginFailed;
	private JLabel lblLoginFailed2;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnLogin;

	public Login() {
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Login to your Subterranean Security Account");
		setBounds(100, 100, 450, 335);
		getContentPane().setLayout(new BorderLayout());

		card_panel = new JPanel();
		getContentPane().add(card_panel, BorderLayout.CENTER);
		card_panel.setLayout(new CardLayout(0, 0));
		login_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		card_panel.add(login_panel, "LOGIN");
		card_panel.add(create_panel, "CREATE");
		create_panel.setLayout(null);

		JButton btnCreateAccount = new JButton("Create");
		btnCreateAccount.setFont(new Font("Dialog", Font.BOLD, 10));
		btnCreateAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				lblLoginFailed2.setForeground(new Color(0, 102, 0));
				lblLoginFailed2.setText("Creating Account...");
				lblLoginFailed.setForeground(new Color(0, 102, 0));
				lblLoginFailed.setText("Creating Account...");
				Runnable r = new Runnable() {
					public void run() {
						// check fields
						if (!validate_create(create_username_field.getText().toLowerCase(), email_field.getText(), new String(create_password_field.getPassword()), new String(create_password2_field.getPassword()))) {
							return;
						}

						// create account
						String out = null;
						// out =
						// ServerCommands.createAccount(create_username_field.getText().toLowerCase(),
						// email_field.getText(), new
						// String(create_password_field.getPassword()),
						// textField.getText(), textField_1.getText());

						if (out.equals("true")) {
							// switch cards
							CardLayout cardLayout = (CardLayout) card_panel.getLayout();
							cardLayout.next(card_panel);
							lblLoginFailed2.setText("Account created.");
							lblLoginFailed.setText("Account created.");
							// clear out the account data
							create_username_field.setText("");
							email_field.setText("");
							textField.setText("");
							textField_1.setText("");
							create_password2_field.setText("");
							create_password_field.setText("");

						} else {
							lblLoginFailed2.setForeground(Color.RED);
							lblLoginFailed2.setText(out);
							lblLoginFailed.setForeground(Color.RED);
							lblLoginFailed.setText(out);
						}
					}

				};

				new Thread(r).start();

			}
		});
		btnCreateAccount.setBounds(352, 267, 76, 25);
		create_panel.add(btnCreateAccount);

		JLabel lblUsername_1 = new JLabel("Username:");
		lblUsername_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername_1.setBounds(89, 72, 104, 16);
		create_panel.add(lblUsername_1);

		create_username_field = new JTextField();
		create_username_field.setBounds(89, 87, 104, 20);
		create_panel.add(create_username_field);
		create_username_field.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(89, 121, 104, 16);
		create_panel.add(lblEmail);

		email_field = new JTextField();
		email_field.setBounds(89, 136, 104, 20);
		create_panel.add(email_field);
		email_field.setColumns(10);

		JLabel lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword_1.setBounds(89, 168, 104, 16);
		create_panel.add(lblPassword_1);

		create_password_field = new JPasswordField();
		create_password_field.setBounds(89, 185, 104, 20);
		create_panel.add(create_password_field);

		JLabel lblPassword_2 = new JLabel("Password:");
		lblPassword_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword_2.setBounds(245, 168, 104, 16);
		create_panel.add(lblPassword_2);

		create_password2_field = new JPasswordField();
		create_password2_field.setBounds(245, 185, 104, 20);
		create_panel.add(create_password2_field);

		JButton btnCancel_1 = new JButton("Cancel");
		btnCancel_1.setFont(new Font("Dialog", Font.BOLD, 11));
		btnCancel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
		btnCancel_1.setBounds(12, 267, 78, 25);
		create_panel.add(btnCancel_1);

		JButton btnBack = new JButton("Back");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// switch cards
				CardLayout cardLayout = (CardLayout) card_panel.getLayout();
				cardLayout.next(card_panel);
			}
		});
		btnBack.setFont(new Font("Dialog", Font.BOLD, 11));
		btnBack.setBounds(284, 267, 65, 25);
		create_panel.add(btnBack);

		JPanel error_panel2 = new JPanel();
		error_panel2.setBounds(89, 267, 193, 26);
		create_panel.add(error_panel2);

		lblLoginFailed2 = new JLabel("");
		error_panel2.add(lblLoginFailed2);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setHorizontalAlignment(SwingConstants.CENTER);
		lblFirstName.setBounds(245, 72, 104, 15);
		create_panel.add(lblFirstName);

		textField = new JTextField();
		textField.setBounds(245, 87, 104, 19);
		create_panel.add(textField);
		textField.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastName.setBounds(245, 121, 104, 15);
		create_panel.add(lblLastName);

		textField_1 = new JTextField();
		textField_1.setBounds(245, 136, 104, 19);
		create_panel.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblCreateACrimson = new JLabel("Create a Crimson Account");
		lblCreateACrimson.setFont(new Font("URW Gothic", Font.BOLD, 15));
		lblCreateACrimson.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateACrimson.setBounds(89, 45, 260, 15);
		create_panel.add(lblCreateACrimson);
		login_panel.setLayout(null);

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Dialog", Font.BOLD, 11));
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!btnLogin.isEnabled()) {
					return;
				}
				Logger.add("Testing login");
				btnLogin.setEnabled(false);
				username_field.setEnabled(false);
				passwordField.setEnabled(false);

				lblLoginFailed.setForeground(new Color(0, 102, 0));
				lblLoginFailed.setText("Logging in...");
				Runnable r = new Runnable() {
					public void run() {
						// check fields
						if (validate_login(username_field.getText(), new String(passwordField.getPassword()))) {// validate
							String result = null;
							try {
								result = ServerCommands.login(username_field.getText(), new String(passwordField.getPassword()));
							} catch (NoReplyException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							if (result.equals("true")) {
								// successful login
								Server.getSettings().setLoggedIn(true);
								Logger.add("LOGGED IN");
								lblLoginFailed.setText("Logged in!");
								try {
									Thread.sleep(200);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								dispose();

								PluginManager pm = new PluginManager();
								pm.setVisible(true);
								return;
							} else {
								Server.getSettings().setLoggedIn(false);

								Logger.add("LOGIN FAILED");
								// clear password
								passwordField.setText("");
								lblLoginFailed.setForeground(Color.RED);
								lblLoginFailed.setText(result);
								btnLogin.setEnabled(true);
								username_field.setEnabled(true);
								passwordField.setEnabled(true);
								return;
							}

						}
						Server.getSettings().setLoggedIn(false);
						Logger.add("LOGIN FAILED");
						// clear password
						passwordField.setText("");
						lblLoginFailed.setForeground(Color.RED);
						lblLoginFailed.setText("Failed Validation");
						btnLogin.setEnabled(true);
						username_field.setEnabled(true);
						passwordField.setEnabled(true);
					}
				};
				new Thread(r).start();

			}
		});
		btnLogin.setBounds(359, 267, 69, 26);
		login_panel.add(btnLogin);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Dialog", Font.BOLD, 11));
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(12, 267, 78, 26);
		login_panel.add(btnCancel);

		JButton btnCreateAnAccount = new JButton("Create Account");
		btnCreateAnAccount.setMargin(new Insets(2, 7, 2, 7));
		btnCreateAnAccount.setFont(new Font("Dialog", Font.BOLD, 9));
		btnCreateAnAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// go to site
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(new URI("https://www.subterranean-security.com"));
						return;
					} catch (IOException | URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					lblLoginFailed.setText("Navigate to website");
				}
			}
		});
		btnCreateAnAccount.setBounds(258, 267, 96, 26);
		login_panel.add(btnCreateAnAccount);

		URL url = getClass().getResource("/subterranean/crimson/server/graphics/images/splash.png");

		BufferedImage image = null;
		try {
			image = ImageIO.read(url);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedImage resizedImage = resize(image, 428, 200);
		ImageIcon icon = new ImageIcon(resizedImage);

		JLabel lblImage = new JLabel(icon);
		lblImage.setBounds(0, 0, 440, 180);
		login_panel.add(lblImage);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(12, 179, 209, 76);
		login_panel.add(panel);
		panel.setLayout(null);

		JLabel lblUsername = new JLabel("Email:");
		lblUsername.setFont(new Font("URW Gothic", Font.BOLD, 13));
		lblUsername.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUsername.setBounds(0, 12, 75, 15);
		panel.add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("URW Gothic", Font.BOLD, 13));
		lblPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPassword.setBounds(0, 39, 75, 16);
		panel.add(lblPassword);

		username_field = new JTextField();
		username_field.setBounds(83, 10, 114, 20);
		panel.add(username_field);
		username_field.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(83, 38, 114, 20);
		panel.add(passwordField);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(219, 179, 209, 76);
		login_panel.add(panel_1);
		panel_1.setLayout(null);

		String stat1 = Server.marketInfo.getFact1();
		if(stat1 == null){
			stat1 = "";
		}
		
		String stat2 = Server.marketInfo.getFact2();
		if(stat2 == null){
			stat2 = "";
		}
		
		String stat3 = Server.marketInfo.getFact3();
		if(stat3 == null){
			stat3 = "";
		}
		JLabel lblMessage = new JLabel(stat1);
		lblMessage.setFont(new Font("Dialog", Font.BOLD, 10));
		lblMessage.setBounds(12, 12, 185, 15);
		panel_1.add(lblMessage);

		JLabel lblMessage_1 = new JLabel(Server.marketInfo.getFact2());
		lblMessage_1.setFont(new Font("Dialog", Font.BOLD, 10));
		lblMessage_1.setBounds(12, 32, 185, 15);
		panel_1.add(lblMessage_1);

		JLabel lblMessage_2 = new JLabel(Server.marketInfo.getFact3());
		lblMessage_2.setFont(new Font("Dialog", Font.BOLD, 10));
		lblMessage_2.setBounds(12, 52, 185, 15);
		panel_1.add(lblMessage_2);

		JPanel error_panel1 = new JPanel();
		error_panel1.setBounds(93, 267, 162, 26);
		login_panel.add(error_panel1);
		error_panel1.setLayout(new BorderLayout(0, 0));

		lblLoginFailed = new JLabel("");
		error_panel1.add(lblLoginFailed);

	}

	public boolean validate_create(String username, String email, String p1, String p2) {
		// passwords must match
		if (!p1.equals(p2)) {
			lblLoginFailed2.setForeground(Color.RED);
			lblLoginFailed2.setText("Passwords must match");
			return false;
		}
		if (!GraphicUtilities.isValidEmailAddress(email)) {
			lblLoginFailed2.setForeground(Color.RED);
			lblLoginFailed2.setText("Invalid Email");
			return false;
		}
		if (username.length() > 20) {
			lblLoginFailed2.setForeground(Color.RED);
			lblLoginFailed2.setText("Username too long");
			return false;
		}
		if (email.length() > 255) {
			lblLoginFailed2.setForeground(Color.RED);
			lblLoginFailed2.setText("Email too long");
			return false;
		}
		if (p1.length() < 5) {
			lblLoginFailed2.setForeground(Color.RED);
			lblLoginFailed2.setText("Password too short");
			return false;
		}

		return true;
	}

	public boolean validate_login(String username, String password) {

		if (username.length() > 96) {
			lblLoginFailed.setForeground(Color.RED);
			lblLoginFailed.setText("Email too long");
			return false;
		}
		if (!GraphicUtilities.isValidEmailAddress(username)) {
			lblLoginFailed.setForeground(Color.RED);
			lblLoginFailed.setText("Invalid Email");
			return false;
		}
		if (password.length() < 5) {
			lblLoginFailed.setForeground(Color.RED);
			lblLoginFailed.setText("Password too short");
			return false;
		}

		return true;
	}

	public static BufferedImage resize(BufferedImage image, int width, int height) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics2D g2d = (Graphics2D) bi.createGraphics();
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		g2d.drawImage(image, 0, 0, width, height, null);
		g2d.dispose();
		return bi;
	}
}
