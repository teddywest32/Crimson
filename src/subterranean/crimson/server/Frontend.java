/*******************************************************************************
 *              Crimson Extended Administration Tool (CrimsonXAT)              *
 *                   Copyright (C) 2015 Subterranean Security                  *
 *                                                                             *
 *     This program is free software: you can redistribute it and/or modify    *
 *     it under the terms of the GNU General Public License as published by    *
 *      the Free Software Foundation, either version 3 of the License, or      *
 *                      (at your option) any later version.                    *
 *                                                                             *
 *       This program is distributed in the hope that it will be useful,       *
 *        but WITHOUT ANY WARRANTY; without even the implied warranty of       *
 *        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        *
 *                 GNU General Public License for more details.                *
 *                                                                             *
 *      You should have received a copy of the GNU General Public License      *
 *      along with this program.  If not, see http://www.gnu.org/licenses      *
 *******************************************************************************/
package subterranean.crimson.server;

import java.awt.HeadlessException;
import java.io.File;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import subterranean.crimson.server.containers.ServerSettings;
import subterranean.crimson.server.graphics.Debug;
import subterranean.crimson.server.graphics.EndUserLicenseAgreement;
import subterranean.crimson.server.graphics.NewTempDir;
import subterranean.crimson.server.graphics.frames.MainScreen;
import subterranean.crimson.universal.FileLocking;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.Platform;
import subterranean.crimson.universal.Version;
import subterranean.crimson.universal.database.Database;
import subterranean.crimson.universal.translation.T;

public class Frontend {

	public static boolean EULAaccepted = false;
	public static MainScreen window;

	public static Debug debug;

	public static void main(String[] argv) {

		if (!Version.release) {
			// debug = new Debug();
			// debug.setVisible(true);
		}

		// test the tempdir and setup settings
		if (!Platform.tempDir.canRead() || !Platform.tempDir.canWrite()) {
			NewTempDir ntd = new NewTempDir();
			ntd.setVisible(true);
			while (ntd.isVisible()) {
				// block
			}
		}

		// check if a lockfile exists
		if (FileLocking.lockExists()) {
			// stop
			Logger.add("Crimson detected that it's already running. If it's not, clear out your temp directory.");
			return;
		} else {
			FileLocking.lock();

		}

		Server.database = new Database(new File("Ssettings.db"));
		if (Server.database.isFirstRun()) {
			// load a new settings object
			Server.database.storeObject(new ServerSettings());
		}

		T.loadTranslation(Server.getSettings().getLang());

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (Server.getSettings().isShowEULA()) {
			// show EULA
			EndUserLicenseAgreement dialog = null;
			try {
				dialog = new EndUserLicenseAgreement(Version.version, true);
			} catch (HeadlessException e1) {
				Logger.add(T.t("error-headless"));
				System.exit(0);
			}
			dialog.setLocationRelativeTo(null);

			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			//
			int waited = 0;
			while (!EULAaccepted) {
				// wait for eula before proceeding
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (waited > 1000) {
					System.exit(0);
				} else {
					waited++;
				}
			}
			//
		}

		// start server
		Logger.add("STARTING SERVER!");
		Server.main(argv);

		//

	}

}
