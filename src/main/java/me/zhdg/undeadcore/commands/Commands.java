package me.zhdg.undeadcore.commands;

import me.zhdg.undeadcore.utils.Utils;
import me.zhdg.undeadcore.api.Chat;
import mkremins.fanciful.FancyMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor {

    private static List<String> SC = new ArrayList<>();
    private static List<String> MSG = new ArrayList<>();
    private static List<String> REPLY = new ArrayList<>();
    private static List<String> NICK = new ArrayList<>();

    private static JavaPlugin PLUGIN;

    public static void initCommands(JavaPlugin pl) {
        PLUGIN = pl;
        SC.add("staffchat");

        MSG.add("pm");
        MSG.add("message");
        MSG.add("tell");

        REPLY.add("reply");

        NICK.add("nickname");

        setupCommand(new FlyCommand(), "fly", "uc.fly");

        setupCommand(new GamemodeCommand(), "gm", "uc.gamemode");

        setupCommand(new InvseeCommand(), "invsee", "uc.invsee");

        setupCommand(new MaintenanceCommand(), "maintenance", "uc.maintenance");

        setupCommand(new MessageCommand(), "msg", "uc.message");
        setupAlias("msg", MSG);

        setupCommand(new ReplyCommand(), "r", "uc.message");
        setupAlias("r", REPLY);

        setupCommand(new MessageToggleCommand(), "msgtoggle", "us.togglemsg");

        setupCommand(new SpyCommand(), "spy", "uc.spy");

        setupCommand(new StaffChatCommand(), "sc", "uc.staffchat");
        setupAlias("sc", SC);

        setupCommand(new NickCommand(), "nick", "uc.nickname");
        setupAlias("nick", NICK);
    }

    public static void setupCommand(CommandExecutor executor, String command, String perm) {
        PLUGIN.getCommand(command).setExecutor(executor);
        PLUGIN.getCommand(command).setPermission(perm);
        PLUGIN.getCommand(command).setPermissionMessage(Utils.colorize("&7&l[&e&lSANCTUARY&7&l] &cPermission Denied"));
    }

    public static void setupAlias(String command, List<String> alias) {
        PLUGIN.getCommand(command).setAliases(alias);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String command, String[] args) {
        if (cmd.getName().equalsIgnoreCase("alert")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("uc.alert")) {
                    player.sendMessage(" ");
                    player.sendMessage(Chat.centerV1("&8&m-----------------------------------------"));
                    player.sendMessage(Chat.centerV1("&a&lSHOP ALERT"));
                    player.sendMessage(Chat.centerV1("&7Ranks are now on sale!"));
                    player.sendMessage(Chat.centerV1("&7Up to &e&l50%&r &7on all ranks!"));
                    player.sendMessage(" ");
                    new FancyMessage(Chat.centerSpace("&a&lCLICK TO GO TO THE SHOP")).then(Utils.colorize("&a&lCLICK TO GO TO THE SHOP")).tooltip(Utils.colorize("&eClick to Open\n&a&lThe Warehouse")).send(player);
                    player.sendMessage(Chat.centerV1("&8&m-----------------------------------------"));
                    player.sendMessage(" ");
                }
            }
        }
        return true;
    }
}