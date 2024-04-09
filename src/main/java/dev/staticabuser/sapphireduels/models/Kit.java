package dev.staticabuser.sapphireduels.models;

import dev.staticabuser.sapphireduels.utils.TagUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class Kit {
    private ItemStack display;
    private List<ItemStack> items;
    public Kit(ItemStack display, List<ItemStack> items) {
        this.display = display;
        this.items = items;
    }

    public void give(Player player) {
        ItemStack[] armorContents = new ItemStack[4];
        int index = 3;
        for (ItemStack item : items) {
            if (TagUtil.isArmor(item, player) && index >= 0) {
                armorContents[index] = item;
                index--;
            } else {
                player.getInventory().addItem(item);
            }
        }
        player.getEquipment().setArmorContents(armorContents);
    }

    public ItemStack getDisplay() {
        return display;
    }
}
