package me.zhdg.undeadcore.commands;

import me.zhdg.undeadcore.utils.DataStorage;
import me.zhdg.undeadcore.utils.Utils;
import me.zhdg.undeadcore.api.MessageMethods;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class MaintenanceCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                sender.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cUsage: /maintenance <&aon&c/&4off&c>"));
                return true;
            } else if (args[0].equals("on")) {
                if (!DataStorage.getMaintenance()) {
                    DataStorage.setMaintenance(true);
                    MessageMethods.sendActionBar(player, Utils.colorize("&e&l[&f&l!&e&l]&r &aMaintenance Mode Enabled. Kicking Players..."));
                    for (Player online : DataStorage.getOnline()) {
                        if (!online.hasPermission("uc.entermaintenance")) {
                            online.kickPlayer(Utils.colorize("&7[&e&lMAINTENANCE&7]\n&cThe Server is Entering Maintenance."));
                        }
                    }
                    MessageMethods.sendActionBar(player, Utils.colorize("&e&l[&f&l!&e&l]&r &6Server Cleared. Maintenance: &a&lENABLED"));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                } else {
                    player.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cMaintenance Mode is already enabled."));
                }
            } else if (args[0].equals("off")) {
                if (DataStorage.getMaintenance()) {
                    DataStorage.setMaintenance(false);
                    MessageMethods.sendActionBar(player, Utils.colorize("&e&l[&f&l!&e&l]&r &6Server Ready. Maintenance: &c&lDISABLED"));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                } else {
                    player.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cMaintenance Mode is already disabled."));
                }
            }
        } else {
            if (args.length == 0) {
                sender.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cUsage: /maintenance <on/off>"));
                return true;
            } else if (args[0].equals("on")) {
                if (!DataStorage.getMaintenance()) {
                    DataStorage.setMaintenance(true);
                    for (Player online : DataStorage.getOnline()) {
                        if (!online.hasPermission("uc.entermaintenance")) {
                            online.kickPlayer(Utils.colorize("&7[&e&lMAINTENANCE&7]\n&cThe Server is Entering Maintenance."));
                        }
                    }
                } else {
                    sender.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cMaintenance Mode is already enabled."));
                }
            } else if (args[0].equals("off")) {
                if (DataStorage.getMaintenance()) {
                    DataStorage.setMaintenance(false);
                } else {
                    sender.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cMaintenance Mode is already disabled."));
                }
            }
        }
        return true;
    }
}
