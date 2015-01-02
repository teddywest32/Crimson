package subterranean.crimson.universal.containers;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

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

public abstract class Settings implements Serializable {

	private static final long serialVersionUID = 1L;
	protected int infoId;
	protected boolean errorReporting;
	protected short errorReportsSent;
	protected String lang;
	protected File pluginDir;

	protected ArrayList<Information> reportBuffer = new ArrayList<Information>();

	public void addReport(Information i) {
		reportBuffer.add(i);
	}

	public Information getBufferedReport() {
		return reportBuffer.remove(0);
	}

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public boolean isErrorReporting() {
		return errorReporting;
	}

	public void setErrorReporting(boolean errorReporting) {
		this.errorReporting = errorReporting;
	}

	public short getErrorReportsSent() {
		return errorReportsSent;
	}

	public void updateErrorReportsSent() {
		this.errorReportsSent++;
	}

	public void setPluginDir(File f) {
		pluginDir = f;
	}

	public File getPluginDir() {
		return pluginDir;
	}

}
