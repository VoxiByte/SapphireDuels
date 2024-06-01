package dev.staticabuser.sapphireduels.handlers;

import dev.staticabuser.sapphireduels.SapphireDuels;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigHandler {
    private final SapphireDuels plugin;
    private YamlConfiguration kits;
    private YamlConfiguration settings;

    public ConfigHandler(SapphireDuels plugin) {
        this.plugin = plugin;
        setupConfigs();
    }

    public YamlConfiguration initConfig(String name) {
        File file = new File(plugin.getDataFolder(), name);

        if (!file.exists()) {
            plugin.saveResource(name, true);
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    public void setupConfigs() {
        kits = initConfig("kits.yml");
        settings = initConfig("settings.yml");
    }

    public YamlConfiguration getKits() {
        return kits;
    }

    public YamlConfiguration getSettings() {
        return settings;
    }
}
