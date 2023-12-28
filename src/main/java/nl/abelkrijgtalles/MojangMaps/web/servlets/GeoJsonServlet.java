package nl.abelkrijgtalles.MojangMaps.web.servlets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import mil.nga.sf.geojson.FeatureCollection;
import mil.nga.sf.geojson.FeatureConverter;
import nl.abelkrijgtalles.MojangMaps.util.object.RoadUtil;

import java.io.IOException;
import java.io.OutputStream;

public class GeoJsonServlet implements HttpHandler {
	@Override
	public void handle(HttpExchange t) throws IOException {
		FeatureCollection collection = RoadUtil.getFeatureCollection();
		String response = FeatureConverter.toStringValue(collection);
		t.getResponseHeaders().add("Content-Type", "application/json");
		t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
