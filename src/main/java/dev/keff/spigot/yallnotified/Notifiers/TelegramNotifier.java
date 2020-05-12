package dev.keff.spigot.yallnotified.notifiers;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import dev.keff.spigot.yallnotified.HttpRequester;

public class TelegramNotifier extends Notifier {
    HttpRequester httpRequester = new HttpRequester();
    String token;
    List<String> chatIds;

    public TelegramNotifier(FileConfiguration config) throws Exception {
        super("telegram", config);
        if (!config.isSet("telegram.token")) {
            throw new Exception("[TelegramNotifier] Config 'telegram.token' is required if enabled");
        }

        this.chatIds = config.getStringList("telegram.chat_ids");
        this.token = config.getString("telegram.token");
    }

    public String createUrlForChat(String chatId, String message) {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=markdown";
        return String.format(urlString, this.token, chatId, message);
    }

    @Override
    public void notify(String message) {
        this.logger.info(Color.GREEN + " Telegram Notify");
        for (int i = 0; i < this.chatIds.size(); i++) {
            String chatId = this.chatIds.get(i);
            this.logger.info(Color.GREEN + " Telegram Notify  " + chatId);
            String url = this.createUrlForChat(chatId, message);
            this.httpRequester.post(url);
        }
    }
}