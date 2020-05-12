package dev.keff.spigot.yallnotified.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import dev.keff.spigot.yallnotified.App;

public class IgnorePlayerCommand implements CommandExecutor, TabCompleter {
    FileConfiguration config;
    App main = App.getPlugin(App.class);

    public IgnorePlayerCommand(FileConfiguration config) {
        super();
        this.config = main.getConfig();
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Only let user run commands if they are OP'd or have permissions set
            if (player.isOp() || player.isPermissionSet("yn.commands")) {
                String subcommand = args[0];
                String playername = args[1];
                String notifier = args[2];

                if (subcommand == null) {
                    player.sendMessage("\u00a7c /yn <ignore|unignore> <player> [notifier]");
                    return false;
                }

                if (notifier == null) {
                    List<String> currentIgnoredPlayers = this.config.getStringList("ignored_players");
                    currentIgnoredPlayers.add(playername);
                    this.config.set("ignored_players", currentIgnoredPlayers);
                }

                if (notifier != null) {
                    List<String> currentIgnoredPlayers = this.config.getStringList(notifier + ".ignored_players");
                    currentIgnoredPlayers.add(playername);
                    this.config.set(notifier + ".ignored_players", currentIgnoredPlayers);
                }

                main.saveConfig();
            }
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd,
            @NotNull String alias, @NotNull String[] args) {

        if (sender instanceof Player && cmd.getName() == "yn") {
            List<String> newList = new ArrayList<String>();

            // Subcomand argument
            if (args.length == 0) {
                newList.add("ignore");
                newList.add("unignore");
            }

            // Player argument
            else if (args.length == 1) {
                // They started typing username
                if (args[0] != "") {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                            newList.add(player.getName());
                        }
                    }
                } else {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        newList.add(player.getName());
                    }
                }
            }

            // Noitifier argument
            else if (args.length == 2) {
                if (args[0] != "") {
                    for (String notif : App.NOTIFIERS) {
                        if (notif.toLowerCase().startsWith(args[0].toLowerCase())) {
                            newList.add(notif);
                        }
                    }
                } else {
                    for (String notif : App.NOTIFIERS) {
                        newList.add(notif);
                    }
                }
            }

            Collections.sort(newList);
            return newList;
        }
        return null;
    }
}