/*
 * Copyright (C) 2023 Abel van Hulst/Abelkrijgtalles/Abelpro678
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package nl.abelkrijgtalles.MojangMaps.object;

import nl.abelkrijgtalles.MojangMaps.util.file.MessageUtil;

import nl.abelkrijgtalles.MojangMaps.util.geo_json.RoadType;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Road implements ConfigurationSerializable {
    private String name;

    private final RoadType type;

    private final List<Integer> locations;

    public Road(List<Integer> locations, RoadType type) {
        this.locations = locations;
        this.type = type;
    }

    public Road(String name, List<Integer> locations, RoadType type) {
        this.name = name;
        this.locations = locations;
        this.type = type;
    }

    public static Road deserialize(Map<String, Object> args) {
        String name = (String) args.get("name");
        String type = (String) args.get("type");
        List<Integer> locations = (List<Integer>) args.get("locations");

        if (locations == null) {
            throw new IllegalArgumentException("There aren't any locations in this road.");
        }

        RoadType roadType = RoadType.Unclassified;
        if (type != null) {
            roadType = RoadType.getType(name);
        }

        if (name != null) {
            return new Road(name, locations, roadType);
        } else {
            return new Road(locations, roadType);
        }
    }

    public String getName() {
        return Objects.requireNonNullElse(name, MessageUtil.getMessage("unnamedroad"));
    }

    public RoadType getType() {
        return type;
    }

    public List<Integer> getLocations() {
        return locations;
    }

    public int addLocation(Integer location) {
        locations.add(location);
        return locations.size() - 1;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        if (name != null) {
            data.put("name", this.name);
        }

        if (type != null) {
            data.put("type", this.type.getTypeId());
        } else {
            data.put("type", RoadType.Unclassified.getTypeId());
        }

        data.put("locations", this.locations);

        return data;
    }

}
