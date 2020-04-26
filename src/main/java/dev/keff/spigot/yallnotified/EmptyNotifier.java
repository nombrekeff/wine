package dev.keff.spigot.yallnotified;

import org.bukkit.Bukkit;

public class EmptyNotifier extends Notifier {

    @Override
    void notify(String message) {
        Bukkit.getLogger().info("[EmptyNotifier]: " + message);
    }
}