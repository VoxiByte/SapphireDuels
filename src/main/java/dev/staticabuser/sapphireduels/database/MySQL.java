package dev.staticabuser.sapphireduels.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class MySQL {
    private final Connection connection;
    private final int port;
    private final String host;
    private final String database;
    private final String table;
    private final String username;
    private final String password;

    public MySQL(int port, String host, String database, String table, String username, String password) throws SQLException {
        this.port = port;
        this.host = host;
        this.database = database;
        this.table = table;
        this.username = username;
        this.password = password;
        this.connection = DriverManager.getConnection(
                "jdbc:mysql://" + host + ":" + port + "/" + database,
                username,
                password
        );
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return connection != null;
    }

    public String getTable() {
        return table;
    }
    public void addPlayer(Player player) throws SQLException {
        String sql = "INSERT INTO " + getTable() + " (uuid, duels, wins, losses) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = getConnection().prepareStatement(sql);

        String uuid = player.getUniqueId().toString();
        statement.setString(1, uuid);
        statement.setInt(2, 0);
        statement.setInt(3, 0);
        statement.setInt(4, 0);
        statement.executeUpdate();
        statement.close();
    }
    public void addPlayerDuelLoss(Player player) {
        try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE " + getTable() + " SET losses = losses + 1 WHERE uuid = ?;")) {
            updateStatement.setString(1, player.getUniqueId().toString());
            updateStatement.executeUpdate();
            System.out.println("Player duels updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating player duels: " + e.getMessage());
        }
    }
    public void addPlayerDuelWin(Player player) {
        try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE " + getTable() + " SET wins = wins + 1 WHERE uuid = ?;")) {
            updateStatement.setString(1, player.getUniqueId().toString());
            updateStatement.executeUpdate();
            System.out.println("Player duels updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating player duels: " + e.getMessage());
        }
    }
    public void addPlayerDuel(Player player) throws SQLException {
        try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE " + getTable() + " SET duels = duels + 1 WHERE uuid = ?;")) {
            updateStatement.setString(1, player.getUniqueId().toString());
            updateStatement.executeUpdate();
            System.out.println("Player duels updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating player duels: " + e.getMessage());
        }
    }
    public String getTopPlayer(String category, int position) throws SQLException {
        int playerPosition = position - 1;
        /* Old one:
        String query = "SELECT uuid FROM " + getTable() + " WHERE " + category + " > 0" +
        " AND " + category + " = (SELECT MAX(" + category + ") FROM " + getTable() + " WHERE " + category + " IS NOT NULL);";
        */
        String query = "SELECT * FROM " + getTable() + " ORDER BY " + category + " DESC LIMIT 1 OFFSET " + playerPosition + ";";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);

        String topPlayerUuid = null;
        if (result.next()) {
            topPlayerUuid = result.getString("uuid");
        }

        result.close();
        statement.close();

        if (topPlayerUuid == null) {
            System.out.println("No players found with the highest " + category + ".");
        }
        return Bukkit.getOfflinePlayer(UUID.fromString(topPlayerUuid)).getName();
    }
    /*
    offset = position - 1
    SELECT * FROM table ORDER BY category LIMIT 1 OFFSET offset
     */
}
