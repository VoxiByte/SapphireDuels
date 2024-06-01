package dev.staticabuser.sapphireduels.listeners;

import dev.staticabuser.sapphireduels.SapphireDuels;
import dev.staticabuser.sapphireduels.models.Duel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class PlayerDeathEvent implements Listener {
    private final SapphireDuels plugin;

    public PlayerDeathEvent(SapphireDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            Player playerKiller = player.getKiller();
            if (plugin.getDuelHandler().isPlayerInDuel(player)) {
                Duel duel = plugin.getDuelHandler().getDuel(player);
                duel.stop(playerKiller, player);
            }
        }
    }
}
