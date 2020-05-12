package dev.keff.spigot.yallnotified;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.bukkit.Bukkit;

public class HttpRequester {
    String token;
    HttpClient httpclient = HttpClients.createDefault();

    // TODO
    public void sendPost(String url, String message) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("message", message));
        httppost.setEntity(new UrlEncodedFormEntity(nvps));

        CloseableHttpResponse response = httpclient.execute(httppost);

        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    // do something useful
                } finally {
                    instream.close();
                }
            }
        } finally {
            response.close();
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