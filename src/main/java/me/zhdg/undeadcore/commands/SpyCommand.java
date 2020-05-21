package me.zhdg.undeadcore.commands;

import me.zhdg.undeadcore.api.MessageMethods;
import me.zhdg.undeadcore.utils.DataStorage;
import me.zhdg.undeadcore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender user, Command cmd, String command, String[] args) {
        if (user instanceof Player) {
            Player sender = (Player) user;
            if (DataStorage.getSpy().contains(sender)) {
                DataStorage.getSpy().remove(sender);
                MessageMethods.sendActionBar(sender, Utils.colorize("&7Spy System: &c&lDISABLED&r"));
            } else {
                DataStorage.getSpy().add(sender);
                MessageMethods.sendActionBar(sender, Utils.colorize("&7Spy System: &a&lENABLED&r"));
            }
        }
        return true;
    }
}
