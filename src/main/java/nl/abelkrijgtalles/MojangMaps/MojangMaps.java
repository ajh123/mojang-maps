package nl.abelkrijgtalles.MojangMaps;

import com.samjakob.spigui.SpiGUI;
import nl.abelkrijgtalles.MojangMaps.command.register.RegisterLocationCommand;
import nl.abelkrijgtalles.MojangMaps.command.register.RegisterRoadCommand;
import nl.abelkrijgtalles.MojangMaps.command.using.GoToCommand;
import nl.abelkrijgtalles.MojangMaps.command.using.WhereAmIStandingCommand;
import nl.abelkrijgtalles.MojangMaps.command.util.ReloadConfigsFromDiskCommand;
import nl.abelkrijgtalles.MojangMaps.listener.PlayerJoinListener;
import nl.abelkrijgtalles.MojangMaps.listener.PlayerWalkListener;
import nl.abelkrijgtalles.MojangMaps.object.Road;
import nl.abelkrijgtalles.MojangMaps.util.file.NodesConfigUtil;
import nl.abelkrijgtalles.MojangMaps.util.file.TranslationUtil;
import nl.abelkrijgtalles.MojangMaps.util.other.HTTPUtil;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.DrilldownPie;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class MojangMaps extends JavaPlugin {

    public static SpiGUI spiGUI;

    public boolean isPluginOutdated = false;

    private static void addLanguageChart(Metrics metrics, MojangMaps plugin) {

        metrics.addCustomChart(new DrilldownPie("language", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            String language = plugin.getConfig().getString("language");
            Map<String, Integer> entry = new HashMap<>();
            entry.put(language, 1);

            switch (language) {
                case "af" -> map.put("Afrikaans", entry);
                case "ar" -> map.put("Arabic", entry);
                case "ca" -> map.put("Catalan", entry);
                case "cs" -> map.put("Czech", entry);
                case "da" -> map.put("Danish", entry);
                case "de" -> map.put("German", entry);
                case "el" -> map.put("Greek", entry);
                case "en" -> map.put("English", entry);
                case "es-ES" -> map.put("Spanish", entry);
                case "fi" -> map.put("Finnish", entry);
                case "fr" -> map.put("French", entry);
                case "he" -> map.put("Hebrew", entry);
                case "hu" -> map.put("Hungarian", entry);
                case "it" -> map.put("Italian", entry);
                case "ja" -> map.put("Japanese", entry);
                case "ko" -> map.put("Korean", entry);
                case "nl" -> map.put("Dutch", entry);
                case "no" -> map.put("Norwegian", entry);
                case "pl" -> map.put("Polish", entry);
                case "pt-BR" -> map.put("Portuguese, Brazilian", entry);
                case "pt-PT" -> map.put("Portuguese", entry);
                case "ro" -> map.put("Romanian", entry);
                case "ru" -> map.put("Russian", entry);
                case "sr" -> map.put("Serbian (Cyrillic)", entry);
                case "sv-SE" -> map.put("Swedish", entry);
                case "tr" -> map.put("Turkish", entry);
                case "uk" -> map.put("Ukrainian", entry);
                case "vi" -> map.put("Vietnamese", entry);
                case "zh-CN" -> map.put("Chinese Simplified", entry);
                case "zh-TW" -> map.put("Chinese Traditional", entry);
                default -> map.put("Other", entry);
            }

            return map;
        }));

    }

    @Override
    public void onDisable() {

        if (isPluginOutdated) {

            getLogger().warning("Don't forget to update Mojang Maps.");

        }

    }

    @Override
    public void onEnable() {

        // Bstats init
        int pluginId = 19295;
        Metrics metrics = new Metrics(this, pluginId);
        addLanguageChart(metrics, this);

        // Config init
        ConfigurationSerialization.registerClass(Road.class);
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        NodesConfigUtil.setup();
        TranslationUtil translationUtil = new TranslationUtil(this);
        translationUtil.updateTranslations();

        // Update stuff
        checkVersion();
        if (isPluginOutdated) {

            getLogger().warning("Mojang Maps is outdated, please download the newest version at: https://github.com/Abelkrijgtalles/mojang-maps/releases/latest");

        }

        // Commands Init
        getCommand("registerlocation").setExecutor(new RegisterLocationCommand());
        getCommand("registerroad").setExecutor(new RegisterRoadCommand());
        getCommand("goto").setExecutor(new GoToCommand(this));
        getCommand("whereamistanding").setExecutor(new WhereAmIStandingCommand());
        getCommand("reloadconfigsfromdisk").setExecutor(new ReloadConfigsFromDiskCommand(this));

        // Listeners/Events init
        getServer().getPluginManager().registerEvents(new PlayerWalkListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        // SpiGUI init
        spiGUI = new SpiGUI(this);

    }

    private void checkVersion() {

        JsonObject latestRelease = HTTPUtil.HTTPRequestJSONObject("https://api.github.com/repos/Abelkrijgtalles/mojang-maps/releases/latest");
        if (!Objects.equals(latestRelease.getString("name"), getDescription().getVersion())) {
            Bukkit.getLogger().info(latestRelease.getString("name"));
            Bukkit.getLogger().info(getDescription().getVersion());
            isPluginOutdated = true;

        }

    }

}
