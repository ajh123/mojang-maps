package nl.abelkrijgtalles.MojangMaps.web.servlets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class InfoServlet implements HttpHandler {
	@Override
	public void handle(HttpExchange t) throws IOException {
		String response = "{ \"status\": \"ok\"}";
		t.getResponseHeaders().add("Content-Type", "application/json");
		t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
