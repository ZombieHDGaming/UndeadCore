package me.zhdg.undeadcore.commands;

import me.zhdg.undeadcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &7&oGamemodes Available&r&7: &eSurvival(0)&7, &eCreative(1)&7, &eAdventure(2)&7, &eSpectator(3)"));
                return true;
            } else if (args.length == 1) {
                if (args[0] != null) {
                    if (args[0].equals("0") | args[0].equalsIgnoreCase("s") | args[0].equalsIgnoreCase("survival")) {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(Utils.colorize("&eYour gamemode has been updated to &7&oSurvival&6."));
                        return true;
                    } else if (args[0].equals("1") | args[0].equalsIgnoreCase("c") | args[0].equalsIgnoreCase("creative")) {
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(Utils.colorize("&eYour gamemode has been updated to &7&oCreative&6."));
                        return true;
                    } else if (args[0].equals("2") | args[0].equalsIgnoreCase("a") | args[0].equalsIgnoreCase("adventure")) {
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage(Utils.colorize("&eYour gamemode has been updated to &7&oAdventure&6."));
                        return true;
                    } else if (args[0].equals("3") | args[0].equalsIgnoreCase("sp") | args[0].equalsIgnoreCase("spectator")) {
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage(Utils.colorize("&eYour gamemode has been updated to &7&oSpectator&6."));
                        return true;
                    }
                }
            } else if (args.length == 2) {
                if (args[0] != null) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target == null) {
                        player.sendMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cError: The player specified could not be found."));
                        return false;
                    }
                    if (args[0].equals("0") | args[0].equalsIgnoreCase("s") | args[0].equalsIgnoreCase("survival")) {
                        target.setGameMode(GameMode.SURVIVAL);
                        target.sendMessage(Utils.colorize("&eYour gamemode has been updated to &7&oSurvival&6."));
                        return true;
                    } else if (args[0].equals("1") | args[0].equalsIgnoreCase("c") | args[0].equalsIgnoreCase("creative")) {
                        target.setGameMode(GameMode.CREATIVE);
                        target.sendMessage(Utils.colorize("&eYour gamemode has been updated to &7&oCreative&6."));
                        return true;
                    } else if (args[0].equals("2") | args[0].equalsIgnoreCase("a") | args[0].equalsIgnoreCase("adventure")) {
                        target.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage(Utils.colorize("&eYour gamemode has been updated to &7&oAdventure&6."));
                        return true;
                    } else if (args[0].equals("3") | args[0].equalsIgnoreCase("sp") | args[0].equalsIgnoreCase("spectator")) {
                        target.setGameMode(GameMode.SPECTATOR);
                        target.sendMessage(Utils.colorize("&eYour gamemode has been updated to &7&oSpectator&6."));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
