package subterranean.crimson.server.graphics.renderers.table;

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

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import subterranean.crimson.server.MarketPluginInfo;
import subterranean.crimson.universal.Utilities;
import subterranean.crimson.universal.Version;

public class PluginTableRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	private String[] headers = { "Plugin Name", "Latest Version", "Type", "Compatible", "Price" };
	private Color installed_u = new Color(142, 244, 142);
	private Color installed_s = new Color(58, 255, 58);

	private Color official_u = new Color(105, 199, 255);
	private Color official_s = new Color(9, 160, 250);

	private Color outdated_u = new Color(255, 228, 149);
	private Color outdated_s = new Color(255, 191, 0);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		setBorder(noFocusBorder);
		// determine what color this row should be
		MarketPluginInfo p = (MarketPluginInfo) value;
		if (p.isOutdated()) {
			if (isSelected) {
				this.setBackground(outdated_s);
			} else {
				this.setBackground(outdated_u);
			}
		} else if (p.isInstalled()) {
			if (isSelected) {
				this.setBackground(installed_s);
				this.setForeground(new Color(0, 0, 0));
			} else {
				this.setBackground(installed_u);
			}
		} else if (p.getType().equals("Official")) {
			if (isSelected) {
				this.setBackground(official_s);
			} else {
				this.setBackground(official_u);
			}

		}

		switch (headers[column]) {
		case ("Plugin Name"): {// name
			this.setText(p.getName());
			break;
		}
		case ("Type"): {// type
			this.setText(p.getType());
			break;
		}
		case ("Compatible"): {// lcv
			if (Version.version.equals(p.getLcversion())) {
				this.setText("Yes");
			} else {
				this.setText(Utilities.laterVersion(p.getLcversion(), Version.version) ? "No" : "Yes");
			}

			break;
		}
		case ("Price"): {// price
			// keep in this order
			if (p.getPrice() == 0) {
				this.setText("Free");
				break;
			}
			if (p.isPurchased()) {
				this.setText("Purchased");
				break;
			}

			this.setText("$" + p.getPrice());
			break;
		}
		case ("Latest Version"): {
			this.setText(p.getVersion());
			break;
		}

		}

		return this;
	}

}
