package dev.staticabuser.sapphireduels.listeners;

import dev.staticabuser.sapphireduels.SapphireDuels;
import dev.staticabuser.sapphireduels.events.PlayerDuelWinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class DuelWinEvent implements Listener {
    private final SapphireDuels plugin;
    public DuelWinEvent(SapphireDuels plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onDuelWin(PlayerDuelWinEvent event) {
        event.getPlayer().sendMessage(Objects.requireNonNull(plugin.getConfig().getString("messages.win-message")));
    }
}
