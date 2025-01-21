package com.ideao.bookingsystem.migrations;

import com.ideao.bookingsystem.util.JdbcUtilities;
import java.sql.*;

public class UserTable {
    private Connection connection;

    public UserTable(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        String query =
                "CREATE TABLE IF NOT EXISTS user (" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "username VARCHAR(255) NOT NULL, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "PRIMARY KEY (id)" +
                ")";

        try(Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        }catch (SQLException e) {
            JdbcUtilities.printSQLException(e);
        }
    }

    public void populateTable(int qtd) throws SQLException {
        connection.setAutoCommit(false);
        String query = "INSERT INTO user (username, password) VALUES(?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            for(int i = 1; i <= qtd; i++) {
                pstmt.setString(1, "username" + i);
                pstmt.setString(2, "password" + i);
                pstmt.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            JdbcUtilities.printSQLException(e);
            try {
                System.err.println("Transaction is being rolled back");
                connection.rollback();
            } catch (SQLException ex) {
                JdbcUtilities.printSQLException(ex);
            }
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void populateTableBatch(int qtd) throws SQLException {
        connection.setAutoCommit(false);
        String query = "INSERT INTO user (username, password) VALUES(?, ?)";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            for(int i = 1; i <= qtd; i++) {
                pstmt.setString(1, "username" + i);
                pstmt.setString(2, "password" + i);
                pstmt.addBatch();
            }

            int[] updateCounts = pstmt.executeBatch();
            connection.commit();
        } catch (BatchUpdateException b) {
            JdbcUtilities.printBatchUpdateException(b);
        } catch (SQLException e) {
            JdbcUtilities.printSQLException(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void dropTable() {
        String query = "DROP TABLE IF EXISTS user";

        try(Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);

        } catch (SQLException e) {
            JdbcUtilities.printSQLException(e);
        }
    }

}
