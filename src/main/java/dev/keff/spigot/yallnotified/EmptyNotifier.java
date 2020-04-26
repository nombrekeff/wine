package dev.keff.spigot.yallnotified;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class EmptyNotifier extends Notifier {
    public EmptyNotifier(FileConfiguration config) {
        super(config);
    }

    @Override
    void notify(String message) {
        Bukkit.getLogger().info("[EmptyNotifier]: " + message);
    }
}