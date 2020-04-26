package dev.keff.spigot.yallnotified;

import java.util.logging.Logger;

import org.bukkit.Bukkit;

abstract public class Notifier {
    Logger logger;

    public Notifier() {
        this.logger = Bukkit.getLogger();
    }

    abstract void notify(String message);
}