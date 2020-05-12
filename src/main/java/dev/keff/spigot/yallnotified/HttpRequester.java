package dev.keff.spigot.yallnotified;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.bukkit.Bukkit;

public class HttpRequester {
    String token;

    // TODO
    public void sendPost(String urlString, String data) throws Exception {
        Bukkit.getLogger().info("HttpRequester.sendPost(" + urlString + ");");

        try {
            final URL url = new URL(urlString);
            final URLConnection conn = url.openConnection();
            conn.setRequestProperty("method", "GET");
            conn.setRequestProperty("content-type", "text");

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
    
            writer.write(data);
            writer.flush();
            
            final InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    public void post(String urlString) {
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