package com.ideao.bookingsystem.migrations;

import com.ideao.bookingsystem.util.JdbcUtilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class GuestTable {
    private Connection connection;

    public GuestTable(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS guest (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "surname VARCHAR(255) NOT NULL, " +
                "birth_date DATE, " +
                "nationality VARCHAR(255), " +
                "phone VARCHAR(255), " +
                "PRIMARY KEY (id)" +
                ")";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.execute();
        } catch (SQLException e) {
            JdbcUtilities.printSQLException(e);
        }
    }

    public void dropTable() {
        String query = "DROP TABLE IF EXISTS guest";
        try(Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            JdbcUtilities.printSQLException(e);
        }
    }
}
