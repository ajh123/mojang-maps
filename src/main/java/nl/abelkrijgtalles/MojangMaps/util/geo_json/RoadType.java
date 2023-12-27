package nl.abelkrijgtalles.MojangMaps.util.geo_json;

import java.util.HashMap;
import java.util.Map;

/**
 RoadType. Represent different type of roads

 See also: <a href="https://wiki.openstreetmap.org/wiki/Highways">Highways</a> **/
public class RoadType {
	public static RoadType Motorway = new RoadType("motorway");
	public static RoadType Trunk = new RoadType("truck");
	public static RoadType Primary = new RoadType("primary");
	public static RoadType Secondary = new RoadType("secondary");
	public static RoadType Tertiary = new RoadType("tertiary");
	public static RoadType Residential = new RoadType("residential");
	public static RoadType Service  = new RoadType("service");
	public static RoadType Unclassified = new RoadType("unclassified");

	private final String type_id;
	private static final Map<String, RoadType> types = new HashMap<>();

	RoadType(String typeId) {
		type_id = typeId;
		if (RoadType.types.containsKey(typeId)) {
			throw new IndexOutOfBoundsException("The road type `"+typeId+"` already exists.");
		}
		RoadType.types.put(typeId, this);
	}

	public String getTypeId() {
		return type_id;
	}

	public static RoadType getType(String typeId) {
		if (RoadType.types.containsKey(typeId)) {
			return RoadType.types.get(typeId);
		}
		return RoadType.Unclassified;
	}
}
