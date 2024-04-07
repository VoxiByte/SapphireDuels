package dev.staticabuser.sapphireduels.models;

import dev.staticabuser.sapphireduels.SapphireDuels;
import dev.staticabuser.sapphireduels.enums.ArenaState;
import dev.staticabuser.sapphireduels.events.PlayerDuelLoseEvent;
import dev.staticabuser.sapphireduels.events.PlayerDuelWinEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Duel {
    private final Player duelistOne;
    private final Player duelistTwo;
    private final Arena duelArena;
    private final int id;
    private final Player[] players;
    private final SapphireDuels plugin;
    public Duel(Player duelistOne, Player duelistTwo, Arena duelArena, int id, SapphireDuels plugin) {
        this.duelistOne = duelistOne;
        this.duelistTwo = duelistTwo;
        this.duelArena = duelArena;
        this.id = id;
        this.players = new Player[]{duelistOne, duelistTwo};
        this.plugin = plugin;
    }

    public Player getDuelistOne() { return duelistOne; }

    public Player getDuelistTwo() { return duelistTwo; }

    public int getId() { return id; }

    public Player[] getPlayers() {
        return players;
    }

    public void start() {
        duelArena.setArenaState(ArenaState.NOT_AVAILABLE);
        duelistOne.teleport(duelArena.getFirstSpawn());
        duelistTwo.teleport(duelArena.getSecondSpawn());
    }

    public void stop(Player duelWinner, Player duelLoser) {
        duelArena.setArenaState(ArenaState.AVAILABLE);
        PlayerDuelWinEvent playerDuelWinEvent = new PlayerDuelWinEvent(duelWinner);
        Bukkit.getPluginManager().callEvent(playerDuelWinEvent);
        PlayerDuelLoseEvent playerDuelLoseEvent = new PlayerDuelLoseEvent(duelLoser);
        Bukkit.getPluginManager().callEvent(playerDuelLoseEvent);
        plugin.getDuelHandler().getDuelList().remove(this);
        plugin.getDuelRequestHandler().getDuelRequests().get(duelLoser.getUniqueId()).remove(duelWinner.getUniqueId());
        plugin.getDuelRequestHandler().getDuelRequests().get(duelWinner.getUniqueId()).remove(duelLoser.getUniqueId());
    }
}
