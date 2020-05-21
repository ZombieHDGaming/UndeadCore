package me.zhdg.undeadcore.config;

import java.io.File;
import java.io.IOException;

import me.zhdg.undeadcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	static YamlConfiguration YourFile;
	static File pdfile;

	public static void setup() {
		pdfile = new File(Main.INSTANCE.getDataFolder(), "config.yml");

		if (!pdfile.exists()) {
			Main.INSTANCE.saveResource("config.yml", true);
		}

		YourFile = YamlConfiguration.loadConfiguration(pdfile);
	}

	public static YamlConfiguration get() {
		return YourFile;
	}

	public static void save() {
		try {
			YourFile.save(pdfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
		}
	}

	public static void reload() {
		YourFile = YamlConfiguration.loadConfiguration(pdfile);
	}

}
