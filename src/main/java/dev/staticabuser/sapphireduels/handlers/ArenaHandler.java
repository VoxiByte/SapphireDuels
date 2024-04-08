package dev.staticabuser.sapphireduels.handlers;

import dev.staticabuser.sapphireduels.SapphireDuels;
import dev.staticabuser.sapphireduels.enums.ArenaState;
import dev.staticabuser.sapphireduels.models.Arena;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArenaHandler {
    private final List<Arena> arenas = new ArrayList<>();
    private final SapphireDuels plugin;
    public ArenaHandler(SapphireDuels plugin) {
        this.plugin = plugin;
        loadArenas();
    }

    private void loadArenas() {
        ConfigurationSection arenaSection = plugin.getConfig().getConfigurationSection("arenas");
        assert arenaSection != null;
        arenaSection.getKeys(false).forEach(arenaId -> {
            System.out.println(plugin.getConfig().getString("arenas." + arenaId + "world"));
            World world = Bukkit.getWorld(Objects.requireNonNull(plugin.getConfig().getString("arenas." + arenaId + ".world")));
            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            Location arenaLocationA = new Location(
                    world,
                    plugin.getConfig().getDouble("arenas." + arenaId + ".locations.1.x"),
                    plugin.getConfig().getDouble("arenas." + arenaId + ".locations.1.y"),
                    plugin.getConfig().getDouble("arenas." + arenaId + ".locations.1.z")
            );
            Location arenaLocationB = new Location(
                    world,
                    plugin.getConfig().getDouble("arenas." + arenaId + ".locations.2.x"),
                    plugin.getConfig().getDouble("arenas." + arenaId + ".locations.2.y"),
                    plugin.getConfig().getDouble("arenas." + arenaId + ".locations.2.z")
            );
            Arena arena = new Arena(
                arenaLocationA, arenaLocationB, Integer.parseInt(arenaId), ArenaState.AVAILABLE
            );
            arenas.add(arena);
        });
    }
    public Arena getFirstAvailableArena() {
        for (Arena arena : arenas) {
            if (arena.getArenaState() == ArenaState.AVAILABLE) {
                return arena;
            }
        }
        return null;
    }
    public List<Arena> getArenas() {
        return arenas;
    }
}
