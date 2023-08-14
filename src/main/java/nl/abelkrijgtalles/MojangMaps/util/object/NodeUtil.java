package nl.abelkrijgtalles.MojangMaps.util.object;

import nl.abelkrijgtalles.MojangMaps.object.Node;
import nl.abelkrijgtalles.MojangMaps.util.file.MessageUtil;
import nl.abelkrijgtalles.MojangMaps.util.file.NodesConfigUtil;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NodeUtil {

    public static List<Node> addAdjacentNodes() {
        // Create the node list
        List<Node> nodes = new ArrayList<Node>();

        List<Location> locations = NodesConfigUtil.getLocations();

        for (Location location : locations) {
            nodes.add(new Node(Integer.toString(locations.indexOf(location))));
        }

        // Add Adjacent nodes
        for (Node node : nodes) {
            for (Node adjacentNode : nodes) {
                if (adjacentNode.getName() != node.getName() && LocationUtil.isTheSameLocation(getLocationFromNode(node), getLocationFromNode(adjacentNode), 10)) {
                    // TODO: Clean up this mess of a function
                    node.addAdjacentNode(adjacentNode, LocationUtil.getDistance(getLocationFromNode(node), getLocationFromNode(adjacentNode)));
                }
            }
        }

        return nodes;
    }

    public static Location getLocationFromNode(Node node) {

        return NodesConfigUtil.getLocations().get(Integer.parseInt(node.getName()));

    }

    public static void registerLocation(Location location) {
        // TODO: inLocation needs to be renamed in the following functions
        List<Location> locations = (List<Location>) NodesConfigUtil.get().getList("locations");
        for (Location inLocation : locations) {
            if (LocationUtil.isTheSameLocation(location, inLocation, 0)) {
                return;
            }
        }

        NodesConfigUtil.addLocation(location);

    }

    public static void registerLocation(Player p, Location location) {

        List<Location> locations = (List<Location>) NodesConfigUtil.get().getList("locations");
        for (Location inLocation : locations) {
            if (LocationUtil.isTheSameLocation(location, inLocation, 0)) {
                p.sendMessage(ChatColor.RED + MessageUtil.getMessage("locationalreadyregistered"));
                return;
            }
        }

        NodesConfigUtil.addLocation(location);

        p.sendMessage(ChatColor.YELLOW + MessageUtil.getMessage("registerlocation").formatted(location.getBlockX(), location.getBlockZ()));

    }

}
