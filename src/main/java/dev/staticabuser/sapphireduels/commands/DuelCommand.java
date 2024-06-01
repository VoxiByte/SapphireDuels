package dev.staticabuser.sapphireduels.commands;

import dev.staticabuser.sapphireduels.SapphireDuels;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DuelCommand implements CommandExecutor, TabCompleter {
    private final SapphireDuels plugin;

    public DuelCommand(SapphireDuels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            if (args.length < 2) return true;

            String duelAction = args[0];
            String enemyName = args[1];

            Player enemy = Bukkit.getPlayerExact(enemyName);
            if (enemy == null) {
                player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("messages.player-null")));
                return true;
            } else if (enemy == player) {
                player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("messages.player-myself")));
                return true;
            }
            switch (duelAction) {
                case "request":
                    if (plugin.getDuelRequestHandler().playerSentRequestTo(player.getUniqueId(), enemy.getUniqueId())) {
                        player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("messages.request-already-sent")));
                    } else {
                        plugin.getDuelRequestHandler().sendRequest(player, enemy);
                        player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("messages.sent-request")).replace("[player]", enemy.getName()));
                    }
                    break;
                case "accept":
                    if (plugin.getDuelRequestHandler().playerHasRequestFrom(player.getUniqueId(), enemy.getUniqueId())) {
                        plugin.getDuelHandler().createDuel(player, enemy);
                        player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("messages.request-accepted")).replace("[player]", enemy.getName()));
                        enemy.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("messages.player-request-accepted")).replace("[player]", player.getName()));
                    } else {
                        player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("messages.no-duel-request")));
                        return true;
                    }
                    break;
                case "refuse":
                    if (plugin.getDuelRequestHandler().playerHasRequestFrom(player.getUniqueId(), enemy.getUniqueId())) {
                        plugin.getDuelRequestHandler().getDuelRequests().get(player.getUniqueId()).remove(enemy.getUniqueId());
                        player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("messages.refused-request").replace("[player]", enemy.getName())));
                        enemy.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("messages.player-request-refused").replace("[player]", player.getName())));
                    } else {
                        player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("messages.no-duel-request")));
                        return true;
                    }
                    break;

            }
            return true;
        }

        return true;

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> results = new ArrayList<>();
        switch (args.length) {
            case 1:
                results.add("request");
                results.add("accept");
                results.add("refuse");
                break;
            case 2:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    results.add(player.getName());
                }
        }
        return results;
    }
}