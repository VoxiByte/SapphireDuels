package dev.staticabuser.sapphireduels.listeners;

import dev.staticabuser.sapphireduels.SapphireDuels;
import dev.staticabuser.sapphireduels.events.PlayerDuelWinEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelWinEvent implements Listener {
    private final SapphireDuels plugin;
    public DuelWinEvent(SapphireDuels plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onDuelWin(PlayerDuelWinEvent event) {
        plugin.getDatabase().addPlayerDuelWin(event.getPlayer());
        List<String> winRewards = plugin.getConfig().getStringList("duel-rewards");
        winRewards.forEach(reward -> {
            Pattern pattern = Pattern.compile("\\[([^]]+)\\] \\(([^)]+)\\)");
            Matcher matcher = pattern.matcher(reward);
            while (matcher.find()) {
                String rewardType = matcher.group(1);
                if (rewardType.equals("ITEM")) {
                    String itemReward = matcher.group(2);
                    Material rewardMaterial = Material.matchMaterial(itemReward.split(":")[0]);
                    int amount = Integer.parseInt(itemReward.split(":")[1]);
                    ItemStack itemStack = new ItemStack(rewardMaterial, amount);
                    event.getPlayer().getInventory().addItem(itemStack);
                }
            }
        });
    }
}
