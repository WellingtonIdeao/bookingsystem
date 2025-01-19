package com.ideao.bookingsystem.dao;

import com.ideao.bookingsystem.model.User;
import com.ideao.bookingsystem.util.JdbcUtilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection connection;


    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public List<User> list() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, username, password FROM user";
        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            transformResultSetToUser(rs, users);
        } catch (SQLException e) {
            JdbcUtilities.printSQLException(e);
        }
        return users;
    }


    private void transformResultSetToUser(ResultSet rs, List<User> users) throws SQLException {
        while(rs.next()) {
            User user = new User( rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3));
            users.add(user);
        }
    }

    private void transformResultSetToUser(Statement stmt, List<User> users) {
        try(ResultSet rs = stmt.getResultSet()) {
            while(rs.next()) {
                User user = new User( rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
