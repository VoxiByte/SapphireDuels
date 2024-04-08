package dev.staticabuser.sapphireduels.models;

import dev.staticabuser.sapphireduels.SapphireDuels;
import dev.staticabuser.sapphireduels.enums.ArenaState;
import dev.staticabuser.sapphireduels.events.PlayerDuelLoseEvent;
import dev.staticabuser.sapphireduels.events.PlayerDuelWinEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.*;

public class Duel {
    private final Player duelistOne;
    private final Player duelistTwo;
    private final Arena duelArena;
    private final int id;
    private final Player[] players;
    private final SapphireDuels plugin;
    private final HashMap<Kit, List<UUID>> votes = new HashMap<>();
    public Duel(Player duelistOne, Player duelistTwo, Arena duelArena, int id, SapphireDuels plugin) {
        this.duelistOne = duelistOne;
        this.duelistTwo = duelistTwo;
        this.duelArena = duelArena;
        this.id = id;
        this.players = new Player[]{duelistOne, duelistTwo};
        this.plugin = plugin;
        for (Kit kit : plugin.getKitsHandler().getKits()) {
            votes.put(kit, new ArrayList<>());
        }
    }

    public int getId() { return id; }

    public Player[] getPlayers() {
        return players;
    }

    public void start() {
        duelArena.setArenaState(ArenaState.NOT_AVAILABLE);
        savePlayerInventories();
        savePlayerLastLocations();
        clearPlayerInventories();
        teleportPlayers();
        for (Player player : players) {
            try {
                plugin.getDatabase().addPlayerDuel(player);
                plugin.getKitsHandler().openKitsInventory(player);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            Kit longestKit = null;
            int longestListSize = 0;
            for (Map.Entry<Kit, List<UUID>> entry : votes.entrySet()) {
                int currentListSize = entry.getValue().size();
                if (currentListSize > longestListSize) {
                    longestListSize = currentListSize;
                    longestKit = entry.getKey();
                }
            }
            if (longestKit != null) {
                for (Player player : players) {
                    longestKit.give(player);
                }
            } else {
                System.out.println("No kits have any votes yet.");
            }
        },200);
    }

    public void stop(Player duelWinner, Player duelLoser) {
        duelArena.setArenaState(ArenaState.AVAILABLE);
        plugin.getDuelHandler().getDuelList().remove(this);
        removeDuelRequests(duelWinner, duelLoser);
        clearPlayerInventories();
        sendMessage(plugin.getConfig().getString("messages.teleport-message"));
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            duelistOne.teleport(plugin.getLastLocationHandler().getLastPlayerLocation(duelistOne));
            duelistTwo.teleport(plugin.getLastLocationHandler().getLastPlayerLocation(duelistTwo));
            removePlayerLastLocations();
            loadPlayerInventories();
            removePlayerInventories();
        }, 100);
        Bukkit.getPluginManager().callEvent(new PlayerDuelWinEvent(duelWinner));
        Bukkit.getPluginManager().callEvent(new PlayerDuelLoseEvent(duelLoser));

    }
    private void savePlayerInventories() {
        for (Player player : players) {
            plugin.getInventoryHandler().savePlayerInventory(player);
        }
    }
    private void loadPlayerInventories() {
        for (Player player : players) {
            plugin.getInventoryHandler().loadPlayerInventory(player);
        }
    }
    private void removePlayerInventories() {
        for (Player player : players) {
            plugin.getInventoryHandler().removePlayerInventory(player);
        }
    }
    private void savePlayerLastLocations() {
        for (Player player : players) {
            plugin.getLastLocationHandler().saveLastPlayerLocation(player);
        }
    }
    private void removePlayerLastLocations() {
        for (Player player : players) {
            plugin.getLastLocationHandler().removeLastPlayerLocation(player);
        }
    }
    private void teleportPlayers() {
        duelistOne.teleport(duelArena.getFirstSpawn());
        duelistTwo.teleport(duelArena.getSecondSpawn());
    }
    private void clearPlayerInventories() {
        for (Player player : players) {
            player.getInventory().clear();
        }
    }
    private void removeDuelRequests(Player duelWinner, Player duelLoser) {
        plugin.getDuelRequestHandler().getDuelRequests().get(duelLoser.getUniqueId()).remove(duelWinner.getUniqueId());
        plugin.getDuelRequestHandler().getDuelRequests().get(duelWinner.getUniqueId()).remove(duelLoser.getUniqueId());
    }

    private void sendMessage(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }

    public HashMap<Kit, List<UUID>> getVotes() {
        return votes;
    }
}
