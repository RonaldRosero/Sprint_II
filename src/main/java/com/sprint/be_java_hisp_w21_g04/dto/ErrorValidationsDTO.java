package com.sprint.be_java_hisp_w21_g04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorValidationsDTO {

    String explanation;

    List<String> messages;

}
