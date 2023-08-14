package nl.abelkrijgtalles.MojangMaps.command.util;

import nl.abelkrijgtalles.MojangMaps.MojangMaps;
import nl.abelkrijgtalles.MojangMaps.util.file.NodesConfigUtil;
import nl.abelkrijgtalles.MojangMaps.util.file.TranslationUtil;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public class ReloadConfigsFromDiskCommand implements CommandExecutor {

    private final MojangMaps plugin;

    public ReloadConfigsFromDiskCommand(MojangMaps plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        File config = new File(Bukkit.getServer().getPluginManager().getPlugin("MojangMaps").getDataFolder(), "config.yml");

        NodesConfigUtil.reload();
        try {
            plugin.getConfig().load(config);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getLogger().warning("Could not load config.");
        }

        TranslationUtil translationUtil = new TranslationUtil(plugin);
        translationUtil.updateTranslations();

        return true;
    }
}
