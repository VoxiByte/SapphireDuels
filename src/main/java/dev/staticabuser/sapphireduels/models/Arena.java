package dev.staticabuser.sapphireduels.models;

import dev.staticabuser.sapphireduels.enums.ArenaState;
import org.bukkit.Location;

public class Arena {
    private final Location firstSpawn;
    private final Location secondSpawn;
    private final int id;
    private ArenaState arenaState;

    public Arena(Location firstSpawn, Location secondSpawn, int id, ArenaState arenaState) {
        this.firstSpawn = firstSpawn;
        this.secondSpawn = secondSpawn;
        this.id = id;
        this.arenaState = arenaState;
    }

    public Location getFirstSpawn() {
        return firstSpawn;
    }

    public Location getSecondSpawn() {
        return secondSpawn;
    }

    public int getId() {
        return id;
    }

    public ArenaState getArenaState() {
        return arenaState;
    }

    public void setArenaState(ArenaState arenaState) {
        this.arenaState = arenaState;
    }
}
