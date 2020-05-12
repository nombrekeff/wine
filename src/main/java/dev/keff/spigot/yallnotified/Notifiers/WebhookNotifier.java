package dev.keff.spigot.yallnotified.notifiers;

import java.sql.Timestamp;
import java.util.Date;

import com.google.gson.JsonObject;

import org.bukkit.configuration.file.FileConfiguration;
import dev.keff.spigot.yallnotified.HttpRequester;

public class WebhookNotifier extends Notifier {
    HttpRequester httpRequester = new HttpRequester();
    String endpoint;

    public WebhookNotifier(FileConfiguration config) throws Exception {
        super("webhook", config);
        this.endpoint = config.getString("webhook.endpoint");

        if (!config.isSet("webhook.endpoint")) {
            throw new Exception("[WebhookNotifier] Config 'webhook.endpoint' is required if enabled");
        }
    }

    @Override
    public void notify(String message) {
        try {
            JsonObject data = new JsonObject();
            data.addProperty("message", message);
            data.addProperty("date", java.time.LocalDateTime.now().toString());

            this.httpRequester.sendPost(this.endpoint, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}