package dao;

import model.User;

import java.sql.*;


public class UserDAO {
    public User getUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connect = DBConnection.getConnection();
        PreparedStatement pretart = connect.prepareStatement(sql)){
            pretart.setString(1, username);
            pretart.setString(2, password);
            ResultSet rs = pretart.executeQuery();
            if (rs.next()){
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            }
        }
        return null;
    }
}