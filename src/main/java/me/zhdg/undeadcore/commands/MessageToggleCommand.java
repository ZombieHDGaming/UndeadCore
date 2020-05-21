package me.zhdg.undeadcore.commands;

import me.zhdg.undeadcore.api.MessageMethods;
import me.zhdg.undeadcore.utils.DataStorage;
import me.zhdg.undeadcore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageToggleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender user, Command cmd, String command, String[] args) {
        if (user instanceof Player) {
            Player sender = (Player) user;
            if (DataStorage.getToggle().contains(sender)) {
                DataStorage.getToggle().remove(sender);
                MessageMethods.sendActionBar(sender, Utils.colorize("&ePrivate Messages: &a&lENABLED&r"));
            } else {
                DataStorage.getToggle().add(sender);
                MessageMethods.sendActionBar(sender, Utils.colorize("&ePrivate Messages: &c&lDISABLED&r"));
            }
        }
        return false;
    }
}
