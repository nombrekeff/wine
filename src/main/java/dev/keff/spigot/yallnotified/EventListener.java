package dev.keff.spigot.yallnotified;

import java.util.HashMap;
import java.util.List;
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

public class EventListener implements Listener {
    List<Notifier> notifiers;
    FileConfiguration config;
    Logger logger;

    EventListener(List<Notifier> notifiers, FileConfiguration config) {
        this.notifiers = notifiers;
        this.config = config;
        this.logger = Bukkit.getLogger();
    }

    public String formatMessage(String path, Map<String, String> values) {
        String template = config.getString(path);
        String message = StrSubstitutor.replace(template, values, "{", "}");
        return message;
    }

    /**
     * Check wether player is ignored
     * 
     * @default true
     */
    boolean isUserNotifiable(String name) {
        if (this.config.isList("ignored_players")) {
            List<String> ignoredUsers = this.config.getStringList("ignored_players");
            return !ignoredUsers.contains(name);
        }

        if (this.config.isList("telegram.ignored_players")) {
            List<String> ignoredUsers = this.config.getStringList("telegram.ignored_players");
            return !ignoredUsers.contains(name);
        }

        return true;
    }

    public void notifyPlayerEvent(PlayerEvent event) {
        String eventName = event.getEventName();
        this.logger.info("eventName: " + eventName);

        if (config.getBoolean("telegram.events." + eventName)) {
            String name = event.getPlayer().getName();
            Map<String, String> values = new HashMap<>();
            values.put("name", name);

            String outputMsg = formatMessage("telegram.message_formats." + eventName, values);
            this.notifyToAll(outputMsg);
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

            values.put("death_x", String.format("%.2f", deathLocation.getX()));
            values.put("death_y", String.format("%.2f", deathLocation.getY()));
            values.put("death_z", String.format("%.2f", deathLocation.getZ()));
            values.put("death_cause", event.getEntity().getLastDamageCause().getCause().name());

            String outputMsg = formatMessage("telegram.message_formats." + eventName, values);
            this.notifyToAll(outputMsg);
        }
    }

    public void notifyToAll(String message) {
        for (int index = 0; index < notifiers.size(); index++) {
            notifiers.get(index).notify(message);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (this.isUserNotifiable(event.getPlayer().getName())) {
            this.notifyPlayerEvent(event);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (this.isUserNotifiable(event.getPlayer().getName())) {
            this.notifyPlayerEvent(event);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (this.isUserNotifiable(event.getEntity().getName())) {
            this.notifyEntityEvent(event);
        }
    }
}