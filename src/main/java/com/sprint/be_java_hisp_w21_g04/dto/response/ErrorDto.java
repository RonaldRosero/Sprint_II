package com.sprint.be_java_hisp_w21_g04.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ErrorDto {
    private String message;
    private int status;
}
