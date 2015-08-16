package acse.AutoCommands;

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
        return getSection("locale").getString(name);
    }
}
