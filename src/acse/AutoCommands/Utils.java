package acse.AutoCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Utils {

    public static void say(String msg, String perm) {
        Collection<? extends Player> playersOnline = Bukkit.getServer().getOnlinePlayers();

        for(Player player : playersOnline) {
            sayPlayer(player, perm, msg);
        }
    }
    public static void say(String msg, String perm, String exclude) {
        Collection<? extends Player> playersOnline = Bukkit.getServer().getOnlinePlayers();

        for(Player player : playersOnline) {
            if(player.getName().equalsIgnoreCase(exclude)) {
                continue;;
            }
            sayPlayer(player, perm, msg);
        }
    }
    public static void say(String msg, String perm, String[] exclude) {
        Collection<? extends Player> playersOnline = Bukkit.getServer().getOnlinePlayers();

        for(Player player : playersOnline) {
            for (String excludePlayer : exclude) {
                if(player.getName().equalsIgnoreCase(excludePlayer)) {
                    continue;
                }
            }
            sayPlayer(player, perm, msg);
        }
    }
    public static void say(String msg, String perm, Player exclude) {
        Collection<? extends Player> playersOnline = Bukkit.getServer().getOnlinePlayers();

        for(Player player : playersOnline) {
            if(player == exclude) {
                continue;;
            }
            sayPlayer(player, perm, msg);
        }
    }
    public static void say(String msg, String perm, Player[] exclude) {
        Collection<? extends Player> playersOnline = Bukkit.getServer().getOnlinePlayers();

        for(Player player : playersOnline) {
            for (Player excludePlayer : exclude) {
                if(player == excludePlayer) {
                    continue;
                }
            }
            sayPlayer(player, perm, msg);
        }
    }
    private static void sayPlayer(Player player, String perm, String msg) {
        if(player == null || !player.isOnline()) {
            return;
        }
        if(!perm.equalsIgnoreCase('none') && !player.hasPermission(perm)) {
            return;
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
