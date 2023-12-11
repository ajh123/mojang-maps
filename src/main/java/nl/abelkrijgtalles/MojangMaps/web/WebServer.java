package nl.abelkrijgtalles.MojangMaps.web;

import nl.abelkrijgtalles.MojangMaps.MojangMaps;
import org.eclipse.jetty.ee10.servlet.DefaultServlet;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.ResourceFactory;

import java.net.URL;
import java.util.List;
import java.util.logging.Level;

public class WebServer {
	private Server webServer;
	private final MojangMaps plugin;

	public WebServer(MojangMaps plugin) {
		this.plugin = plugin;
	}

	public void start() throws Exception {
		webServer = new Server();
		ServerConnector connector = new ServerConnector(webServer);
		connector.setPort(plugin.getConfig().getInt("port"));
		webServer.setConnectors(new Connector[]{connector});

		final ServletContextHandler context = new ServletContextHandler();
		webServer.setHandler(context);

		// Create and configure a ResourceHandler.
		ResourceHandler handler = new ResourceHandler();
		// Configure the directory where static resources are located.
		handler.setBaseResource(ResourceFactory.of(handler).newResource("/assets/website"));
		// Configure directory listing.
		handler.setDirAllowed(false);
		// Configure welcome files.
		handler.setWelcomeFiles(List.of("index.html"));
		// Configure whether to accept range requests.
		handler.setAcceptRanges(true);

		final ServletHolder servletHolder = new ServletHolder("default", DefaultServlet.class);
		servletHolder.setInitParameter("dirAllowed", "true");
		servletHolder.setInitParameter("cacheControl", "max-age=0,public");
		context.addServlet(servletHolder, "/");
		webServer.start();
	}

	public void stop() {
		try {
			webServer.stop();
		} catch (Exception e) {
			MojangMaps.getMMLogger().log(Level.SEVERE, "An error occurred when stopping the web server.", e);
		}
	}
}
