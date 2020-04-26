package dev.keff.spigot.yallnotified;

import java.util.HashMap;

import com.ibm.icu.text.MessageFormat;

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

    public String formatMessage(String path, String playerName) {
        MessageFormat messageFormat = new MessageFormat(config.getString(path));
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("username", playerName);

        return messageFormat.format(args);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (config.getBoolean("telegram.events.onJoin")) {
            String playerName = event.getPlayer().getName();
            String chatMsg = "Hey there, " + playerName + ", welcome!";
            String outputMsg = formatMessage("telegram.message_formats.onJoin", playerName);

            Bukkit.getLogger().info(chatMsg);
            event.getPlayer().sendMessage(chatMsg);
            notifier.notify(outputMsg);
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (config.getBoolean("telegram.events.onQuit")) {
            String playerName = event.getPlayer().getName();
            String chatMsg = "Bye bye, " + playerName;
            String outputMsg = formatMessage("telegram.message_formats.onQuit", playerName);

            Bukkit.getLogger().info(chatMsg);
            event.getPlayer().sendMessage(chatMsg);
            notifier.notify(outputMsg);
        }
    }
}