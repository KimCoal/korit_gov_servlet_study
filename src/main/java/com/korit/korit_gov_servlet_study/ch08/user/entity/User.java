package com.korit.korit_gov_servlet_study.ch08.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long userId;
    private String username;
    private String password;
    private Integer age;
    private LocalDateTime createDt;
}
