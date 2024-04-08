package dev.staticabuser.sapphireduels.models;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Kit {
    private ItemStack display;
    private List<ItemStack> items;
    public Kit(ItemStack display, List<ItemStack> items) {
        this.display = display;
        this.items = items;
    }

    public void give(Player player) {
        for (ItemStack item : items) {
            player.getInventory().addItem(item);
        }
    }

    public ItemStack getDisplay() {
        return display;
    }
}
