package dev.keff.spigot.yallnotified;

import java.util.List;

public class TelegramNotifier extends Notifier {
    TelegramRequester bot;
    List<String> chatIds;

    public TelegramNotifier(String token, List<String> chat_ids) {
        super();
        this.bot = new TelegramRequester(token);
        this.chatIds = chat_ids;
    }

    @Override
    void notify(String message) {
        for (int i = 0; i < this.chatIds.size(); i++) {
            String id = this.chatIds.get(i);
            this.bot.sendMessage(id, message);
        }
    }
}