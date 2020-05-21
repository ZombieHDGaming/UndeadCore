package me.zhdg.undeadcore;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import me.zhdg.undeadcore.api.Chat;
import me.zhdg.undeadcore.commands.Commands;
import me.zhdg.undeadcore.config.Config;
import me.zhdg.undeadcore.config.PlayerConfig;
import me.zhdg.undeadcore.utils.DataStorage;
import me.zhdg.undeadcore.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    private static final Logger LOG = Logger.getLogger("Minecraft");
    public static Main INSTANCE;
    public static Economy ECON = null;

    @Override
    public void onEnable() {
        INSTANCE = this;

        Config.setup();

        if (!getServer().getPluginManager().isPluginEnabled("Vault")) {
            LOG.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Utils.setupChat();
        Commands.initCommands(this);
        Events.initEvents();

        final List<WrappedGameProfile> names = new ArrayList<>();
        names.add(new WrappedGameProfile("1", Utils.colorize("&e&lThe Sanctuary Network")));
        names.add(new WrappedGameProfile("2", Utils.colorize("")));
        names.add(new WrappedGameProfile("3", Utils.colorize("&7A getaway from the normal Minecraft experience")));
        names.add(new WrappedGameProfile("4", Utils.colorize("&7Hosted and run by: &a&lZHDG Hosting")));
        names.add(new WrappedGameProfile("5", Utils.colorize("")));
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Status.Server.SERVER_INFO) {
            @Override
            public void onPacketSending(PacketEvent event) {
                WrappedServerPing ping = event.getPacket().getServerPings().read(0);
                if (DataStorage.getMaintenance()) {
                    ping.setVersionProtocol(999);
                    ping.setMotD(Chat.centerMotD("&e&lThe Sanctuary&r") + "\n" + Chat.centerMotD("&c&lMAINTENANCE MODE"));
                    ping.setVersionName(Utils.colorize("&a&l1/31"));
                } else {
                    ping.setVersionName(Utils.colorize("&cRequires MC 1.15.X"));
                    ping.setMotD(Chat.centerMotD("&e&lThe Sanctuary&r") + "\n" + Chat.centerMotD("&7Season 1: &6New Beginnings"));
                    ping.setPlayers(names);
                }
            }
        });

        if (!this.setupEconomy()) {
            System.out.println(" ");
            LOG.severe("Could not find a Vault economy dependency!");
            System.out.println(" ");
            this.getServer().getPluginManager().disablePlugin(this);
        } else {
            System.out.println(" ");
            LOG.info("Vault Economy Found and Hooked.");
            System.out.println(" ");
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(
                this,
                () -> {
                    for (Player player : DataStorage.getOnline()) {
                        PlayerConfig config = PlayerConfig.getConfig(player.getUniqueId());
                        player.setDisplayName(config.getFile().getString("nick").equalsIgnoreCase("") ? player.getName() : config.getFile().getString("nick"));
                        player.setPlayerListName(Utils.getVaultName(player));
                    }
                }, 0L,
                40L);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    private boolean setupEconomy() {
        final RegisteredServiceProvider<Economy> economyProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        Optional.ofNullable(economyProvider).map(provider -> (Main.ECON = provider.getProvider())).orElse(null);
        return Main.ECON != null;
    }
}
