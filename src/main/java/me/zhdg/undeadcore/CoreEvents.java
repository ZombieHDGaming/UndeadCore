package me.zhdg.undeadcore;

import me.zhdg.undeadcore.config.Config;
import me.zhdg.undeadcore.config.PlayerConfig;
import me.zhdg.undeadcore.utils.DataStorage;
import me.zhdg.undeadcore.utils.Utils;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.*;

import java.util.Objects;

public class CoreEvents implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void loginSystem(PlayerLoginEvent event) {
        if (DataStorage.getMaintenance()) {
            if (event.getPlayer().hasPermission("uc.entermaintenance")) {
                event.allow();
            } else {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
                        Utils.colorize("&7&l[ &e&lMAINTENANCE &7&l]&r"
                                + "\n\n&aWe are currently doing required Maintenance."
                                + "\n&aDuring Maintenance, only Admins, Developers, and Builders will Have access to the server."
                                + "\n\n&7If you would like to know when the server will start again,"
                                + "\n&7please check the &9Discord &7for the listed Maintenance completion"
                                + "\n\n&9Discord: &7&n" + Config.get().getString("Discord") + "\n"));
            }
        }
        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
            if (event.getPlayer().hasPermission("uc.limitbypass")) {
                event.allow();
            } else {
                event.disallow(PlayerLoginEvent.Result.KICK_FULL,
                        Utils.colorize("&7&l[ &e&lTHE SANCTUARY &7&l]&r"
                                + "\n\n&aThe Server is Currently full."
                                + "\n\n&7If you wish to join, buy a rank on our store,"
                                + "\n&7or purchase exclusive access on the store."
                                + "\n\n&7If you have purchased a rank or access and you are still seeing this message,"
                                + "\n&7Contact a Staff Member on the &9Discord&c."
                                + "\n\n&9Discord: &7&n" + Config.get().getString("Discord") + "\n"));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void joinSystem(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        DataStorage.getOnline().add(player);
        PlayerConfig config = PlayerConfig.getConfig(player.getUniqueId());
        player.setDisplayName(config.getFile().getString("nick"));
        player.setPlayerListName(Utils.getVaultName(player));
        event.setJoinMessage(null);

        new FancyMessage(Utils.colorize("&8&m+---------------------------------------------------+\n"))
                .then(Utils.colorize("&7Welcome to &e&lThe Sanctuary\n\n"))
                .then(Utils.colorize(" &7- "))
                .then(Utils.colorize("&e&lSTORE")).link("http://www.google.com/").tooltip(Utils.colorize("&aClick to go to &e&lThe Warehouse&7\nWe offer all our ranks and packages there.\n\n&ehttp://www.store.altitude.com/"))
                .then(Utils.colorize(" &7- "))
                .then(Utils.colorize("&9&lDiscord")).link("http://www.google.com/").tooltip(Utils.colorize("&aClick to go to the Discord\nInteract with the community!\n\n&e" + Config.get().getString("Discord")))
                .then(Utils.colorize(" &7- \n"))
                .then(Utils.colorize("&8&m+---------------------------------------------------+\n"))
                .send(player);

        if (player.hasPermission("uc.safelogin") && player.getLocation().subtract(0, 2, 0).getBlock().isEmpty()) {
            DataStorage.getFlying().add(player);
            player.setAllowFlight(true);
            player.setFlying(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void quitSystem(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        DataStorage.getOnline().remove(player);
        DataStorage.getFlying().remove(player);
        event.setQuitMessage(null);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void chatSystem(AsyncPlayerChatEvent event) {
        String new1 = event.getMessage();
        for (Player p : DataStorage.getOnline()) {
            new1 = new1.replaceAll(p.getName(), Utils.colorize("&e@" + p.getName() + "&f"));
            if (event.getMessage().contains(p.getName())) {
                p.playSound(p.getEyeLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
            }
        }
        event.setMessage(new1);
        event.setFormat(Utils.colorize(Utils.getVaultName(event.getPlayer()) + " &7&l>&r ") + event.getMessage());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSignChange(SignChangeEvent event) {
        if (!event.getPlayer().hasPermission("uc.colorsign")) {
            return;
        }
        event.setLine(0, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(event.getLine(0))));
        event.setLine(1, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(event.getLine(1))));
        event.setLine(2, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(event.getLine(2))));
        event.setLine(3, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(event.getLine(3))));
    }
}
