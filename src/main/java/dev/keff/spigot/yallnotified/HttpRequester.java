package dev.keff.spigot.yallnotified;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequester {
    String token;
    private final OkHttpClient httpClient = new OkHttpClient();

    public void sendPost(String url, RequestBody formBody) throws Exception {
        // form parameters
        // RequestBody formBody = new FormBody.Builder().add("username",
        // "abc").add("password", "123")
        // .add("custom", "secret").build();

        Request request = new Request.Builder().url(url).post(formBody).build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

            // Get response body
            System.out.println(response.body().string());
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