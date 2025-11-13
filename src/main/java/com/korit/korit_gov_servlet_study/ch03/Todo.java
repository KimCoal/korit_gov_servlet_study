package com.korit.korit_gov_servlet_study.ch03;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Todo {
    private Integer todoId;
    private String title;
    private String content;
    private String username;
}
