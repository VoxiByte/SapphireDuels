package dev.staticabuser.sapphireduels.listeners;

import dev.staticabuser.sapphireduels.SapphireDuels;
import dev.staticabuser.sapphireduels.events.PlayerDuelLoseEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class DuelLoseEvent implements Listener {
    private final SapphireDuels plugin;

    public DuelLoseEvent(SapphireDuels plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onDuelLose(PlayerDuelLoseEvent event) {
        event.getPlayer().sendMessage(Objects.requireNonNull(plugin.getConfig().getString("messages.lose-message")));
    }
}
