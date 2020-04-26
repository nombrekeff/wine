package dev.keff.spigot.yallnotified.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NetherCoordsCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location playerLocation = player.getLocation();
            World currentWorld = playerLocation.getWorld();

            Bukkit.getLogger().info("Running /ncoords on " + currentWorld.getName());
            if (currentWorld.getName().equals("world")) {
                double x = playerLocation.getX();
                double z = playerLocation.getZ();

                player.sendMessage("Nether XZ: " + (x / 8) + " " + (z / 8) + " ");
            } else {
                player.sendMessage("\u00a7c /ncoords only works on \"overworld\"");
            }
        }

        return false;
    }
}