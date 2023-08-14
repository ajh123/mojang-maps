package nl.abelkrijgtalles.MojangMaps.util.file;

import nl.abelkrijgtalles.MojangMaps.object.Road;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NodesConfigUtil {

    private static File file;
    private static FileConfiguration customFile;

    // Idk weird setup I guess
    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("MojangMaps").getDataFolder(), "nodes.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                // Write some things in the configuration file
                customFile = YamlConfiguration.loadConfiguration(file);
                get().options().header("This is auto-generated by Mojang Maps.\nChanging anything in this file will result in a broken Mojang Maps.");
                get().addDefault("roads", new ArrayList<Road>());
                get().addDefault("locations", new ArrayList<Location>());
                get().options().copyDefaults(true);
                save();
            } catch (IOException e) {
                Bukkit.getLogger().warning("Could not create the configuration file nodes.yml.");
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return customFile;
    }

    public static void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().warning("Could not save the configuration file nodes.yml.");
        }
        reload();
    }

    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static List<Location> getLocations() {

        return (List<Location>) get().getList("locations");

    }

    public static List<Road> getRoads() {

        return (List<Road>) get().getList("roads");

    }

    public static void addLocation(Location location) {

        getLocations().add(location);
        save();

    }

    public static void addRoad(Road road) {

        getRoads().add(road);
        save();

    }

}
