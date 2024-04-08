package dev.staticabuser.sapphireduels.handlers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class LastLocationHandler {
    private HashMap<UUID, Location> lastLocations = new HashMap<>();

    public void saveLastPlayerLocation(Player player) {
        Location location = player.getLocation();
        lastLocations.put(player.getUniqueId(), location);
    }

    public Location getLastPlayerLocation(Player player) {
        if (lastLocations.containsKey(player.getUniqueId())) {
            return lastLocations.get(player.getUniqueId());
        }
        return null;
    }

    public void removeLastPlayerLocation(Player player) {
        lastLocations.remove(player.getUniqueId());
    }
}
