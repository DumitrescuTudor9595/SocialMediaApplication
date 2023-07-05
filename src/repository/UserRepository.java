package repository;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class UserRepository {

    public ArrayList<User> readAll() {
        ArrayList<User> users = new ArrayList<>();
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM user_table");
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public User readById(int id) {
        User user = null;
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery("SELECT * FROM user_table WHERE id = " + id);
            rs.next();
            user = extractUser(rs);
        } catch (SQLException e) {
        }
        return user;
    }

    public boolean create(String firstName, String lastName, String email, String phoneNumber) {
        int affectedRows = 0;
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "INSERT INTO user_table (first_name,last_name,email,phone_number)" +
                    " VALUES ('%s','%s','%s','%s')";
            affectedRows = st.executeUpdate(String.format(template, firstName, lastName, email, phoneNumber));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return affectedRows > 0;
    }

    public boolean update(int userId, String firstName, String lastName, String email, String phoneNumber) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            String template = "UPDATE user_table set first_name = '%s', last_name = '%s', " +
                    "email = '%s', phone_number = '%s' WHERE id = %d";
            st.executeUpdate(String.format(template, firstName, lastName, email, phoneNumber, userId));

        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean delete(int userId) {
        boolean userWasDeleted;
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            int affectedRows = st.executeUpdate("DELETE FROM user_table WHERE id = " + userId);
            userWasDeleted= affectedRows>0;
        } catch (SQLException e) {
            userWasDeleted=false;
        }
        return userWasDeleted;
    }


    private User extractUser(ResultSet rs) {
        User user = null;
        try {
            int userId = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            String phone_number = rs.getString("phone_number");
            user = new User(userId, firstName, lastName, email, phone_number,new ArrayList<>());
        } catch (SQLException e) {
        }

        return user;
    }
}
