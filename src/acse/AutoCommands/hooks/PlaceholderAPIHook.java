package acse.AutoCommands.hooks;

public final class PlaceholderAPIHook extends PluginHook {
    public static PlaceholderAPIHook instance;

    public PlaceholderAPIHook() {
        super("PlaceholderAPI");

        instance = this;
    }
}