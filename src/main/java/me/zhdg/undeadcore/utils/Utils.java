package me.zhdg.undeadcore.utils;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Utils {

    private static Chat CHAT;

    public static boolean setupChat() {
        RegisteredServiceProvider<Chat> service = Bukkit.getServicesManager().getRegistration(Chat.class);
        if (service != null) {
            CHAT = service.getProvider();
        }

        return CHAT != null;
    }

    public synchronized static String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public synchronized static String getVaultName(Player p) {
        return colorize(CHAT.getPlayerPrefix(p) + p.getDisplayName() + CHAT.getPlayerSuffix(p));
    }

}
