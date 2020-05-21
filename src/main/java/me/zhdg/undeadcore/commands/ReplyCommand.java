package me.zhdg.undeadcore.commands;

import me.zhdg.undeadcore.utils.DataStorage;
import me.zhdg.undeadcore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ReplyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender user, Command cmd, String command, String[] args) {
        StringBuilder message2 = new StringBuilder();
        if (user instanceof Player) {
            for (String s : args) {
                String arg = s + " ";
                message2.append(arg);
            }
            Player sender = (Player) user;
            if (!DataStorage.getPMs().containsKey(sender)) {
                sender.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cYou have nobody to reply to!"));
                return false;
            }
            if (!DataStorage.getPMs().get(sender).isOnline()) {
                sender.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cThe player you are trying to reply to is offline."));
                return false;
            }
            final Player target = DataStorage.getPMs().get(sender);
            if (args.length == 0) {
                sender.sendMessage(Utils.colorize("&cUsage: /r <message>"));
            } else {
                if (!DataStorage.getToggle().contains(sender)) {
                    if (!DataStorage.getToggle().contains(Objects.requireNonNull(target))) {
                        sender.sendMessage(Utils.colorize("&6&lTO: &r" + Utils.getVaultName(target) + " &r&7&l>&r " + message2));
                        target.sendMessage(Utils.colorize("&6&lFROM: &r" + Utils.getVaultName(sender) + " &r&7&l>&r " + message2));
                        for (Player online : DataStorage.getSpy()) {
                            online.sendMessage(Utils.colorize("&6&lSPY: &r" + Utils.getVaultName(sender) + " &r&6&l>&r " + Utils.getVaultName(target) + " &r&6&l:&r " + message2));
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
        return false;
    }
}
