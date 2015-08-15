package acse.AutoCommands;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;

public class CommandParser {
    public static void run(String str, String perm) {
        if (str.startsWith('/')) {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Server server = Bukkit.getServer();
            String cmd = str.substring(1);

            server.dispatchCommand(console, cmd);
        } else {
            Utils.say(str, perm);
        }
    }
}
