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
package subterranean.webserver;

import java.io.InputStream;

import subterranean.crimson.universal.Logger;
import webserver.NanoHTTPD;
import webserver.NanoHTTPD.Response.Status;

public class AppletServer extends NanoHTTPD {

	public AppletServer(int port) {
		super(port);

	}

	@Override
	public Response serve(IHTTPSession session) {
		Method method = session.getMethod();
		String uri = session.getUri();
		Logger.add("uri: " + uri);

		if (uri.contains("crimsonWeb.jar")) {
			Logger.add("Serving crimsonWeb.jar");
			// looking for the jar
			InputStream is = null;
			is = getClass().getResourceAsStream("/subterranean/webserver/crimsonWeb.jar");

			return new NanoHTTPD.Response(Status.OK, "application/java-archive", is);
		} else {
			Logger.add("Serving html file");
			// looking for the html file
			String main = "<!DOCTYPE html><html><body><applet code = 'subterranean.crimson.web.AppletInterface' archive = '127.0.0.1/crimsonWeb.jar' width = 600 height = 300></applet></body></html>";
			return new NanoHTTPD.Response(main);
		}

	}

}
