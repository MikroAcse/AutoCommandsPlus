package acse.AutoCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.text.CollationElementIterator;
import java.util.Collection;

public class CommandParser {
    public static void parse(String str, String perm) {
        String[] strings = str.split(">;");
        int length = strings.length;

        for (int i = 0; i < length; i++) {
            String string = strings[i];

            run(string, perm);
        }
    }

    public static void run(String str, String perm) {
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
                Utils.reselectRandomPlayer();
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

        if (Utils.randomPlayer == null || !Utils.randomPlayer.isOnline()) {
            Utils.reselectRandomPlayer();
        }

        if (str.startsWith("/")) {
            Server server = Bukkit.getServer();
            ConsoleCommandSender console = server.getConsoleSender();
            String cmd = str.substring(1);

            if(sAllPlayers) {
                Collection<? extends Player> playersOnline = server.getOnlinePlayers();

                for (Player player : playersOnline) {
                    if(sExcludePlayer && player == Utils.randomPlayer) {
                        continue;
                    }

                    cmd = Utils.replacePlaceholders(cmd, player);
                    if (sSudoPlayer) {
                        server.dispatchCommand(player, ChatColor.translateAlternateColorCodes('&', cmd));
                    } else {
                        server.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', cmd));
                    }
                }
            } else if (sRandomPlayer) {
                cmd = Utils.replacePlaceholders(cmd, Utils.randomPlayer);
                if (sSudoPlayer) {
                    server.dispatchCommand(Utils.randomPlayer, ChatColor.translateAlternateColorCodes('&', cmd));
                } else {
                    server.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', cmd));
                }
            } else {
                cmd = Utils.replacePlaceholders(cmd, Utils.randomPlayer);
                server.dispatchCommand(console, ChatColor.translateAlternateColorCodes('&', cmd));
            }
        } else {
            if(sRandomPlayer) {
                Utils.sayPlayer(Utils.randomPlayer, str, perm);
            } else if (sExcludePlayer) {
                Utils.say(str, perm, Utils.randomPlayer);
            }
            Utils.say(str, perm);
        }
    }
}
