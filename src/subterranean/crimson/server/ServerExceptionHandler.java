package subterranean.crimson.server;

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

import subterranean.crimson.server.graphics.frames.MainScreen;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Reporter;
import subterranean.crimson.universal.translation.T;

final class ServerExceptionHandler implements Thread.UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread aThread, Throwable aThrowable) {
		aThrowable.printStackTrace();
		// report this exception
		Logger.add(T.t("misc-reporting"));
		Reporter.report(aThrowable);
		MainScreen.window.addNotification(T.t("notification-error"));

	}

}
