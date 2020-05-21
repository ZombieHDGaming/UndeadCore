package me.zhdg.undeadcore.commands;

import me.zhdg.undeadcore.utils.DataStorage;
import me.zhdg.undeadcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class MessageCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender user, Command cmd, String command, String[] args) {
        StringBuilder message2 = new StringBuilder();
        if (user instanceof Player) {
            Player sender = (Player) user;
            if (args.length == 0) {
                sender.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cUsage: /msg (player) <message>"));
                return false;
            }
            for (int i = 1; i < args.length; i++) {
                String arg = args[i] + " ";
                message2.append(arg);
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (args.length < 2) {
                sender.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cUsage: /msg (player) <message>"));
                return false;
            }
            if (target == null) {
                sender.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cYou need to specify an Online player"));
                sender.sendMessage(Utils.colorize("&cUsage: /msg (player) <message>"));
                return false;
            }
            if (target.isOnline()) {
                if ((!DataStorage.getToggle().contains(sender)) && (sender.hasPermission("uc.vanishedmessage"))) {
                    if (!DataStorage.getToggle().contains(target)) {
                        sender.sendMessage(Utils.colorize("&6&lTO: &r" + Utils.getVaultName(target) + " &r&6&l>&r " + message2));
                        target.sendMessage(Utils.colorize("&6&lFROM: &r" + Utils.getVaultName(sender) + " &r&6&l>&r " + message2));
                        for (Player online : DataStorage.getSpy()) {
                            online.sendMessage(Utils.colorize("&6&lSPY: &r" + Utils.getVaultName(sender) + " &r&7&l>&r " + Utils.getVaultName(target) + " &r&6&l:&r " + message2));
                        }
                        DataStorage.getPMs().remove(sender);
                        DataStorage.getPMs().remove(target);
                        DataStorage.getPMs().put(sender, target);
                        DataStorage.getPMs().put(target, sender);
                    } else {
                        sender.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cThis player has Messaging disabled."));
                        return false;
                    }
                } else {
                    sender.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cYou have Messaging disabled. Re-enable Messaging to Send a Message."));
                    return false;
                }
            }
        }
        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void messageLeave(PlayerQuitEvent event) {
        DataStorage.getSpy().remove(event.getPlayer());
        DataStorage.getPMs().remove(event.getPlayer());
    }
}
