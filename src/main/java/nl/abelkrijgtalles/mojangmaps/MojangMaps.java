package nl.abelkrijgtalles.mojangmaps;

import nl.abelkrijgtalles.mojangmaps.handlers.config.NodesConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class MojangMaps extends JavaPlugin {

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        NodesConfig.setup();
        NodesConfig.get().options().header("This is auto-generated by Mojang Maps.\nChanging anything in this file will result in a broken Mojang Maps.");
        NodesConfig.save();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
