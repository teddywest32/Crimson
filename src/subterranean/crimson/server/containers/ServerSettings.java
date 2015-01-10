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
package subterranean.crimson.server.containers;



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

import subterranean.crimson.server.Server;
import subterranean.crimson.server.sdk.PluginSettings;
import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.containers.Information;
import subterranean.crimson.universal.containers.Options;
import subterranean.crimson.universal.containers.Settings;

public class ServerSettings extends Settings {

	private static final long serialVersionUID = 1L;

	/**
	 * Indicates if Crimson should close to the system tray if supported
	 */
	private boolean closeOnTray;

	/**
	 * Indicates if the eula should be shown on every startup
	 */
	private boolean showEULA;

	/**
	 * Indicates if the detail pane should be shown
	 */
	private boolean showDetails;

	/**
	 * Indicates if helpful information panels should be shown
	 */
	private boolean informationPanels;

	/**
	 * Indicates if entries should be removed when clients disconnect
	 */
	private boolean removeClientonExit;

	/**
	 * Maps clientIDs to dataIDs
	 */
	private HashMap<Integer, Short> connectionProfilePointers;

	/**
	 * Saved values for the payload generator
	 */
	private Options savedJarPayloadOptions;

	/**
	 * Saved values for the downloader generator
	 */
	private Options savedJarDownloaderOptions;

	/**
	 * Saved values for the apk generator
	 */
	private Options savedApkPayloadOptions;

	/**
	 * Saved values for the ipa generator
	 */
	private Options savedIpaPayloadOptions;

	/**
	 * Saved values for the exe generator
	 */
	private Options savedExePayloadOptions;

	/**
	 * Provides rules for showing the notification pane
	 */
	private NotificationPolicy notePolicy;

	/**
	 * Contains serialized listener objects which can be restored on startup
	 */
	private ArrayList<String> listeners;

	/**
	 * Provides persistence of tab states
	 */
	private DesktopTabMemory desktopCP;

	/**
	 * Provides persistence of tab states
	 */
	private MobileTabMemory mobileCP;

	/**
	 * The email used to login to Crimson
	 */
	private String userEmail;

	/**
	 * The user's hashed password
	 */
	private String userPassword;

	/**
	 * The theme
	 */
	private String theme;

	/**
	 * Headers to show on the host list
	 */
	private String[] listHeaders;

	/**
	 * Indicates what to show under nodes in the graph view
	 */
	private String graphText;

	/**
	 * Information that has not been sent to the server
	 */
	private HashSet<Information> infoBuffer;

	/**
	 * Container for plugins to store data
	 */
	private ArrayList<PluginEntry> plugins;

	/**
	 * For dmodule settings persistence
	 */
	private DModuleMemory dmoduleMemory;

	public ServerSettings() {
		setErrorReporting(true);
		setCloseOnTray(false);
		setShowEULA(true);
		setShowDetails(true);
		setRemoveClientonExit(true);
		setInformationPanels(true);
		setListeners(new ArrayList<String>());
		setInfoBuffer(new HashSet<Information>());
		setDesktopCP(new DesktopTabMemory());
		setMobileCP(new MobileTabMemory());
		setSavedJarPayloadOptions(new Options());
		setSavedJarDownloaderOptions(new Options());
		setSavedApkPayloadOptions(new Options());
		setSavedIpaPayloadOptions(new Options());
		setSavedExePayloadOptions(new Options());
		setTheme("System");
		setUserEmail("");
		setUserPassword("");
		setGraphText("Hostname");
		setNotePolicy(new NotificationPolicy());
		setListHeaders(new String[] { "Location", "Client Version", "Current Process", "Activity Status", "Hostname", "Username", "External IP", "Operating System" });
		setDmoduleMemory(new DModuleMemory());
		setPluginDir(new File("plugins"));
		
		connectionProfilePointers = new HashMap<Integer, Short>();
		errorReportsSent = 0;
		plugins = new ArrayList<PluginEntry>();

		switch (Locale.getDefault().getLanguage()) {
		case "es": {
			setLang("Español");
			break;
		}
		case "de": {
			setLang("Deutsch");
			break;
		}
		default: {
			setLang("English");
			break;
		}
		}

	}

	public PluginSettings getPluginSettings(String pac) {
		for (PluginEntry pe : plugins) {
			if (pe.packagename.equals(pac)) {
				return (PluginSettings) Server.database.retrieveObject(pe.settingsPointer);
			}
		}
		return null;
	}

	public boolean isCloseOnTray() {
		return closeOnTray;
	}

	public void setCloseOnTray(boolean closeOnTray) {
		this.closeOnTray = closeOnTray;
	}

	public boolean isShowEULA() {
		return showEULA;
	}

	public void setShowEULA(boolean showEULA) {
		this.showEULA = showEULA;
	}

	public boolean isShowDetails() {
		return showDetails;
	}

	public void setShowDetails(boolean showDetails) {
		this.showDetails = showDetails;
	}

	public boolean isInformationPanels() {
		return informationPanels;
	}

	public void setInformationPanels(boolean informationPanels) {
		this.informationPanels = informationPanels;
	}

	public boolean isRemoveClientonExit() {
		return removeClientonExit;
	}

	public void setRemoveClientonExit(boolean removeClientonExit) {
		this.removeClientonExit = removeClientonExit;
	}

	public Options getSavedJarPayloadOptions() {
		return savedJarPayloadOptions;
	}

	public void setSavedJarPayloadOptions(Options savedJarPayloadOptions) {
		this.savedJarPayloadOptions = savedJarPayloadOptions;
	}

	public Options getSavedJarDownloaderOptions() {
		return savedJarDownloaderOptions;
	}

	public void setSavedJarDownloaderOptions(Options savedJarDownloaderOptions) {
		this.savedJarDownloaderOptions = savedJarDownloaderOptions;
	}

	public Options getSavedApkPayloadOptions() {
		return savedApkPayloadOptions;
	}

	public void setSavedApkPayloadOptions(Options savedApkPayloadOptions) {
		this.savedApkPayloadOptions = savedApkPayloadOptions;
	}

	public Options getSavedIpaPayloadOptions() {
		return savedIpaPayloadOptions;
	}

	public void setSavedIpaPayloadOptions(Options savedIpaPayloadOptions) {
		this.savedIpaPayloadOptions = savedIpaPayloadOptions;
	}

	public Options getSavedExePayloadOptions() {
		return savedExePayloadOptions;
	}

	public void setSavedExePayloadOptions(Options savedExePayloadOptions) {
		this.savedExePayloadOptions = savedExePayloadOptions;
	}

	public NotificationPolicy getNotePolicy() {
		return notePolicy;
	}

	public void setNotePolicy(NotificationPolicy notePolicy) {
		this.notePolicy = notePolicy;
	}

	public ArrayList<String> getListeners() {
		return listeners;
	}

	public void setListeners(ArrayList<String> listeners) {
		this.listeners = listeners;
	}

	public DesktopTabMemory getDesktopCP() {
		return desktopCP;
	}

	public void setDesktopCP(DesktopTabMemory desktopCP) {
		this.desktopCP = desktopCP;
	}

	public MobileTabMemory getMobileCP() {
		return mobileCP;
	}

	public void setMobileCP(MobileTabMemory mobileCP) {
		this.mobileCP = mobileCP;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String[] getListHeaders() {
		return listHeaders;
	}

	public void setListHeaders(String[] listHeaders) {
		this.listHeaders = listHeaders;
	}

	public String getGraphText() {
		return graphText;
	}

	public void setGraphText(String graphText) {
		this.graphText = graphText;
	}

	public HashSet<Information> getInfoBuffer() {
		return infoBuffer;
	}

	public void setInfoBuffer(HashSet<Information> infoBuffer) {
		this.infoBuffer = infoBuffer;
	}

	public ArrayList<PluginEntry> getPlugins() {
		return plugins;
	}

	public void setPlugins(ArrayList<PluginEntry> plugins) {
		this.plugins = plugins;
	}

	public HashMap<Integer, Short> getConnectionProfilePointers() {
		return connectionProfilePointers;
	}

	public DModuleMemory getDmoduleMemory() {
		return dmoduleMemory;
	}

	public void setDmoduleMemory(DModuleMemory dmoduleMemory) {
		this.dmoduleMemory = dmoduleMemory;
	}

	public ConnectionProfile getProfile(int clientID) {
		short diskID = connectionProfilePointers.get(clientID);
		Logger.disk("Getting ConnectionProfile: " + clientID + " with diskID of: " + diskID);
		return (ConnectionProfile) Server.database.retrieveObject(diskID);
	}

}
