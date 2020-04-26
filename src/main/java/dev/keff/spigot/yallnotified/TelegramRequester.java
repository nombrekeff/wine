package dev.keff.spigot.yallnotified;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;

public class TelegramRequester {
    String token;

    TelegramRequester(final String token) {
        this.token = token;
    }

    public void sendMessage(String chatId, String text) {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=markdown";

        // Add Telegram token (given Token is fake)
        final String apiToken = this.token;
        urlString = String.format(urlString, apiToken, chatId, text);

        Bukkit.getLogger().info("URL: " + urlString);

        try {
            final URL url = new URL(urlString);
            final URLConnection conn = url.openConnection();
            final InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}