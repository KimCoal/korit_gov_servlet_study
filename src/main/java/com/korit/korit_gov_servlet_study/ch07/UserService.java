package com.korit.korit_gov_servlet_study.ch07;

import java.util.List;

public class UserService {
    private static UserService instance;
    private final UserRepository userRepository;

    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService(UserRepository.getInstance());
        }
        return instance;
    }

    // username 중복 체크
    public boolean isDuplicatedUsername(String username) {
        return userRepository.findByUsername(username) != null;
    }

    // 회원 추가
    public User signUp(SignUpReqDto dto) {
        User user = dto.toEntity();
        return userRepository.addUser(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.getAll();
    }
}
