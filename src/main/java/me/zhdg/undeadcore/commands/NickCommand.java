package me.zhdg.undeadcore.commands;

import me.zhdg.undeadcore.config.PlayerConfig;
import me.zhdg.undeadcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class NickCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cUsage: /nick <nick> <player>"));
                return true;
            } else if (args.length == 1) {
                PlayerConfig config = PlayerConfig.getConfig(player.getUniqueId());
                if (args[0].equalsIgnoreCase("off")) {
                    player.setDisplayName(player.getName());
                    player.setPlayerListName(Utils.getVaultName(player));
                    player.sendMessage(Utils.colorize("&7Nickname has been set to: &r" + player.getName()));
                    config.getFile().set("nick", player.getName());
                    config.save();
                    return true;
                }
                player.setDisplayName(Utils.colorize(args[0]));
                player.setPlayerListName(Utils.getVaultName(player));
                player.sendMessage(Utils.colorize("&7Nickname has been set to: &r" + args[0]));
                config.getFile().set("nick", Utils.colorize(args[0]));
                config.save();
            } else {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cYou need to specify an Online player"));
                } else {
                    PlayerConfig config = PlayerConfig.getConfig(target.getUniqueId());
                    if (args[0].equalsIgnoreCase("off")) {
                        target.setDisplayName(target.getName());
                        target.setPlayerListName(Utils.getVaultName(target));
                        player.sendMessage(Utils.colorize("&a" + target.getName() + " &7has had their nickname set to: " + target.getName()));
                        target.sendMessage(Utils.colorize("&7Nickname has been set to: &r" + target.getName()));
                        config.getFile().set("nick", target.getName());
                        config.save();
                        return true;
                    }
                    target.setDisplayName(Utils.colorize(args[0]));
                    target.setPlayerListName(Utils.getVaultName(target));
                    player.sendMessage(Utils.colorize("&a" + target.getName() + " &7has had their nickname set to: " + args[0]));
                    target.sendMessage(Utils.colorize("&7Nickname has been set to: &r" + args[0]));
                    config.getFile().set("nick", Utils.colorize(args[0]));
                    config.save();
                }
            }
        } else {
            if(args.length < 2) {
                sender.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cUsage: /nick <nick> <player>"));
                return true;
            } else {
                Player player = Bukkit.getPlayer(args[1]);
                if (player == null) {
                    sender.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cYou need to specify an Online player"));
                } else {
                    PlayerConfig config = PlayerConfig.getConfig(player.getUniqueId());
                    if (args[0].equalsIgnoreCase("off")) {
                        player.setDisplayName(player.getName());
                        player.setPlayerListName(Utils.getVaultName(player));
                        player.sendMessage(Utils.colorize("&7Nickname has been set to: &r" + player.getName()));
                        config.getFile().set("nick", player.getName());
                        return true;
                    }
                    player.setDisplayName(Utils.colorize(args[0]));
                    player.setPlayerListName(Utils.colorize(args[0]));
                    player.sendMessage(Utils.colorize("&7Nickname has been set to: &r" + args[0]));
                    config.getFile().set("nick", Utils.colorize(args[0]));
                }
            }
        }
        return true;
    }
}
