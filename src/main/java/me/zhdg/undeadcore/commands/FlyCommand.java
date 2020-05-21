package me.zhdg.undeadcore.commands;

import me.zhdg.undeadcore.api.MessageMethods;
import me.zhdg.undeadcore.utils.DataStorage;
import me.zhdg.undeadcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (DataStorage.getFlying().contains(player)) {
                    DataStorage.getFlying().remove(player);
                    player.setAllowFlight(false);
                    MessageMethods.sendActionBar(player, Utils.colorize("&6Fly Mode: &c&lDISABLED&r"));
                } else {
                    DataStorage.getFlying().add(player);
                    player.setAllowFlight(true);
                    MessageMethods.sendActionBar(player, Utils.colorize("&6Fly Mode: &a&lENABLED&r"));
                }
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    player.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cYou need to specify an online Player!"));
                    player.sendMessage(Utils.colorize("&cUsage: /fly (player)"));
                }
                if (DataStorage.getFlying().contains(target)) {
                    DataStorage.getFlying().remove(target);
                    MessageMethods.sendActionBar(player, Utils.colorize("&6Fly Mode for &e" + target.getName() + "&6: &c&lDISABLED"));
                    MessageMethods.sendActionBar(player, Utils.colorize("&6Fly Mode: &c&lDISABLED"));
                } else {
                    DataStorage.getFlying().add(target);
                    MessageMethods.sendActionBar(player, Utils.colorize("&6Fly Mode for &e" + target.getName() + "&6: &a&lENABLED"));
                    MessageMethods.sendActionBar(player, Utils.colorize("&6Fly Mode: &c&lENABLED"));
                }
            }
        }
        return true;
    }
}
