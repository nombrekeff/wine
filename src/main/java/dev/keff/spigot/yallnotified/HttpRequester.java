package dev.keff.spigot.yallnotified;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonObject;

import org.bukkit.Bukkit;

public class HttpRequester {
    String token;

    // TODO
    public void sendPost(String urlString, JsonObject data) throws Exception {
        Bukkit.getLogger().info("HttpRequester.sendPost(" + urlString + ");");
        try {
            final URL url = new URL(urlString);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("method", "POST");
            conn.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            writer.write(data.toString());
            writer.flush();

            final InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    public void fetch(String urlString) {
        Bukkit.getLogger().info("HttpRequester.post(" + urlString + ");");

        try {
            final URL url = new URL(urlString);
            final URLConnection conn = url.openConnection();
            conn.setRequestProperty("method", "GET");

            final InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}