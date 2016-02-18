package acse.AutoCommands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class Config {
    public static String getString(String path) {
        return  AutoCommands.plugin.getConfig().getString(path);
    }

    public static List<String> getStrings(String path) {
        return AutoCommands.plugin.getConfig().getStringList(path);
    }

    public static ConfigurationSection getSection(String path) {
        return AutoCommands.plugin.getConfig().getConfigurationSection(path);
    }

    public static boolean isEnabled() {
        return AutoCommands.plugin.getConfig().getBoolean("enabled");
    }

    public static boolean usePlaceholderAPI() { return AutoCommands.plugin.getConfig().getBoolean("usePlaceholderAPI"); }

    public static ConfigurationSection getLists() {
        return getSection("lists");
    }

    public static ConfigurationSection getList(String list) {
        return getLists().getConfigurationSection(list);
    }
    
    public static int getInterval(String list) {
        return getList(list).getInt("interval");
    }

    public static String getType(String list) {
        return getList(list).getString("type");
    }

    public static int getMinimumPlayers(String list) {
        return getList(list).getInt("minimumPlayers");
    }

    public static String getPermission(String list) {
        return getList(list).getString("permission");
    }

    public static List<String> getListCommands(String list) {
        return getList(list).getStringList("commands");
    }

    public static String getListCommand(String list, int index) {
        return getListCommands(list).get(index);
    }

    public static String getLocale(String name) {
        return ChatColor.translateAlternateColorCodes('&', getSection("locale").getString(name));
    }

    public static boolean isListEnabled(String list) {
        return getList(list).getBoolean("enabled");
    }

    public static void setEnabled(String list, boolean value) {
        getList(list).set("enabled", value);
        AutoCommands.plugin.saveConfig();
    }

    public static boolean isDebug() {
        return AutoCommands.plugin.getConfig().getBoolean("debug");
    }
}
