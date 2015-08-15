package acse.AutoCommands;

public class CommandTask implements Runnable {
    private String list;
    public int index;

    public CommandTask(String list) {
        this.list = list;
        this.index = 0;
    }

    @Override
    public void run() {
        String command = Config.getListCommand(list, index);
        String permission = Config.getPermission(list);

        CommandParser.run(command, permission);

        index++;
        if(index == Config.getListCommands(list).size()) {
            index = 0;
        }
    }
}
