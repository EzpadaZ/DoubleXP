package dev.ezpadaz.doublexp.Listeners.Events;

import dev.ezpadaz.doublexp.DoubleXP;
import org.bukkit.Bukkit;

public class ListenerBootloader {

    public ListenerBootloader() {
        Bukkit.getPluginManager().registerEvents(new DoubleXPEvent(), DoubleXP.getInstance());
    }
}
