package dev.staticabuser.sapphireduels.hooks;

import dev.staticabuser.sapphireduels.SapphireDuels;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;

public class TopDuels extends PlaceholderExpansion {
    private final SapphireDuels plugin;

    public TopDuels(SapphireDuels plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "topduels";
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
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }
    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        try {
            // int position = Integer.parseInt(params);
            return plugin.getDatabase().getTopPlayer("duels");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
