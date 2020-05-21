package me.zhdg.undeadcore.commands;

import me.zhdg.undeadcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InvseeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cUsage: /invsee (player)"));
            } else {
                if (args.length == 1) {
                    Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                    if (targetPlayer != null) {
                        Inventory inv = targetPlayer.getInventory();
                        player.openInventory(inv);
                    } else {
                        player.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cPlayer not found."));
                    }
                } else if (args.length == 2) {
                    Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);
                    Inventory inv = Bukkit.getServer().createInventory(targetPlayer, 9, "Equipped");
                    inv.setContents(targetPlayer.getInventory().getArmorContents());
                    player.openInventory(inv);
                }
            }
        }
        return true;
    }
}
