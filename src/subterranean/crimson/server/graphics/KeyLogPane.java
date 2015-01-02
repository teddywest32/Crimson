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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;

import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.containers.Key;

public class KeyLogPane extends JTextPane {

	private static final long serialVersionUID = 1L;
	private boolean highlightPhone = true;
	private boolean highlightEmail = true;
	private boolean highlightURL = false;
	private boolean highlightFileURL = false;

	public boolean isHighlightPhone() {
		return highlightPhone;
	}

	public void setHighlightPhone(boolean highlightPhone) {
		this.highlightPhone = highlightPhone;
	}

	public boolean isHighlightEmail() {
		return highlightEmail;
	}

	public void setHighlightEmail(boolean highlightEmail) {
		this.highlightEmail = highlightEmail;
	}

	public boolean isHighlightURL() {
		return highlightURL;
	}

	public void setHighlightURL(boolean highlightURL) {
		this.highlightURL = highlightURL;
	}

	public boolean isHighlightFileURL() {
		return highlightFileURL;
	}

	public void setHighlightFileURL(boolean highlightFileURL) {
		this.highlightFileURL = highlightFileURL;
	}

	public KeyLogPane() {
		setEditable(false);
		setVisible(true);
		this.setContentType("text/html");
	}

	public void loadData(ArrayList<Key> keys) {
		// iterate over keys and start placing them in the pane
		ArrayList<String> lines = new ArrayList<String>();

		String lastTitle = "hopefully no windows with this title SDSA343DH";

		for (Key k : keys) {
			if (lastTitle.equals(k.windowTitle)) {

				// old window
				lines.set(lines.size() - 1, lines.get(lines.size() - 1) + k.key);

			} else {

				// new window
				lines.add("<br><strong><font color=\"#c80000\">\"" + k.windowTitle + "\" at " + k.date.toString() + "</font></strong>");
				lines.add(k.key);
				lastTitle = k.windowTitle;

			}

		}

		StringBuffer text = new StringBuffer();
		for (String s : lines) {
			text.append(highlight(s) + "<br>");
		}
		setText(text.toString());
	}

	public void clear() {
		setText("");
	}

	private String highlight(String s) {

		if (highlightEmail) {

		}
		if (highlightFileURL) {

		}
		if (highlightPhone) {
			Matcher phone = Pattern.compile("\\d{3}-\\d{3}[-]\\d{4}").matcher(s);
			while (phone.find()) {
				Logger.add("Found a Phone Number");
				int start = phone.start();
				int end = phone.end();
				String target = s.substring(start, end);
				s = s.substring(0, start) + "<font color=\"#c80000\">" + target + "</font>" + s.substring(end);
			}
		}
		if (highlightURL) {

		}
		return s;
	}

}
