package dev.keff.spigot.wine.notifiers;

import org.bukkit.configuration.file.FileConfiguration;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;

public class DiscordNotifier extends Notifier {
    DiscordClient client;

    public DiscordNotifier(String token, FileConfiguration config) {
        super("discord", config);
        client = DiscordClientBuilder.create(token).build();
        client.login().block();
    }

    @Override
    public void notify(String message) {
        // TODO Auto-generated method stub
    }
}