package dev.staticabuser.sapphireduels.listeners;

import dev.staticabuser.sapphireduels.SapphireDuels;
import dev.staticabuser.sapphireduels.models.Duel;
import dev.staticabuser.sapphireduels.models.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryInteractionEvent implements Listener {
    private final SapphireDuels plugin;

    public InventoryInteractionEvent(SapphireDuels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("Kit Selector")) return;
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        if (plugin.getDuelHandler().isPlayerInDuel(player)) {
            ItemStack kitDisplayItem = event.getCurrentItem();
            Duel duel = plugin.getDuelHandler().getDuel(player);
            Kit kit = plugin.getKitsHandler().getKit(kitDisplayItem.getItemMeta().getLocalizedName());
            System.out.println(kitDisplayItem);
            duel.getVotes().get(kit).add(player.getUniqueId());
            player.closeInventory();
        }
    }
}

