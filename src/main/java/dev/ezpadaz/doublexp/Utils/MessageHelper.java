package dev.ezpadaz.doublexp.Utils;

import dev.ezpadaz.doublexp.DoubleXP;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageHelper {
    public static void send(CommandSender sender, String message) {
        send(sender, message, "&6[&5DXP&6]&f ");
    }

    public static void send(CommandSender sender, String message, String prefix) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }

    public static void consoleDebug(String message) {
        boolean isDebugEnabled = DoubleXP.getInstance().config.getBoolean("debug");

        if (isDebugEnabled) console(message);
    }

    public static void console(String message) {
        send(DoubleXP.getInstance().getServer().getConsoleSender(), "&6[&5DXP&6]&f " + message, "&a");
    }
}
