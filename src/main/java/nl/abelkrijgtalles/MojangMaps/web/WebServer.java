package nl.abelkrijgtalles.MojangMaps.web;

import com.sun.net.httpserver.HttpServer;
import nl.abelkrijgtalles.MojangMaps.MojangMaps;
import nl.abelkrijgtalles.MojangMaps.web.servlets.GeoJsonServlet;
import nl.abelkrijgtalles.MojangMaps.web.servlets.InfoServlet;

import java.net.InetSocketAddress;
import java.util.logging.Level;

public class WebServer {
	private final MojangMaps plugin;
	private HttpServer server;

	public WebServer(MojangMaps plugin) {
		this.plugin = plugin;
	}

	public void start() throws Exception {
		server = HttpServer.create(new InetSocketAddress(plugin.getConfig().getInt("port")), 0);

		server.createContext("/api/v1/info", new InfoServlet());
		server.createContext("/api/v1/data/geojson", new GeoJsonServlet());

		server.setExecutor(null); // creates a default executor
		server.start();
	}

	public void stop() {
		try {
			server.stop(0);
		} catch (Exception e) {
			MojangMaps.getMMLogger().log(Level.SEVERE, "An error occurred when stopping the web server.", e);
		}
	}
}
