package dev.keff.spigot.yallnotified;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang.text.StrSubstitutor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    static String EVENT_ON_JOIN = "onJoin";

    Notifier notifier;
    FileConfiguration config;
    Logger logger;

    ConnectionListener(Notifier notifier, FileConfiguration config) {
        this.notifier = notifier;
        this.config = config;
        this.logger = Bukkit.getLogger();
    }

    public String formatMessage(String path, Map<String, String> values) {
        String template = config.getString(path);
        String message = StrSubstitutor.replace(template, values, "{", "}");
        return message;
    }

    public void notifyPlayerEvent(PlayerEvent event) {
        String eventName = event.getEventName();
        this.logger.info("eventName: " + eventName);

        if (config.getBoolean("telegram.events." + eventName)) {
            String name = event.getPlayer().getName();
            Map<String, String> values = new HashMap<>();
            values.put("name", name);

            String outputMsg = formatMessage("telegram.message_formats." + eventName, values);
            notifier.notify(outputMsg);
        }
    }

    public void notifyEntityEvent(EntityEvent event) {
        String eventName = event.getEventName();
        this.logger.info("eventName: " + eventName);

        if (config.getBoolean("telegram.events." + eventName)) {
            Entity entity = event.getEntity();
            String name = entity.getName();
            Location deathLocation = entity.getLocation();

            Map<String, String> values = new HashMap<>();
            values.put("name", name);
            values.put("death_x", Double.toString(deathLocation.getX()));
            values.put("death_y", Double.toString(deathLocation.getY()));
            values.put("death_z", Double.toString(deathLocation.getZ()));
            values.put("death_cause", event.getEntity().getLastDamageCause().toString());

            String outputMsg = formatMessage("telegram.message_formats." + eventName, values);
            notifier.notify(outputMsg);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.notifyPlayerEvent(event);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.notifyPlayerEvent(event);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        this.notifyEntityEvent(event);
    }

}