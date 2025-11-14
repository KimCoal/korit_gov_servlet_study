package com.korit.korit_gov_servlet_study.ch07;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String email;
}
