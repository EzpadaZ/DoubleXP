package dev.ezpadaz.doublexp;

import dev.ezpadaz.doublexp.Commands.CommandBootloader;
import dev.ezpadaz.doublexp.Listeners.Events.ListenerBootloader;
import dev.ezpadaz.doublexp.Utils.MessageHelper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class DoubleXP extends JavaPlugin {
    private static DoubleXP instance;
    public FileConfiguration config = getConfig();

    public static DoubleXP getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        instance = this;
        new CommandBootloader();
        new ListenerBootloader();
        MessageHelper.console("&6Plugin status: &aActive");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        MessageHelper.console("&6DXP: &cDisabled correctly.");
    }
}
