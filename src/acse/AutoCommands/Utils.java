package acse.AutoCommands;

import acse.AutoCommands.hooks.PlaceholderAPIHook;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class Utils {
    private static HashMap<String, Player> randomPlayers;

    public static void say(String msg, String perm, String list) {
        Collection<? extends Player> playersOnline = Bukkit.getServer().getOnlinePlayers();

        for(Player player : playersOnline) {
            sayPlayer(player, msg, perm, list);
        }
    }
    public static void say(String msg, String perm, String list, Player exclude) {
        Collection<? extends Player> playersOnline = Bukkit.getServer().getOnlinePlayers();

        for(Player player : playersOnline) {
            if(player == exclude) {
                continue;
            }
            sayPlayer(player, msg, perm, list);
        }
    }
    public static void sayPlayer(Player player, String msg, String perm, String list) {
        if(player == null || !player.isOnline()) {
            return;
        }
        if(!perm.equalsIgnoreCase("none") && !player.hasPermission(perm) && !player.isOp()) {
            return;
        }

        msg = replacePlaceholders(msg, player, list);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static Player reselectRandomPlayer(String list) {
        Collection<? extends Player> playersOnline = Bukkit.getServer().getOnlinePlayers();
        Random random = new Random();
        int size = playersOnline.size();
        if(size < 1) {
            return null;
        }
        int i = random.nextInt(playersOnline.size());
        Player randomPlayer = (Player) playersOnline.toArray()[i];
        randomPlayers.put(list, randomPlayer);
        return randomPlayer;
    }

    public static Player getRandomPlayer(String list) {
        return randomPlayers.get(list);
    }

    public static void resetRandomPlayers() {
        if(randomPlayers == null) {
            randomPlayers = new HashMap<>();
        }
        randomPlayers.clear();
    }

    public static void debug(String msg) {
        if(Config.isDebug()) {
            Bukkit.getServer().getConsoleSender().sendMessage(msg);
        }
    }

    public static String replacePlaceholders(String str, Player player, String list) {
        if (list != null) {
            Player randomPlayer = getRandomPlayer(list);
            str = str.replaceAll("%randomplayer%", randomPlayer.getName());
            str = str.replaceAll("%randomplayername%", randomPlayer.getDisplayName());
        }

        if(Config.usePlaceholderAPI() && PlaceholderAPIHook.instance.isEnabled()) {
            str = PlaceholderAPI.setPlaceholders(player, str);
            return str;
        }

        str = str.replaceAll("%player%", player.getName());
        str = str.replaceAll("%playername%", player.getDisplayName());

        str = str.replaceAll("%online%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
        str = str.replaceAll("%maxplayers%", String.valueOf(Bukkit.getServer().getMaxPlayers()));
        if(AutoCommands.economy != null) {
            OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(player.getName());
            str = str.replaceAll("%balance%", String.valueOf(AutoCommands.economy.getBalance(offlinePlayer)));
        }
        return str;
    }
}
