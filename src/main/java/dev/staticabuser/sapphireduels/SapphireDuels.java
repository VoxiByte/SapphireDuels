package dev.staticabuser.sapphireduels;

import dev.staticabuser.sapphireduels.commands.DuelCommand;
import dev.staticabuser.sapphireduels.events.PlayerDuelLoseEvent;
import dev.staticabuser.sapphireduels.handlers.ArenaHandler;
import dev.staticabuser.sapphireduels.handlers.ConfigHandler;
import dev.staticabuser.sapphireduels.handlers.DuelHandler;
import dev.staticabuser.sapphireduels.handlers.DuelRequestHandler;
import dev.staticabuser.sapphireduels.listeners.ConnectionEvents;
import dev.staticabuser.sapphireduels.listeners.DuelLoseEvent;
import dev.staticabuser.sapphireduels.listeners.DuelWinEvent;
import dev.staticabuser.sapphireduels.listeners.PlayerDeathEvent;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SapphireDuels extends JavaPlugin {
    private DuelHandler duelHandler;
    private DuelRequestHandler duelRequestHandler;
    private ArenaHandler arenaHandler;
    private ConfigHandler configHandler;
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new ConnectionEvents(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathEvent(this),this);
        getServer().getPluginManager().registerEvents(new DuelLoseEvent(this), this);
        getServer().getPluginManager().registerEvents(new DuelWinEvent(this),this);
        loadCommand("duel", new DuelCommand(this));

        duelHandler = new DuelHandler(this);
        duelRequestHandler = new DuelRequestHandler(this);
        arenaHandler = new ArenaHandler(this);
        configHandler = new ConfigHandler(this);
    }

    @Override
    public void onDisable() {

    }

    private void loadCommand(String name, CommandExecutor executor) {
        Objects.requireNonNull(getCommand(name)).setExecutor(executor);
    }

    public DuelRequestHandler getDuelRequestHandler() {
        return duelRequestHandler;
    }

    public ArenaHandler getArenaHandler() {
        return arenaHandler;
    }

    public DuelHandler getDuelHandler() {
        return duelHandler;
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }
}
