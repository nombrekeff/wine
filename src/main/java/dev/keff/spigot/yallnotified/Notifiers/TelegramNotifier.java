package dev.keff.spigot.yallnotified.Notifiers;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import dev.keff.spigot.yallnotified.Notifier;
import dev.keff.spigot.yallnotified.TelegramRequester;

public class TelegramNotifier extends Notifier {
    TelegramRequester bot;
    List<String> chatIds;

    public TelegramNotifier(String token, List<String> chat_ids, FileConfiguration config) {
        super("telegram", config);
        this.bot = new TelegramRequester(token);
        this.chatIds = chat_ids;
    }

    @Override
    protected void notify(String message) {
        for (int i = 0; i < this.chatIds.size(); i++) {
            String id = this.chatIds.get(i);
            this.bot.sendMessage(id, message);
        }
    }
}