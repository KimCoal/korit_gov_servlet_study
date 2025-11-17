package com.korit.korit_gov_servlet_study.ch08.user.dto;

import com.korit.korit_gov_servlet_study.ch08.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReqDto {
    private String username;
    private String password;
    private Integer age;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .age(age)
                .build();
    }
}
