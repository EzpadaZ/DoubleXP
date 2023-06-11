package dev.ezpadaz.doublexp.Listeners.Events;

import dev.ezpadaz.doublexp.DoubleXP;
import dev.ezpadaz.doublexp.Utils.MessageHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.util.ArrayList;

public class DoubleXPEvent implements Listener {
    public static boolean isEnabled = false;

    public static ArrayList<String> playerNames = new ArrayList<String>();

    @EventHandler
    public void playerExpChange(PlayerExpChangeEvent event) {

        int multiplier = DoubleXP.getInstance().config.getInt("multiplier");

        if (multiplier == 0) {
            // In case default value is 0 (null)
            multiplier = 2;
        }

        int obtainedExp = event.getAmount();
        int doubleExp = obtainedExp * multiplier;
        Player jugador = event.getPlayer();

        if (isEnabled) {
            if (DoubleXP.getInstance().config.getBoolean("log") && playerNames.contains(jugador.getName())) {
                MessageHelper.send(jugador, "You received: " + doubleExp + " (x" + multiplier + ") XP");
            }
            event.setAmount(doubleExp);
        } else {
            if (DoubleXP.getInstance().config.getBoolean("log") && playerNames.contains(jugador.getName()))
                MessageHelper.send(event.getPlayer(), "You received: " + obtainedExp + " XP");
            event.setAmount(obtainedExp);
        }
    }
}
