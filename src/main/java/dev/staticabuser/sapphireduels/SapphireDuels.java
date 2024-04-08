package dev.staticabuser.sapphireduels;

import dev.staticabuser.sapphireduels.commands.DuelCommand;
import dev.staticabuser.sapphireduels.database.MySQL;
import dev.staticabuser.sapphireduels.handlers.*;
import dev.staticabuser.sapphireduels.hooks.TopDuels;
import dev.staticabuser.sapphireduels.hooks.TopLosses;
import dev.staticabuser.sapphireduels.hooks.TopWins;
import dev.staticabuser.sapphireduels.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
public final class SapphireDuels extends JavaPlugin {
    private DuelHandler duelHandler;
    private DuelRequestHandler duelRequestHandler;
    private ArenaHandler arenaHandler;
    private ConfigHandler configHandler;
    private InventoryHandler inventoryHandler;
    private LastLocationHandler lastLocationHandler;
    private KitsHandler kitsHandler;
    private MySQL database;
    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getConfig().options().copyDefaults();
            saveDefaultConfig();

            new TopDuels(this).register();
            new TopWins(this).register();
            new TopLosses(this).register();

            getServer().getPluginManager().registerEvents(new ConnectionEvents(this), this);
            getServer().getPluginManager().registerEvents(new PlayerDeathEvent(this),this);
            getServer().getPluginManager().registerEvents(new DuelLoseEvent(this), this);
            getServer().getPluginManager().registerEvents(new DuelWinEvent(this),this);
            getServer().getPluginManager().registerEvents(new InventoryInteractionEvent(this),this);

            loadCommand("duel", new DuelCommand(this), new DuelCommand(this));

            duelHandler = new DuelHandler(this);
            duelRequestHandler = new DuelRequestHandler(this);
            arenaHandler = new ArenaHandler(this);
            configHandler = new ConfigHandler(this);
            inventoryHandler = new InventoryHandler();
            lastLocationHandler = new LastLocationHandler();
            kitsHandler = new KitsHandler(this);

            int port = configHandler.getSettings().getInt("port");
            String host = configHandler.getSettings().getString("host");
            String db = configHandler.getSettings().getString("database");
            String table = configHandler.getSettings().getString("table");
            String username = configHandler.getSettings().getString("username");
            String password = configHandler.getSettings().getString("password");
            try {
                database = new MySQL(port, host, db, table, username, password);
                getLogger().log(Level.FINE, "Connected to database!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            getLogger().log(Level.WARNING,"Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        try {
            database.getConnection().close();
            getLogger().log(Level.FINE, "Closing database connection...");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCommand(String name, CommandExecutor executor, TabCompleter tabCompleter) {
        Objects.requireNonNull(getCommand(name)).setExecutor(executor);
        if (tabCompleter != null) {
            Objects.requireNonNull(getCommand(name)).setTabCompleter(tabCompleter);
        }
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

    public InventoryHandler getInventoryHandler() { return inventoryHandler; }

    public LastLocationHandler getLastLocationHandler() { return lastLocationHandler; }

    public MySQL getDatabase() {
        return database;
    }

    public KitsHandler getKitsHandler() {
        return kitsHandler;
    }
}
