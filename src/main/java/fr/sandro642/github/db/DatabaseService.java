package fr.sandro642.github.db;

import fr.sandro642.github.log.Logger;

import java.sql.*;

public class DatabaseService {
    // Instance of DatabaseService
    private static final DatabaseService INSTANCE = new DatabaseService();

    private static final String URL = "jdbc:sqlite:oreratio_storage.db";

    static {
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();

            // Tables existantes
            stmt.execute("CREATE TABLE IF NOT EXISTS RatioTable (id TEXT PRIMARY KEY, UUID TEXT, PlayerName TEXT, content TEXT, updated_at DATETIME DEFAULT CURRENT_TIMESTAMP);");

            Logger.getInstance().SUCCESS("Database initialized successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void save(String table, String id,String UUID, String PlayerName, String content, String Type) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "INSERT OR REPLACE INTO " + table + " (id, UUID, PlayerName, content, updated_at) VALUES (?, ?, ?, ?, ?)";
            String timestamp = java.time.Instant.now().toString();

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, UUID);
            pstmt.setString(3, PlayerName);
            pstmt.setString(4, content);
            pstmt.setString(5, timestamp);
            pstmt.executeUpdate();
        }
    }

    public Object getInfo(String table, String query, String value, String valueToReturn) throws SQLException {
        if (!table.equals("InfoTable")) {
            return false;
        }

        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "SELECT * FROM " + table + " WHERE " + query + " = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(valueToReturn);
            }
        }
        return false;
    }

    // Getter for DatabaseService instance
    public static DatabaseService getInstance() {
        return INSTANCE;
    }
}
