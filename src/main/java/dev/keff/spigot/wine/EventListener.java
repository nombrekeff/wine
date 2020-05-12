package dev.keff.spigot.wine;

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

import dev.keff.spigot.wine.notifiers.Notifier;

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
    boolean isUserNotifiableGlobaly(String name) {
        if (this.config.isSet("ignored_players")) {
            List<String> ignoredUsers = this.config.getStringList("ignored_players");
            boolean canNotify = !ignoredUsers.contains(name);
            this.logger.info("EventListener.isUserNotifiableGlobaly (" + name + "): " + canNotify);
        }

        return true;
    }

    boolean isEventEnabled(String eventName) {
        boolean isEventSetInConfig = config.isSet("telegram.events." + eventName);

        boolean isEnabledSetInEventConfig = config.isSet("telegram.events." + eventName + ".enabled");
        boolean isEnabled = config.getBoolean("telegram.events." + eventName + ".enabled");

        boolean eventDisabledByUser = isEnabledSetInEventConfig && !isEnabled;

        boolean isEventEnabled = isEventSetInConfig && !(eventDisabledByUser);
        this.logger.info("EventListener.isEventEnabled (" + eventName + "): " + isEventEnabled);
        return isEventEnabled;
    }

    public void notifyPlayerEvent(PlayerEvent event) {
        String eventName = event.getEventName();
        boolean eventIsEnabled = isEventEnabled(eventName);

        this.logger.info("eventName: " + eventName + " | enabled: " + eventIsEnabled);

        if (eventIsEnabled) {
            String name = event.getPlayer().getName();
            Map<String, String> interpolationParams = new HashMap<>();
            interpolationParams.put("name", name);

            String outputMsg = formatMessage("telegram.events." + eventName + ".format", interpolationParams);
            this.notifyToAllNotifiers(name, outputMsg);
        }
    }

    public void notifyEntityEvent(EntityEvent event) {
        String eventName = event.getEventName();
        boolean eventIsEnabled = isEventEnabled(eventName);

        this.logger.info("eventName: " + eventName + " | enabled: " + eventIsEnabled);

        if (eventIsEnabled) {
            Entity entity = event.getEntity();
            String name = entity.getName();
            Location deathLocation = entity.getLocation();

            Map<String, String> interpolationParams = new HashMap<>();
            interpolationParams.put("name", name);
            interpolationParams.put("death_x", String.format("%.2f", deathLocation.getX()));
            interpolationParams.put("death_y", String.format("%.2f", deathLocation.getY()));
            interpolationParams.put("death_z", String.format("%.2f", deathLocation.getZ()));
            interpolationParams.put("death_cause", entity.getLastDamageCause().getCause().name());
            interpolationParams.put("world", deathLocation.getWorld().getName());

            String outputMsg = formatMessage("telegram.events." + eventName + ".format", interpolationParams);
            this.notifyToAllNotifiers(name, outputMsg);
        }
    }

    public void notifyToAllNotifiers(String user, String message) {
        for (int index = 0; index < notifiers.size(); index++) {
            Notifier notif = notifiers.get(index);
            if (notif.isEnabled() && notif.canNotifyUser(user)) {
                notif.notify(message);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (this.isUserNotifiableGlobaly(event.getPlayer().getName())) {
            this.notifyPlayerEvent(event);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (this.isUserNotifiableGlobaly(event.getPlayer().getName())) {
            this.notifyPlayerEvent(event);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (this.isUserNotifiableGlobaly(event.getEntity().getName())) {
            this.notifyEntityEvent(event);
        }
    }
}