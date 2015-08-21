package acse.AutoCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.text.CollationElementIterator;
import java.util.Collection;

public class CommandParser {
    public static void parse(String str, String perm, String list) {
        String[] strings = str.split(">;");
        int length = strings.length;

        for (int i = 0; i < length; i++) {
            String string = strings[i];

            Utils.debug("splitted string " + (i + 1) + ": " + string);

            run(string, perm, list);
        }
    }

    public static void run(String str, String perm, String list) {
        int strLength = str.length();
        boolean sRandomPlayer = false;
        boolean sAllPlayers = false;
        boolean sSudoPlayer = false;
        boolean sExcludePlayer = false;

        for (int i = 0; i < strLength; i++) {
            char chr = str.charAt(i);
            if (chr == '@') {
                sAllPlayers = true;
            } else if (chr == '$') {
                Utils.reselectRandomPlayer(list);
            } else if (chr == '!') {
                sRandomPlayer = true;
            } else if (chr == '-') {
                sExcludePlayer = true;
            } else if (chr == '#') {
                sSudoPlayer = true;
            } else if (chr == '>') {
                str = str.substring(i + 1);
                break;
            } else {
                sRandomPlayer = false;
                sAllPlayers = false;
                sSudoPlayer = false;
                sExcludePlayer = false;
                break;
            }
        }

        if (Utils.getRandomPlayer(list) == null || !Utils.getRandomPlayer(list).isOnline()) {
            Utils.reselectRandomPlayer(list);
        }

        if (str.startsWith("/")) {
            Utils.debug("It's a command! random: " + sRandomPlayer + "; exclude: " + sExcludePlayer + "; sudo: " + sSudoPlayer + "; all: " + sAllPlayers);
            Server server = Bukkit.getServer();
            ConsoleCommandSender console = server.getConsoleSender();
            String cmd = str.substring(1);

            if(sAllPlayers) {
                Utils.debug("Using all players! " + cmd);
                Collection<? extends Player> playersOnline = server.getOnlinePlayers();

                for (Player player : playersOnline) {
                    if(sExcludePlayer && player == Utils.getRandomPlayer(list)) {
                        continue;
                    }

                    String pcmd = Utils.replacePlaceholders(cmd, player, list);
                    Utils.debug("Ran to " + player.getName() + ": " + pcmd);
                    if (sSudoPlayer) {
                        server.dispatchCommand(player, ChatColor.translateAlternateColorCodes('&', pcmd));
                    } else {
                        server.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', pcmd));
                    }
                }
            } else if (sRandomPlayer) {
                cmd = Utils.replacePlaceholders(cmd, Utils.getRandomPlayer(list), list);
                if (sSudoPlayer) {
                    server.dispatchCommand(Utils.getRandomPlayer(list), ChatColor.translateAlternateColorCodes('&', cmd));
                } else {
                    server.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', cmd));
                }
            } else {
                cmd = Utils.replacePlaceholders(cmd, Utils.getRandomPlayer(list), list);
                server.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', cmd));
            }
        } else {
            Utils.debug("It's a message! random: " + sRandomPlayer + "; exclude: " + sExcludePlayer);
            if(sRandomPlayer) {
                Utils.sayPlayer(Utils.getRandomPlayer(list), str, perm, list);
            } else if (sExcludePlayer) {
                Utils.say(str, perm, list, Utils.getRandomPlayer(list));
            } else {
                Utils.say(str, perm, list);
            }
        }
    }
}
