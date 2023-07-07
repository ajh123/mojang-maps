package nl.abelkrijgtalles.mojangmaps.commands;

import nl.abelkrijgtalles.mojangmaps.managers.config.NodesConfig;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RegisterLocationCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {

            Player p = (Player) commandSender;

            List<Location> locations = (List<Location>) NodesConfig.get().getList("locations");
            locations.add(p.getLocation());
            NodesConfig.save();

        }

        return true;
    }
}
