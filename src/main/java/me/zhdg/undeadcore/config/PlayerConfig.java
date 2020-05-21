package me.zhdg.undeadcore.config;

import de.leonhard.storage.LightningFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerConfig {
    private static final Map<UUID, PlayerConfig> configs = new HashMap<>();
    private final Object saveLock = new Object();
    private LightningFile file = null;
    private UUID uuid;

    public PlayerConfig(UUID uuid) {
        super();
        file = new LightningFile(uuid.toString(), "plugins/UndeadCore/Players");
        this.uuid = uuid;
    }

    @SuppressWarnings("unused")
    private PlayerConfig() {
        uuid = null;
    }

    public static PlayerConfig getConfig(UUID uuid) {
        synchronized (configs) {
            if (configs.containsKey(uuid)) {
                return configs.get(uuid);
            }
            PlayerConfig config = new PlayerConfig(uuid);
            configs.put(uuid, config);
            return config;
        }
    }

    public LightningFile getFile() {
        return this.file;
    }

    public void save() {
        synchronized (saveLock) {
            try {
                file.write();
            } catch (Exception ignore) {
            }
        }
    }

    public void discard(boolean save) {
        if (save) {
            save();
        }
        synchronized (configs) {
            configs.remove(uuid);
        }
    }
}