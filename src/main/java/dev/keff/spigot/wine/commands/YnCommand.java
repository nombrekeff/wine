package dev.keff.spigot.wine.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import dev.keff.spigot.wine.App;

// TODO: refactor the shit out of this, research a better way of adding subcommands
public class YnCommand implements CommandExecutor, TabCompleter {
    static List<String> SUBCOMMANDS = Arrays.asList("ignore", "unignore", "enable", "disable");
    FileConfiguration config;
    App main = App.getPlugin(App.class);

    public YnCommand(FileConfiguration config) {
        super();
        this.config = main.getConfig();
    }

    private void addPlayerToList(Player player, String listConfigPath, String message) {
        String playerName = player.getName();
        List<String> currentIgnoredPlayers = this.config.getStringList(listConfigPath);
        if (!currentIgnoredPlayers.contains(playerName)) {
            currentIgnoredPlayers.add(playerName);
            this.config.set(listConfigPath, currentIgnoredPlayers);
            player.sendMessage(ChatColor.GREEN + message);
        } else {
            player.sendMessage(ChatColor.YELLOW + "'" + playerName + "' is already ignored.");
        }
    }

    private void enableNotifier(Player player, String notifier) {
        String path = notifier + ".enabled";
        if (!config.getBoolean(path)) {
            this.config.set(path, true);
            player.sendMessage(ChatColor.GREEN + "Enabled notifier " + notifier);
        } else {
            player.sendMessage(ChatColor.YELLOW + "'" + notifier + "' is already enabled.");
        }
    }

    private void disableNotifier(Player player, String notifier) {
        String path = notifier + ".enabled";
        if (config.getBoolean(path)) {
            this.config.set(path, false);
            player.sendMessage(ChatColor.GREEN + "Disabled notifier " + notifier);
        } else {
            player.sendMessage(ChatColor.YELLOW + "'" + notifier + "' is already disabled.");
        }
    }

    private void removePlayerFromList(Player player, String listConfigPath, String message) {
        String playerName = player.getName();
        List<String> currentIgnoredPlayers = this.config.getStringList(listConfigPath);
        if (currentIgnoredPlayers.contains(playerName)) {
            currentIgnoredPlayers.remove(playerName);
            this.config.set(listConfigPath, currentIgnoredPlayers);
            player.sendMessage(ChatColor.GREEN + message);
        } else {
            player.sendMessage(ChatColor.YELLOW + "'" + playerName + "' is already unignored.");
        }
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        // Only let user run commands if they are OP'd or have permissions set
        if (player.isOp() || player.isPermissionSet("yn.commands")) {
            String subcommand = args[0];

            // Errors
            if (subcommand == null) {
                player.sendMessage(ChatColor.RED + "'subcommand' is required");
                return false;
            }

            if (subcommand.equals("ignore") || subcommand.equals("unignore")) {
                String playername = args[1];
                String notifier = args.length == 3 ? args[2] : null;

                if (!SUBCOMMANDS.contains(subcommand)) {
                    player.sendMessage(ChatColor.RED + "'subcommand' may only be one of: " + SUBCOMMANDS);
                    return true;
                }
                if (notifier != null && !App.NOTIFIERS.contains(notifier)) {
                    player.sendMessage(ChatColor.RED + "'notifier' may only be one of: " + App.NOTIFIERS);
                    return true;
                }

                if (subcommand.equals("ignore")) {
                    if (notifier == null) {
                        this.addPlayerToList(player, "ignored_players", "Ignoring '" + playername + "' globaly");
                    } else {
                        this.addPlayerToList(player, notifier + ".ignored_players",
                                "Ignoring '" + playername + "' for notifier '" + notifier + "'");

                    }
                } else if (subcommand.equals("unignore")) {
                    if (notifier == null) {
                        this.removePlayerFromList(player, "ignored_players", "Unignoring " + playername + " globaly");
                    } else {
                        this.removePlayerFromList(player, notifier + ".ignored_players",
                                "Unignoring " + playername + " for notifier " + notifier);
                    }
                }
            }

            else if (subcommand.equals("enable") || subcommand.equals("disable")) {
                String notifier = args[1];
                if (notifier != null && !App.NOTIFIERS.contains(notifier)) {
                    player.sendMessage(ChatColor.RED + "'notifier' may only be one of: " + App.NOTIFIERS);
                    return true;
                }

                if (subcommand.equals("enable")) {
                    this.enableNotifier(player, notifier);
                } else if (subcommand.equals("disable")) {
                    this.disableNotifier(player, notifier);
                }
            }
            main.saveConfig();
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (cmd.getName().toLowerCase().equals("yn")) {
            List<String> newList = new ArrayList<String>();
            if (args.length == 1) {
                if (!args[0].equals("")) {
                    for (String subcmd : SUBCOMMANDS) {
                        if (subcmd.toLowerCase().startsWith(args[0].toLowerCase())) {
                            newList.add(subcmd);
                        }
                    }
                } else {
                    newList.addAll(SUBCOMMANDS);
                }
            }

            // Ignore/unignore player autocomplete
            if (args[0].equals("ignore") || args[0].equals("unignore")) {
                if (args.length == 2) {
                    // They started typing username
                    if (!args[1].equals("")) {
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
                else if (args.length == 3) {
                    if (!args[2].equals("")) {
                        for (String notif : App.NOTIFIERS) {
                            if (notif.toLowerCase().startsWith(args[2].toLowerCase())) {
                                newList.add(notif);
                            }
                        }
                    } else {
                        for (String notif : App.NOTIFIERS) {
                            newList.add(notif);
                        }
                    }
                }
            }

            // Enable/disable notifier autocomplete
            else if (args[0].equals("enable") || args[0].equals("disable")) {
                if (args.length == 2) {
                    if (!args[1].equals("")) {
                        for (String notif : App.NOTIFIERS) {
                            if (notif.toLowerCase().startsWith(args[1].toLowerCase())) {
                                newList.add(notif);
                            }
                        }
                    } else {
                        newList.addAll(App.NOTIFIERS);
                    }
                }
            }

            Collections.sort(newList);
            return newList;
        }
        return null;
    }
}