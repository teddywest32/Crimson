package subterranean.crimson.universal.translation;

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

import java.util.Locale;
import java.util.ResourceBundle;

import subterranean.crimson.universal.Logger;

public class T {

	private static ResourceBundle translation;

	public static String t(String s) {
		if (translation == null) {
			loadTranslation("english");
		}

		try {
			return translation.getString(s);
		} catch (Throwable e) {
			return "NO TRANS";
		}

	}

	public static void loadTranslation(String lang) {
		switch (lang.toLowerCase()) {
		case "english": {
			Logger.add("Loading English");
			translation = ResourceBundle.getBundle("subterranean/crimson/universal/translation/lang", Locale.ENGLISH);
			return;
		}
		case "deutsch": {
			Logger.add("Loading German");
			translation = ResourceBundle.getBundle("subterranean/crimson/universal/translation/lang", Locale.GERMAN);
			return;
		}
		case "french": {
			Logger.add("Loading German");
			translation = ResourceBundle.getBundle("subterranean/crimson/universal/translation/lang", Locale.FRENCH);
			return;
		}
		case "nederlands": {
			Logger.add("Loading German");
			translation = ResourceBundle.getBundle("subterranean/crimson/universal/translation/lang", new Locale("Dutch", "", "nl"));
			return;
		}
		case "español": {
			Logger.add("Loading Spanish");
			translation = ResourceBundle.getBundle("subterranean/crimson/universal/translation/lang", new Locale("Spanish", "", "es"));
			return;
		}

		}

	}

}
