package dev.keff.spigot.yallnotified.notifiers;

import java.util.List;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

abstract public class Notifier {
    public Logger logger;
    public String notifierName;
    public FileConfiguration config;

    public Notifier(String name, FileConfiguration config) {
        this.logger = Bukkit.getLogger();
        this.config = config;
        this.notifierName = name;
    }

    public boolean canNotifyUser(String name) {
        if (this.config.isList("telegram.ignored_players")) {
            List<String> ignoredUsers = this.config.getStringList(this.notifierName + ".ignored_players");
            return !ignoredUsers.contains(name);
        }

        return true;
    }

    public abstract void notify(String message);
}