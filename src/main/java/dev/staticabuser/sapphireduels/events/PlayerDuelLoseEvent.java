package dev.staticabuser.sapphireduels.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerDuelLoseEvent extends Event {
    private Player player;
    private static final HandlerList handlers = new HandlerList();
    public Player getPlayer() { return player; }

    public PlayerDuelLoseEvent(Player player) {
        this.player = player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
