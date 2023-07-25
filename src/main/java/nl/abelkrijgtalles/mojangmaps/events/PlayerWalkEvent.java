package nl.abelkrijgtalles.mojangmaps.events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import nl.abelkrijgtalles.mojangmaps.MojangMaps;
import nl.abelkrijgtalles.mojangmaps.util.RoadUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerWalkEvent implements Listener {

    private final MojangMaps plugin;

    public PlayerWalkEvent(MojangMaps plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerWalk(PlayerMoveEvent e) {

        Player p = e.getPlayer();

        if (plugin.getConfig().getBoolean("street-actionbar")) {

            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(RoadUtil.getLocationMessage(p)));

        }

    }

}
