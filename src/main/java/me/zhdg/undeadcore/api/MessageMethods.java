package me.zhdg.undeadcore.api;

import me.zhdg.undeadcore.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MessageMethods {

    public static void sendActionBar(final Player player, final String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static void sendActionBar(final Player player, final String message, int duration) {
        sendActionBar(player, message);
        if (duration >= 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, "");
                }
            }.runTaskLater(Main.INSTANCE, duration + 1);
        }
        while (duration > 60) {
            duration -= 60;
            final int sched = duration % 60;
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, message);
                }
            }.runTaskLater(Main.INSTANCE, sched);
        }
    }

    public static void sendActionBarToAllPlayers(final String message) {
        sendActionBarToAllPlayers(message, -1);
    }

    public static void sendActionBarToAllPlayers(final String message, final int duration) {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            sendActionBar(p, message, duration);
        }
    }
}