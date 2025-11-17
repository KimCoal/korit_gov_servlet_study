package com.korit.korit_gov_servlet_study.ch08.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiRespDto<T> {
    private String status;
    private String message;
    private T body;
}
