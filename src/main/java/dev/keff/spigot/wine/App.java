package dev.keff.spigot.wine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import dev.keff.spigot.wine.commands.YnCommand;
import dev.keff.spigot.wine.notifiers.TelegramNotifier;
import dev.keff.spigot.wine.notifiers.WebhookNotifier;
import dev.keff.spigot.wine.notifiers.Notifier;

public class App extends JavaPlugin {

    public static List<String> NOTIFIERS = Arrays.asList("telegram", "webhook", "discord");

    @Override
    public void onEnable() {
        Logger logger = this.getLogger();
        FileConfiguration config = this.getConfig();
        List<Notifier> notifiers = new ArrayList<Notifier>();

        logger.info("Enabling...");

        // Save config
        config.options().copyDefaults(true);
        this.saveConfig();

        try {
            // Only add telegram notifier if enabled
            if (config.getBoolean("telegram.enabled")) {
                notifiers.add(new TelegramNotifier(config));
                logger.info("[Telegram Notifier]: Enabled");
            }

            // Only add discord notifier if enabled
            if (config.getBoolean("discord.enabled")) {
                // TODO
            }

            // webhook notifier
            if (config.getBoolean("webhook.enabled")) {
                notifiers.add(new WebhookNotifier(config));
                logger.info("[WebhookNotifier]: Enabled");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "ERR: " + e.getMessage());
            e.printStackTrace();
        }

        // Register event listener
        this.getServer().getPluginManager().registerEvents(new EventListener(notifiers, config), this);

        // Register commands
        List<String> aliases = new ArrayList<String>();
        aliases.add("/yall");
        aliases.add("/wine");
        aliases.add("/yn");

        PluginCommand command = this.getCommand("yn");
        YnCommand cmd = new YnCommand(config);

        // command.setPermission("wine.commands");
        command.setTabCompleter(cmd);
        command.setAliases(aliases);
        command.setExecutor(cmd);

        // Setup update checker if enabled in config
        if (config.getBoolean("update_checker")) {
            UpdateChecker.of(this).resourceId(77962).handleResponse((versionResponse, version) -> {
                switch (versionResponse) {
                    case FOUND_NEW:
                        Bukkit.broadcastMessage("New version of the plugin was found: " + version
                                + " Download it at https://www.spigotmc.org/resources/wine.77962/");
                        break;
                    case UNAVAILABLE:
                        Bukkit.broadcastMessage("Unable to perform an update check.");
                    default:
                        break;
                }
            }).check();
        }

        logger.info("Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Closing down!");
    }
}