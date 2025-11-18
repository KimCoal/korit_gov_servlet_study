package com.korit.korit_gov_servlet_study.ch08.user.service;

import com.korit.korit_gov_servlet_study.ch08.user.dao.UserDao;
import com.korit.korit_gov_servlet_study.ch08.user.dto.SignUpReqDto;
import com.korit.korit_gov_servlet_study.ch08.user.entity.User;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

public class UserService {
    private static UserService instance;
    private UserDao userDao;

    private UserService () {
        userDao = UserDao.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public boolean isDuplicatedUsername(String username) {
        Optional<User> foundUser = userDao.findByUsername(username);
        return foundUser.isPresent();
    }

    public User addUser(SignUpReqDto signUpReqDto) {
        return userDao.addUser(signUpReqDto.toEntity());
    }

    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public Optional<List<User>> findByKeyword(String keyword) {
        return userDao.findByKeyword(keyword);
    }

    public Optional<List<User>> getAllUser() {
        return userDao.getAllUserList();
    }
}
