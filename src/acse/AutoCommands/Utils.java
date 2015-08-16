package acse.AutoCommands;

import net.milkbowl.vault.VaultEco;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Random;

public class Utils {
    public static Player randomPlayer;

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
                continue;
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
                continue;
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
    public static void sayPlayer(Player player, String perm, String msg) {
        if(player == null || !player.isOnline()) {
            return;
        }
        if(!perm.equalsIgnoreCase("none") && !player.hasPermission(perm)) {
            return;
        }

        msg = replacePlaceholders(msg, player);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static Player reselectRandomPlayer() {
        Collection<? extends Player> playersOnline = Bukkit.getServer().getOnlinePlayers();
        Random random = new Random();
        int size = playersOnline.size();
        if(size < 1) {
            return null;
        }
        int i = random.nextInt(playersOnline.size());
        randomPlayer = (Player) playersOnline.toArray()[i];
        return randomPlayer;
    }

    public static String replacePlaceholders(String str, Player player) {
        str = str.replaceAll("%player%", player.getName());
        str = str.replaceAll("%playername%", player.getDisplayName());
        str = str.replaceAll("%randomplayer%", randomPlayer.getName());
        str = str.replaceAll("%randomplayername", randomPlayer.getDisplayName());
        str = str.replaceAll("%online%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
        str = str.replaceAll("%maxplayers%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
        if(AutoCommands.economy != null) {
            OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(player.getName());
            str = str.replaceAll("%balance%", String.valueOf(AutoCommands.economy.getBalance(offlinePlayer)));
        }
        return str;
    }
}
