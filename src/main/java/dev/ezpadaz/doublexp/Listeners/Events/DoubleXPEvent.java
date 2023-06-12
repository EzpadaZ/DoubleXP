package dev.ezpadaz.doublexp.Listeners.Events;

import dev.ezpadaz.doublexp.DoubleXP;
import dev.ezpadaz.doublexp.Utils.MessageHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DoubleXPEvent implements Listener {
    public static boolean isEnabled = false;

    public static ArrayList<String> optedInPlayers = new ArrayList<String>();
    public static ArrayList<String> bannedPlayers = new ArrayList<>();

    public static HashMap<String, Integer> doubleXpDeathCount = new HashMap<>();

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
            if (bannedPlayers.contains(jugador.getName())) {
                if (DoubleXP.getInstance().config.getBoolean("log") && optedInPlayers.contains(jugador.getName())) {
                    MessageHelper.send(jugador, "You received: " + doubleExp +" XP (Reduced)");
                }
                event.setAmount(obtainedExp);
            } else {
                if (DoubleXP.getInstance().config.getBoolean("log") && optedInPlayers.contains(jugador.getName())) {
                    MessageHelper.send(jugador, "You received: " + doubleExp + " (x" + multiplier + ") XP");
                }
                event.setAmount(doubleExp);
            }
        } else {
            if (DoubleXP.getInstance().config.getBoolean("log") && optedInPlayers.contains(jugador.getName()))
                MessageHelper.send(event.getPlayer(), "You received: " + obtainedExp + " XP");
            event.setAmount(obtainedExp);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (isEnabled) {
            int deathCooldown = DoubleXP.getInstance().config.getInt("death-cooldown");
            // if double XP is enabled we must cool down the player if he dies more than 3 times when the event is enabled.
            Player jugador = event.getEntity();
            Integer deathCount = doubleXpDeathCount.get(jugador.getName());

            if(deathCount == null){
                deathCount = 1;
            }

            doubleXpDeathCount.put(jugador.getName(), deathCount + 1);

            if (deathCount >= deathCooldown) {
                DoubleXPEvent.bannedPlayers.add(jugador.getName());
                MessageHelper.send(jugador, "You died too much on 2XP Day, You are no longer participating.");
            }
        }
    }
}
