package com.korit.korit_gov_servlet_study.ch03;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {
    private String title;
    private String content;
    private String username;

    public Todo toEntity() {
        return Todo.builder()
                .title(title)
                .content(content)
                .username(username)
                .build();
    }
}
