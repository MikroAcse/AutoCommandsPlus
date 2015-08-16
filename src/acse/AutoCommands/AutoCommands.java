package acse.AutoCommands;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class AutoCommands extends JavaPlugin {
    public static AutoCommands plugin;
    public static Economy economy;

    @Override
    public void onEnable(){
        this.saveDefaultConfig();
        plugin = this;
        schedule();

        getLogger().log(Level.INFO, "Plugins has been enabled.");

        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        } else {
            economy = null;
        }
    }

    @Override
    public void onDisable(){
        unschedule();
        plugin = null;
        getLogger().log(Level.INFO, "Plugin has been disabled.");
    }

    public void unschedule() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    public void schedule() {
        unschedule();

        ConfigurationSection listsSections = Config.getLists();
        Set<String> lists = listsSections.getKeys(false);

        for (String list : lists) {
            if (list.startsWith("_")) {
                continue;
            }
            if(Config.getListCommands(list).size() < 1) {
                continue;
            }
            int interval = Config.getInterval(list);
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new CommandTask(list), 20 * interval, 20 * interval);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if(cmd.equalsIgnoreCase("tellp")) {
            if(player != null && !player.hasPermission("autocommands.admin") && !player.isOp()) {
                sender.sendMessage(Config.getLocale("noPermission"));
                return true;
            }
            if(args.length < 2) {
                sender.sendMessage(Config.getLocale("wrongTellp"));
                return true;
            }
            Player playerArg = Bukkit.getServer().getPlayerExact(args[0]);
            String message = "";
            int length = args.length;
            for(int i = 1; i < length; i++) {
                message = message + args[i];
            }

            if(playerArg == null) {
                sender.sendMessage(Config.getLocale("wrongTellp"));
                return true;
            }

            message = Utils.replacePlaceholders(message, playerArg);
            playerArg.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        } else if(args[0].equalsIgnoreCase("reload")) {
            if(player != null && !player.hasPermission("autocommands.admin")) {
                sender.sendMessage(Config.getLocale("noPermission"));
                return true;
            }
            this.reloadConfig();
            schedule();
            sender.sendMessage(Config.getLocale("pluginReloaded"));
            return true;
        }
        return false;
    }
}