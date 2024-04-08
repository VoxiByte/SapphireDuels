package dev.staticabuser.sapphireduels.hooks;

import dev.staticabuser.sapphireduels.SapphireDuels;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;

public class TopWins extends PlaceholderExpansion {
    private final SapphireDuels plugin;

    public TopWins(SapphireDuels plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "topwins";
    }

    @Override
    public @NotNull String getAuthor() {
        return "staticabuser";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        try {
            return plugin.getDatabase().getTopPlayer("wins");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
