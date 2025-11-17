package com.korit.korit_gov_servlet_study.ch08.user.dao;

import com.korit.korit_gov_servlet_study.ch08.user.dto.SignUpReqDto;
import com.korit.korit_gov_servlet_study.ch08.user.entity.User;
import com.korit.korit_gov_servlet_study.ch08.user.util.ConnectionFactory;

import java.sql.*;
import java.util.Optional;

public class UserDao {
    private static UserDao instance;

    private UserDao() {}

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    public User addUser(User user) {
        String sql = "INSERT INTO user_tb(username, password, age, create_dt) VALUES (?,?,?,now())";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getAge());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setUserId(rs.getLong(1));
                }
            }

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM user_tb WHERE username = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(toUser(rs)) : Optional.empty();
            }

        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public User toUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .userId(resultSet.getLong("user_id"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .age(resultSet.getInt("age"))
                .createDt(resultSet.getTimestamp("create_dt").toLocalDateTime())
                .build();
    }
}
