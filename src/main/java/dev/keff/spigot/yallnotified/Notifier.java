package dev.keff.spigot.yallnotified;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

abstract public class Notifier {
    Logger logger;
    FileConfiguration config;

    public Notifier(FileConfiguration config) {
        this.logger = Bukkit.getLogger();
        this.config = config;
    }

    abstract void notify(String message);
}