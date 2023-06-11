package dev.ezpadaz.doublexp.Commands.DoubleXP;

import dev.ezpadaz.doublexp.Commands.BaseCommand;
import dev.ezpadaz.doublexp.Listeners.Events.DoubleXPEvent;
import dev.ezpadaz.doublexp.Utils.ExperienceHelper;
import dev.ezpadaz.doublexp.Utils.MessageHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class XPCommand {
    public XPCommand() {
        new BaseCommand("dxp", 1, false) {

            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                String cmd = arguments[0];
                Player jugador = sender.getServer().getPlayer(sender.getName());

                switch (cmd) {
                    case "activate":
                        if (sender instanceof ConsoleCommandSender) {
                            // only allow enable by console.
                            if (DoubleXPEvent.isEnabled) {
                                MessageHelper.console("Double XP Event was already active.");
                            } else {
                                DoubleXPEvent.isEnabled = true;
                                MessageHelper.console("Double XP Event enabled!");
                            }
                        } else {
                            if (sender instanceof Player p) {
                                MessageHelper.send(p, "Este comando solo se puede ejecutar desde consola");
                            }
                        }
                        return true;
                    case "deactivate":
                        if (sender instanceof ConsoleCommandSender) {
                            // only allow disable by console.
                            if (DoubleXPEvent.isEnabled) {
                                DoubleXPEvent.isEnabled = false;
                                MessageHelper.console("Double XP Event disabled!");
                            }
                        } else {
                            if (sender instanceof Player p) {
                                MessageHelper.send(p, "Este comando solo se puede ejecutar desde consola");
                            }
                        }
                        return true;
                    case "status":
                        MessageHelper.send(sender, "Double XP Event: &c" + DoubleXPEvent.isEnabled);
                        return true;
                    case "get":
                        if (jugador != null) {
                            int totalExpPoints = ExperienceHelper.getPlayerExp(jugador);
                            DecimalFormat formatter = new DecimalFormat("#,###");
                            String formattedExp = formatter.format(totalExpPoints);
                            MessageHelper.send(sender, "Tienes: " + formattedExp + " EXP");
                        }
                        return true;
                    case "optin":
                        if (jugador != null) {
                            if (DoubleXPEvent.playerNames.contains(jugador.getName())) {
                                MessageHelper.send(jugador, "You are already receiving XP Updates");
                            } else {
                                DoubleXPEvent.playerNames.add(jugador.getName());
                                MessageHelper.send(jugador, "You have opted-in to receive XP Updates");
                            }
                        }
                        return true;
                    case "optout":
                        if (jugador != null) {
                            if (DoubleXPEvent.playerNames.contains(jugador.getName())) {
                                DoubleXPEvent.playerNames.remove(jugador.getName());
                                MessageHelper.send(jugador, "You have opted out of receiving XP Updates");
                            } else {
                                MessageHelper.send(jugador, "You can't opt-out if you are not receiving updates already.");
                            }
                        }
                        return true;
                    default:
                        return true;
                }
            }

            @Override
            public String getUsage() {
                return "/dxp {get | status}";
            }
        };
    }
}
