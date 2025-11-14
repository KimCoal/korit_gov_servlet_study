package com.korit.korit_gov_servlet_study.ch07;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository instance;
    private List<User> users;
    private Integer setId = 1;

    private UserRepository() {
        users = new ArrayList<>();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User findByUsername(String username) {
        return users.stream()
                .filter(f -> f.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAll() {
        return users;
    }

    public User addUser(User user) {
        user.setUserId(setId++);
        users.add(user);
        return user; // ← null 말고 저장된 user 리턴
    }
}
