package dev.keff.spigot.yallnotified;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
    @Override
    public void onEnable() {
        Logger logger = getLogger();
        FileConfiguration config = getConfig();
        List<Notifier> notifiers = new ArrayList<Notifier>();

        // Save config
        config.options().copyDefaults(true);
        this.saveConfig();

        // Only add telegram notifier if enabled
        if (config.getBoolean("telegram.enabled")) {
            String TG_TOKEN = config.getString("telegram.token");
            List<String> TG_CHAT_IDS = config.getStringList("telegram.chat_ids");
            notifiers.add(new TelegramNotifier(TG_TOKEN, TG_CHAT_IDS, config));
            logger.info("[Telegram Notifier]: Enabled");
        }

        // Only add discord notifier if enabled
        if (config.getBoolean("discord.enabled")) {
            // String TG_TOKEN = config.getString("telegram.token");
            // List<String> TG_CHAT_IDS = config.getStringList("telegram.chat_ids");
            // notifiers.add(new TelegramNotifier(TG_TOKEN, TG_CHAT_IDS, config));
            // logger.info("[Telegram Notifier]: Enabled");
        }

        // webhook notifier
        if (config.getBoolean("webhook.enabled")) {
            // String TG_TOKEN = config.getString("telegram.token");
            // List<String> TG_CHAT_IDS = config.getStringList("telegram.chat_ids");
            // notifiers.add(new TelegramNotifier(TG_TOKEN, TG_CHAT_IDS, config));
            // logger.info("[Telegram Notifier]: Enabled");
        }

        // Register event listener
        getServer().getPluginManager().registerEvents(new EventListener(notifiers, config), this);

        // Setup update checker if enabled in config
        if (config.getBoolean("update_checker")) {
            new UpdateChecker(this, 77962).getVersion(version -> {
                if (!this.getDescription().getVersion().equalsIgnoreCase(version)) {
                    logger.info("Update detected! You are using version " + this.getDescription().getVersion()
                            + " and the latest version is " + version
                            + "! Download it at https://www.spigotmc.org/resources/yallnotified.77962/");
                }
            });
        }

        logger.info("Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Closing down!");
    }
}