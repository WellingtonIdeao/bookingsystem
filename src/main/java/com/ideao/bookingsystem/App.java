package com.ideao.bookingsystem;

import com.ideao.bookingsystem.dao.UserDao;
import com.ideao.bookingsystem.util.JdbcUtilities;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        try(Connection connection = JdbcUtilities.getConnection()){
            System.out.println("Connected with database");
            JdbcUtilities.initializeTables(connection);
//            UserDao dao = new UserDao(connection);
//            System.out.println(dao.list());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Desconnected with database");

    }
}
