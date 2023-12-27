package nl.abelkrijgtalles.MojangMaps.web.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mil.nga.sf.geojson.FeatureCollection;
import mil.nga.sf.geojson.FeatureConverter;
import nl.abelkrijgtalles.MojangMaps.util.object.RoadUtil;

import java.io.IOException;

public class GeoJsonServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		FeatureCollection collection = RoadUtil.getFeatureCollection();
		String geoJson = FeatureConverter.toStringValue(collection);
		response.getWriter().println(geoJson);
	}
}
