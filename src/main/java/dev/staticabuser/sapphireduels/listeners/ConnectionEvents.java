package dev.staticabuser.sapphireduels.listeners;

import dev.staticabuser.sapphireduels.SapphireDuels;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class ConnectionEvents implements Listener {
    private SapphireDuels plugin;

    public ConnectionEvents(SapphireDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getDuelRequestHandler().getDuelRequests().put(player.getUniqueId(), new ArrayList<>());
        System.out.println("Added " + player.getName() + "'s request list.");
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getDuelRequestHandler().getDuelRequests().remove(player.getUniqueId());
        System.out.println("Removed " + player.getName() + "'s pending requests.");
    }
}
