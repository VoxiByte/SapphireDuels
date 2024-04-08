package dev.staticabuser.sapphireduels.handlers;

import dev.staticabuser.sapphireduels.SapphireDuels;
import dev.staticabuser.sapphireduels.models.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@SuppressWarnings("deprecation")
public class KitsHandler {
    private final SapphireDuels plugin;
    private final Inventory kitsInventory = Bukkit.createInventory(null, 54, "Kit Selector");
    private final List<Kit> kits = new ArrayList<>();
    public KitsHandler(SapphireDuels plugin) {
        this.plugin = plugin;
        loadKits();
    }

    private void loadKits() {
        Objects.requireNonNull(plugin.getConfigHandler().getKits().getConfigurationSection("kits")).getKeys(false).forEach(kitId -> {
            List<ItemStack> kitItems = new ArrayList<>();
            List<String> kitItemsString = plugin.getConfigHandler().getKits().getStringList("kits." + kitId + ".items");
            kitItemsString.forEach(itemString -> {
                Material kitItemMaterial = Material.matchMaterial(itemString.split(":")[0].toLowerCase()); // Convert material name to lowercase
                int amount = Integer.parseInt(itemString.split(":")[1]);
                assert kitItemMaterial != null;
                ItemStack kitItem = new ItemStack(kitItemMaterial, amount);
                ItemMeta kitItemMeta = kitItem.getItemMeta();
                kitItemMeta.setLocalizedName(kitId);
                kitItem.setItemMeta(kitItemMeta);
                System.out.println("Adding itemstack " + kitItem + "to kititems");
                kitItems.add(kitItem);
            });

            String displayItemString = plugin.getConfigHandler().getKits().getString("kits." + kitId + ".display_item");
            assert displayItemString != null;
            String[] displayItemSplit = displayItemString.split(":"); // Split display item string
            Material displayItemMaterial = Material.matchMaterial(displayItemSplit[0].toLowerCase()); // Convert material name to lowercase

            int amount = Integer.parseInt(displayItemSplit[1]);
            String displayItemName = displayItemSplit[2];
            List<String> displayItemLore = plugin.getConfigHandler().getKits().getStringList("kits." + kitId + ".display_lore");

            assert displayItemMaterial != null;
            ItemStack displayItemStack = new ItemStack(displayItemMaterial, amount);
            ItemMeta displayItemMeta = displayItemStack.getItemMeta();
            displayItemMeta.setDisplayName(displayItemName);
            displayItemMeta.setLore(displayItemLore);
            displayItemStack.setItemMeta(displayItemMeta);

            Kit kit = new Kit(
                    displayItemStack,
                    kitItems
            );
            kits.add(kit);
            System.out.println(kit.getDisplay());
        });
        for (Kit kit : kits) {
            kitsInventory.addItem(kit.getDisplay());
        }
    }
    public void openKitsInventory(Player player) {
        player.openInventory(kitsInventory);
    }
    public Kit getKit(String localizedName) {
        for (Kit kit : kits) {
            System.out.println(kit.getDisplay());
            if (kit.getDisplay().getItemMeta().getLocalizedName() == localizedName) {
                return kit;
            }
        }
        return null;
    }

    public List<Kit> getKits() {
        return kits;
    }
}
