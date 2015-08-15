package acse.AutoCommands;

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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Level;

public class AutoCommands extends JavaPlugin {
    public static AutoCommands plugin;

    @Override
    public void onEnable(){
        this.saveDefaultConfig();

        plugin = this;

        getLogger().log(Level.INFO, "Plugins has been enabled.");
    }

    @Override
    public void onDisable(){
        getLogger().log(Level.INFO, "Plugin has been disabled.");
    }

    public void unschedule() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    public void schedule() {
        unschedule();


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        String cmd = command.getName();
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if(args[0].equalsIgnoreCase('reload')) {
            if(player != null && !player.hasPermission("autocommands.admin")) {
                sender.sendMessage(Config.getLocale("noPerm"));
                return true;
            }
            this.reloadConfig();
            sender.sendMessage("Plugin has been reloaded.");
            return true;
        }
        return false;
    }
}