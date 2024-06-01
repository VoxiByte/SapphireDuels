package dev.staticabuser.sapphireduels.handlers;

import dev.staticabuser.sapphireduels.SapphireDuels;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DuelRequestHandler {
    private final SapphireDuels plugin;
    private final HashMap<UUID, List<UUID>> duelRequests = new HashMap<>();

    public DuelRequestHandler(SapphireDuels plugin) {
        this.plugin = plugin;
    }

    public boolean playerHasRequestFrom(UUID playerUuid, UUID from) {
        List<UUID> playerDuelRequests = duelRequests.get(playerUuid);

        return playerDuelRequests.contains(from);
    }

    public boolean playerSentRequestTo(UUID playerUuid, UUID to) {
        List<UUID> playerDuelRequests = duelRequests.get(to);

        return playerDuelRequests.contains(playerUuid);
    }

    public HashMap<UUID, List<UUID>> getDuelRequests() {
        return duelRequests;
    }

    public void sendRequest(Player from, Player to) {
        List<UUID> playerDuelRequests = duelRequests.get(to.getUniqueId());
        playerDuelRequests.add(from.getUniqueId());
        to.sendMessage(Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("messages.request-received")).replace("[player]", from.getName())));
    }
}
