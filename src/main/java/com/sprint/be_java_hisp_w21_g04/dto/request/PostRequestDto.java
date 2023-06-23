package com.sprint.be_java_hisp_w21_g04.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostRequestDto {
     @NotNull(message = "El id no puede estar vacío")
     @Min(value = 1, message = "El id debe ser mayor a cero")
     private Integer userId;
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
     @NotNull(message = "La fecha no puede estar vacía")
     private LocalDate date;

     @NotNull(message = "Producto no puede estar vacío")
     @Valid
     private ProductDto product;

     @NotNull(message = "El campo no puede estar vacío")
     @Min(value = 1, message = "La categoria debe ser mayor a cero")
     private Integer category;
     @NotNull(message = "El campo no puede estar vació")
     @Max(value = 10000000, message = "El precio maximo por producto es de 10.000.000")
     @Min(value = 1, message = "El precio minimo por producto es de 1")
     private Double price;
}
