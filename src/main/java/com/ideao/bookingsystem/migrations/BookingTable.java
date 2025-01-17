package com.ideao.bookingsystem.migrations;

import com.ideao.bookingsystem.util.JdbcUtilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BookingTable {

    private Connection connection;

    public BookingTable(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS booking ( " +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "entry_date DATE, " +
                "departure_date DATE, " +
                "value DECIMAL, " +
                "payment_method VARCHAR(255), " +
                "PRIMARY KEY (id)" +
                ")";
        try(Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            JdbcUtilities.printSQLException(e);
        }
    }

    public void dropTable() {
        String query = "DROP TABLE IF EXISTS booking";
        try(Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            JdbcUtilities.printSQLException(e);
        }
    }

}
