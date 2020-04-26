package dev.keff.spigot.yallnotified;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import dev.keff.spigot.yallnotified.commands.NetherCoordsCommand;

public class App extends JavaPlugin {
    @Override
    public void onEnable() {
        Notifier notifier = new EmptyNotifier();
        FileConfiguration config = getConfig();
        Boolean TG_ENABLED = config.getBoolean("telegram.enabled");

        config.options().copyDefaults(true);
        this.saveConfig();

        if (TG_ENABLED == true) {
            String TG_TOKEN = config.getString("telegram.token");
            List<String> TG_CHAT_IDS = config.getStringList("telegram.chat_ids");
            notifier = new TelegramNotifier(TG_TOKEN, TG_CHAT_IDS);
            getLogger().info("[Telegram Notifier]: Enabled");
        }

        getLogger().info("Enabled!");

        // Register event listener
        getServer().getPluginManager().registerEvents(new ConnectionListener(notifier, config), this);
        this.getCommand("ncoords").setExecutor(new NetherCoordsCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("Closing down!");
    }
}