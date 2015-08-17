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
        if(!Config.isListEnabled(list)) {
            return;
        }

        String permission = Config.getPermission(list);
        String type = Config.getType(list);
        int size = Config.getListCommands(list).size();
        int minimumPlayers = Config.getMinimumPlayers(list);
        int playersOnline = Bukkit.getServer().getOnlinePlayers().size();

        Utils.debug("size: " + size + "; playersOnline: " + playersOnline + "; minimumPlayers: " + minimumPlayers + "; type: " + type + "; perm: " + permission);

        if (playersOnline < 1) {
            return;
        }
        if(minimumPlayers > 0 && playersOnline < minimumPlayers) {
            return;
        }

        if(type.equalsIgnoreCase("random")) {
            Random random = new Random();
            index = random.nextInt(size);
        }
        String string = Config.getListCommand(list, index);

        CommandParser.parse(string, permission, list);

        if (type.equalsIgnoreCase("default")) {
            index++;
            if(index >= size) {
                index = 0;
            }
        }
    }
}
