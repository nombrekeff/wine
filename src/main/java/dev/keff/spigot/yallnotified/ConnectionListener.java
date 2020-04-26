package dev.keff.spigot.yallnotified;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    Notifier notifier;
    FileConfiguration config;

    ConnectionListener(Notifier notifier, FileConfiguration config) {
        this.notifier = notifier;
        this.config = config;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (config.getBoolean("telegram.events.onJoin")) {
            String playerName = event.getPlayer().getName();
            String msg = "Hey there, " + playerName + ", welcome!";

            Bukkit.getLogger().info(msg);
            event.getPlayer().sendMessage(msg);
            notifier.notify("A player Joined: **" + playerName + "**");
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (config.getBoolean("telegram.events.onQuit")) {
            String playerName = event.getPlayer().getName();
            String msg = "Bye bye, " + playerName;

            Bukkit.getLogger().info(msg);
            event.getPlayer().sendMessage(msg);
            notifier.notify("A player Left: **" + playerName + "**");
        }
    }
}