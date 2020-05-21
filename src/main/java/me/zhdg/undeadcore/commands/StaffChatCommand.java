package me.zhdg.undeadcore.commands;

import me.zhdg.undeadcore.utils.DataStorage;
import me.zhdg.undeadcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.logging.Level;

public class StaffChatCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(arg).append(" ");
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            switch (args.length) {
                case 0:
                    if (!DataStorage.getChatting().contains(player)) {
                        DataStorage.getChatting().add(player);
                        player.sendMessage(Utils.colorize("&7&l[&9&lSC&7&l] &aJoined Chat Mode"));
                    } else {
                        DataStorage.getChatting().remove(player);
                        player.sendMessage(Utils.colorize("&7&l[&9&lSC&7&l] &cLeft Chat Mode"));
                    }
                    return true;
                default:
                    for (Player user : DataStorage.getStaff()) {
                        if (message.toString().contains(user.getDisplayName())) {
                            user.playSound(user.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                            message = new StringBuilder(message.toString().replace(user.getName(), Utils.colorize("&e@" + user.getName() + "&f")));
                        }
                    }
                    for (Player user : DataStorage.getStaff()) {
                        String full = Utils.colorize("&7&l[&9&lSC&7&l] &r" + Utils.getVaultName(player) + " &6&l>&r ") + message;
                        user.sendMessage(full);
                        Bukkit.getLogger().log(Level.INFO, full);
                    }
            }
        } else {
            switch (args.length) {
                case 0:
                    sender.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cUsage: /sc <message>"));
                    return true;
                default:
                    String full = Utils.colorize("&7&l[&9&lSC&7&l] &r&f&lCONSOLE&r &6&l> &r") + Utils.colorize(message.toString());
                    Bukkit.getLogger().log(Level.INFO, full);
                    for (Player user : DataStorage.getStaff()) {
                        user.sendMessage(full);
                    }
            }
        }
        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void chatSync(AsyncPlayerChatEvent event) {
        if (DataStorage.getChatting().contains(event.getPlayer())) {
            event.setCancelled(true);
            for (Player p : DataStorage.getStaff()) {
                p.sendMessage(Utils.colorize("&7&l[&9&lSC&7&l] &r" + Utils.getVaultName(event.getPlayer()) + " &6&l>&r " + event.getMessage()));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void staffJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("uc.staffchat")) {
            DataStorage.getStaff().add(event.getPlayer());
            for (Player user : DataStorage.getStaff()) {
                user.sendMessage(Utils.colorize("&7&l[&9&lSC&7&l] &6&l>&r " + Utils.getVaultName(event.getPlayer()) + " &7has connected."));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void staffLeave(PlayerQuitEvent event) {
        if (event.getPlayer().hasPermission("uc.staffchat")) {
            DataStorage.getStaff().remove(event.getPlayer());
            for (Player user : DataStorage.getStaff()) {
                user.sendMessage(Utils.colorize("&7&l[&9&lSC&7&l] &6&l>&r " + Utils.getVaultName(event.getPlayer()) + " &7has disconnected."));
            }
        }
    }
}
