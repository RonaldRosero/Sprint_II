package com.sprint.be_java_hisp_w21_g04.dto.request;



import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {

    @NotNull(message = "La id no puede estar vacío")
    @Min(value = 1, message = "El id debe ser mayor a cero")
    private Integer productId;

    @NotBlank(message = "El campo no puede estar vacío")
    @Size(max = 40, message = "La longitud no puede superar los 40 caracteres")
    @Pattern(regexp = "[A-Za-zñóíáéúÁÓÉÍÚ&[0-9] ]*", message = "El campo no puede poseer caracteres especiales")
    private String productName;

    @NotBlank(message = "El campo no puede estar vacío")
    @Size(max = 15, message = "La longitud no puede superar los 15 caracteres")
    @Pattern(regexp = "[A-Za-zñóíáéúÁÓÉÍÚ&[0-9] ]*", message = "El campo no puede poseer caracteres especiales")
    private String type;

    @NotBlank(message = "El campo no puede estar vacío")
    @Size(max = 25, message = "La longitud no puede superar los 25 caracteres")
    @Pattern(regexp = "[A-Za-zñóíáéúÁÓÉÍÚ&[0-9] ]*", message = "El campo no puede poseer caracteres especiales")
    private String brand;

    @NotBlank(message = "El campo no puede estar vació")
    @Size(max = 15, message = "La longitud no puede superar los 15 caracteres")
    @Pattern(regexp = "[A-Za-zñóíáéúÁÓÉÍÚ&[0-9] ]*", message = "El campo no puede poseer caracteres especiales")
    private String color;

    @Size(max = 80, message = "La longitud no puede superar los 80 caracteres")
    @Pattern(regexp = "[A-Za-zñóíáéúÁÓÉÍÚ&[0-9] ]*", message = "El campo no puede poseer caracteres especiales")
    private String notes;

}