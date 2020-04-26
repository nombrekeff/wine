package dev.keff.spigot.yallnotified;

import org.bukkit.configuration.file.FileConfiguration;

public class DiscordNotifier extends Notifier {

    public DiscordNotifier(FileConfiguration config) {
        super(config);
    }

    @Override
    void notify(String message) {
        // TODO Auto-generated method stub
    }
}