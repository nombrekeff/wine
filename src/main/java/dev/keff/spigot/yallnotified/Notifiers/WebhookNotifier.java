package dev.keff.spigot.yallnotified.notifiers;

import org.bukkit.configuration.file.FileConfiguration;
import dev.keff.spigot.yallnotified.HttpRequester;

public class WebhookNotifier extends Notifier {
    HttpRequester httpRequester = new HttpRequester();
    String endpoint;

    public WebhookNotifier(FileConfiguration config) {
        super("webhook", config);
        this.endpoint = config.getString("webhook.endpoint");
    }

    @Override
    public void notify(String message) {
        try {
            this.httpRequester.sendPost(this.endpoint, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}