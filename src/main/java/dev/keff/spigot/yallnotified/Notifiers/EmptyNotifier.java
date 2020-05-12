package dev.keff.spigot.yallnotified.Notifiers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import dev.keff.spigot.yallnotified.Notifier;

public class EmptyNotifier extends Notifier {
    public EmptyNotifier(FileConfiguration config) {
        super(config);
    }

    @Override
    protected void notify(String message) {
        Bukkit.getLogger().info("[EmptyNotifier]: " + message);
    }
}