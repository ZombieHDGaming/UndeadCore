package me.zhdg.undeadcore;

import me.zhdg.undeadcore.commands.MessageCommand;
import me.zhdg.undeadcore.commands.StaffChatCommand;
import me.zhdg.undeadcore.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.TabCompleteEvent;

public class Events implements Listener {

    public static void initEvents() {
        setupEvent(new CoreEvents());
        setupEvent(new StaffChatCommand());
        setupEvent(new MessageCommand());
    }

    public static void setupEvent(Listener listener) {
        Main.INSTANCE.getServer().getPluginManager().registerEvents(listener, Main.INSTANCE);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void elytra(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.isGliding() && p.hasPermission("uc.ElytraUpgrade") && p.getLocation().getPitch() < 0) {
            p.setVelocity(p.getLocation().getDirection().normalize().multiply(1.225));
            //p.setVelocity(p.getVelocity().multiply(1.225));
        }
    }

    /*@EventHandler(priority = EventPriority.HIGHEST)
    public void fallDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (e.getCause() == DamageCause.FALL && Main.getFlying().contains(p.getUniqueId())) {
                e.setCancelled(true);
            } else if (e.getCause() == DamageCause.FALL && p.hasPermission("uc.ElytraUpgrade") && p.isGliding()) {
                e.setCancelled(true);
            } else if (e.getCause() == DamageCause.FLY_INTO_WALL && p.hasPermission("uc.ElytraUpgrade") && p.isGliding()) {
                e.setCancelled(true);
            }
        }
    }*/


    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        if (e.getPlayer().isOp()) {
            return;
        }
        if (e.getMessage().startsWith("/?") || e.getMessage().startsWith("/pl") || e.getMessage().startsWith("/plugins") || e.getMessage().startsWith("/about")) {
            e.getPlayer().sendMessage(Utils.colorize("&7&l[&a&lUC&7&l]&r &cYou do not have permission to execute this command."));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        if (!event.getBuffer().startsWith("/plugins")
                || !event.getBuffer().startsWith("/?")
                || !event.getBuffer().startsWith("/help")
                || !event.getBuffer().startsWith("/about")
                || !event.getBuffer().startsWith("/version")) {
            return;
        }
        //List<String> test = new ArrayList<String>();
        //test.add("Testing");
        //test.add("Hello");
		/*if(event.getBuffer().startsWith("/plugins")) {
			event.setCompletions(test);
		}*/
        CommandSender sender = event.getSender();
        if (sender.isOp() || sender.hasPermission("uc.tabcomplete")) {
            return;
        }
        event.setCancelled(true);
    }

    //	@EventHandler
    //	public void onBlockBreak(BlockBreakEvent event) {
    //		event.getBlock().getWorld().createExplosion(event.getBlock().getLocation(), 1);
    //		event.getPlayer().sendMessage(Utils.colorize("&6Kaboom!"));
    //	}
}
