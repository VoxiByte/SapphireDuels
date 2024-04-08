package dev.staticabuser.sapphireduels.handlers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class InventoryHandler {
    HashMap<UUID, ItemStack[]> inventories = new HashMap<>();

    public void savePlayerInventory(Player player) {
        Inventory inventory = player.getInventory();
        inventories.put(player.getUniqueId(), inventory.getContents());
    }

    public void removePlayerInventory(Player player) {
        inventories.remove(player.getUniqueId());
    }
    public void loadPlayerInventory(Player player) {
        if (inventories.containsKey(player.getUniqueId())) {
            ItemStack[] items = inventories.get(player.getUniqueId());
            player.getInventory().setContents(items);
        }
    }
}
