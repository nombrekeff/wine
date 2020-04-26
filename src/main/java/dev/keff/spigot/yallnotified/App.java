package dev.keff.spigot.yallnotified;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
    @Override
    public void onEnable() {
        FileConfiguration config = getConfig();
        Boolean TG_ENABLED = config.getBoolean("telegram.enabled");
        Logger logger = getLogger();
        Notifier notifier = new EmptyNotifier(config);

        // Save config
        config.options().copyDefaults(true);
        this.saveConfig();

        // Add telegram notifier if enabled
        if (TG_ENABLED == true) {
            String TG_TOKEN = config.getString("telegram.token");
            List<String> TG_CHAT_IDS = config.getStringList("telegram.chat_ids");
            notifier = new TelegramNotifier(TG_TOKEN, TG_CHAT_IDS, config);

            logger.info("[Telegram Notifier]: Enabled");
        }

        // Register event listener
        getServer().getPluginManager().registerEvents(new ConnectionListener(notifier, config), this);

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