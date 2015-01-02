package subterranean.crimson.server.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Platform;

public class NewTempDir extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public NewTempDir() {
		setTitle("Select Temporary Directory");
		setResizable(false);
		setBounds(100, 100, 431, 206);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JTextArea txtrCrimsonDetectedThat = new JTextArea();
		txtrCrimsonDetectedThat.setEditable(false);
		txtrCrimsonDetectedThat.setLineWrap(true);
		txtrCrimsonDetectedThat.setWrapStyleWord(true);
		txtrCrimsonDetectedThat.setText("Crimson detected that the temporary directory: \"" + Platform.tempDir + "\" is unreadable or unwritable.  Crimson must have a directory with read and write permissions to continue. Please select a new temporary directory.");
		txtrCrimsonDetectedThat.setOpaque(false);
		txtrCrimsonDetectedThat.setBackground(new Color(0, 0, 0));
		txtrCrimsonDetectedThat.setBounds(12, 12, 411, 63);
		contentPanel.add(txtrCrimsonDetectedThat);
		{
			JLabel lblSelect = new JLabel("Select:");
			lblSelect.setBounds(12, 103, 60, 15);
			contentPanel.add(lblSelect);
		}

		final JTextField textField = new JTextField();
		textField.setBounds(90, 101, 236, 19);
		contentPanel.add(textField);
		textField.setColumns(10);

		JButton btnBrowse = new JButton("Browse");

		btnBrowse.setFont(new Font("Dialog", Font.BOLD, 9));
		btnBrowse.setBounds(338, 98, 71, 25);
		contentPanel.add(btnBrowse);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			final JLabel label = new JLabel("");
			buttonPane.add(label);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Exit");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						System.exit(0);
					}
				});

				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("Select");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						Platform.tempDir = textField.getText();
						// check the dir
						File test = new File(Platform.tempDir);
						if (!test.canRead() || !test.canWrite()) {
							textField.setText("");
							label.setText("Directory is unreadable or unwritable");
							return;
						}

						dispose();

					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			btnBrowse.addMouseListener(new MouseAdapter() {
				private JFileChooser fc = new JFileChooser();

				@Override
				public void mousePressed(MouseEvent e) {

					int returnVal = fc.showOpenDialog(NewTempDir.this);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						// add the file
						if (!file.isDirectory()) {
							label.setText("Must be a directory");
							label.setForeground(Color.red);
							Logger.add("Must be a directory");
						}

						String path = file.getAbsolutePath();
						textField.setText(path + File.separator);

					}

				}
			});
		}
	}
}
