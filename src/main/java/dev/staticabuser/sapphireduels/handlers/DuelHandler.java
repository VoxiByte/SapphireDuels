package dev.staticabuser.sapphireduels.handlers;

import dev.staticabuser.sapphireduels.SapphireDuels;
import dev.staticabuser.sapphireduels.models.Duel;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DuelHandler {
    private final SapphireDuels plugin;
    private final List<Duel> duelList = new ArrayList<>();

    public DuelHandler(SapphireDuels plugin) {
        this.plugin = plugin;
    }

    public void createDuel(Player duelistOne, Player duelistTwo) {
        Duel duel = new Duel(duelistOne, duelistTwo, plugin.getArenaHandler().getFirstAvailableArena(), duelList.size(), plugin);
        duel.start();
        duelList.add(duel);
    }

    public boolean isPlayerInDuel(Player player) {
        for (Duel duel : duelList) {
            if (Arrays.asList(duel.getPlayers()).contains(player)) {
                return true;
            }
        }
        return false;
    }

    public Duel getDuel(Player player) {
        for (Duel duel : duelList) {
            if (Arrays.asList(duel.getPlayers()).contains(player)) {
                return duel;
            }
        }
        return null;
    }

    public List<Duel> getDuelList() {
        return duelList;
    }
}
