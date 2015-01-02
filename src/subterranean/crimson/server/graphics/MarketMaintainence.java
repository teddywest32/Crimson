package subterranean.crimson.server.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class MarketMaintainence extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public MarketMaintainence() {
		setTitle("Maintainence");
		setResizable(false);
		setBounds(100, 100, 381, 145);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JTextArea txtrTheMarketplaceIs = new JTextArea();
		txtrTheMarketplaceIs.setEditable(false);
		txtrTheMarketplaceIs.setFont(new Font("Dialog", Font.PLAIN, 13));
		txtrTheMarketplaceIs.setWrapStyleWord(true);
		txtrTheMarketplaceIs.setLineWrap(true);
		txtrTheMarketplaceIs.setOpaque(false);
		txtrTheMarketplaceIs.setBackground(new Color(0, 0, 0));
		txtrTheMarketplaceIs.setText("The marketplace is currently closed for maintainence. We apologize for the inconvenience.");
		txtrTheMarketplaceIs.setBounds(12, 12, 347, 56);
		contentPanel.add(txtrTheMarketplaceIs);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
