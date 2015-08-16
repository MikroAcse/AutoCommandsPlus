package acse.AutoCommands;

import org.bukkit.Bukkit;

import java.util.Random;

public class CommandTask implements Runnable {
    public String list;
    public int index;

    public CommandTask(String list) {
        this.list = list;
        this.index = 0;
    }

    @Override
    public void run() {
        String permission = Config.getPermission(list);
        String type = Config.getType(list);
        int size = Config.getListCommands(list).size();
        int minimumPlayers = Config.getMinimumPlayers(list);
        int playersOnline = Bukkit.getServer().getOnlinePlayers().size();

        if (playersOnline < 1) {
            return;
        }
        if(minimumPlayers > 0 && minimumPlayers < playersOnline) {
            return;
        }

        if(type == "random") {
            Random random = new Random();
            index = random.nextInt(size);
        }
        String string = Config.getListCommand(list, index);

        CommandParser.parse(string, permission);

        if (type == "default") {
            index++;
            if(index >= size) {
                index = 0;
            }
        }
    }
}
