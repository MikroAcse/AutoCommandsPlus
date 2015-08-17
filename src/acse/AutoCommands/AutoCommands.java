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

        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        } else {
            economy = null;
        }

        schedule();

        getLogger().log(Level.INFO, "Plugin has been enabled.");
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
        Utils.resetRandomPlayers();

        if(!Config.isEnabled()) {
            return;
        }

        ConfigurationSection listsSections = Config.getLists();
        Set<String> lists = listsSections.getKeys(false);

        for (String list : lists) {
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
            String message = args[1];

            if(args.length > 2) {
                int length = args.length;
                for(int i = 2; i < length; i++) {
                    message = message + " " + args[i];
                }
            }

            if(playerArg == null) {
                sender.sendMessage(Config.getLocale("wrongTellp"));
                return true;
            }

            message = Utils.replacePlaceholders(message, playerArg, null);
            playerArg.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        } else if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(Config.getLocale("help"));
            return true;
        } else if(args[0].equalsIgnoreCase("reload")) {
            if(player != null && !player.hasPermission("autocommands.admin") && !player.isOp()) {
                sender.sendMessage(Config.getLocale("noPermission"));
                return true;
            }
            this.reloadConfig();
            schedule();
            sender.sendMessage(Config.getLocale("pluginReloaded"));
            return true;
        } else if(args[0].equalsIgnoreCase("set")) {
            if(player != null && !player.hasPermission("autocommands.admin") && !player.isOp()) {
                sender.sendMessage(Config.getLocale("noPermission"));
                return true;
            }
            if (args.length != 3) {
                sender.sendMessage(Config.getLocale("wrongEnabled"));
                return true;
            }

            ConfigurationSection list = Config.getList(args[1]);
            boolean value = args[2].equalsIgnoreCase("enabled");

            if(list == null) {
                sender.sendMessage(Config.getLocale("wrongEnabled"));
            }

            Config.setEnabled(args[1], value);
            String msg = Config.getLocale("listEnabledChanged");
            msg = msg.replaceAll("%list%", args[1]);
            if(value) {
                msg = msg.replaceAll("%enabled%", Config.getLocale("enabledTrue"));
            } else {
                msg = msg.replaceAll("%enabled%", Config.getLocale("enabledFalse"));
            }
            sender.sendMessage(msg);
            return true;
        } else if(args[0].equalsIgnoreCase("version")) {
            if(player != null && !player.hasPermission("autocommands.admin") && !player.isOp()) {
                sender.sendMessage(Config.getLocale("noPermission"));
                return true;
            }

            sender.sendMessage(Config.getLocale("version") + this.getDescription().getVersion());

            return true;
        }
        return false;
    }
}