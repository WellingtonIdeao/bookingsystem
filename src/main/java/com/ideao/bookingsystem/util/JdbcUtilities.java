package com.ideao.bookingsystem.util;

import com.ideao.bookingsystem.migrations.BookingTable;
import com.ideao.bookingsystem.migrations.GuestTable;
import com.ideao.bookingsystem.migrations.UserTable;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtilities {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection( "jdbc:mysql://localhost:3306/booking", "root", "password");
    }

    public static void initializeTables(Connection connection) throws SQLException {
        UserTable userTable = new UserTable(connection);
        GuestTable guestTable = new GuestTable(connection);
        BookingTable bookingTable = new BookingTable(connection);
        System.out.println("Dropping existing USER AND GUEST table...");
        guestTable.dropTable();
        bookingTable.dropTable();
        userTable.dropTable();
        guestTable.dropTable();



        System.out.println("Creating USER, GUEST and BOOKING table...");
        userTable.createTable();
        bookingTable.createTable();
        guestTable.createTable();

        System.out.println("Populating USER table...");
        userTable.populateTableBatch(10);

    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (ignoreSQLException(((SQLException)e).getSQLState()) == false) {
                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " + ((SQLException)e).getSQLState());
                    System.err.println("Error Code: " + ((SQLException)e).getErrorCode());
                    System.err.println("Message: " + e.getMessage());
                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }
    public static boolean ignoreSQLException(String sqlState) {
        if (sqlState == null) {
            System.out.println("The SQL state is not defined!");
            return false;
        }
        // X0Y32: Jar file already exists in schema
        if (sqlState.equalsIgnoreCase("X0Y32"))
            return true;
        // 42Y55: Table already exists in schema
        if (sqlState.equalsIgnoreCase("42S02"))
            return true;
        return false;
    }

    public static void printBatchUpdateException(BatchUpdateException b) {
        System.err.println("----BatchUpdateException----");
        System.err.println("SQLState:  " + b.getSQLState());
        System.err.println("Message:  " + b.getMessage());
        System.err.println("Vendor:  " + b.getErrorCode());
        System.err.print("Update counts:  ");
        int[] updateCounts = b.getUpdateCounts();
        for (int i = 0; i < updateCounts.length; i++) {
            System.err.print(updateCounts[i] + "   ");
        }
    }
}
