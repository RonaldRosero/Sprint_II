package com.sprint.be_java_hisp_w21_g04.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {


    private Integer productId;
    private String productName;
    private String type;
    private String brand;
    private String color;
    private String notes;

}
