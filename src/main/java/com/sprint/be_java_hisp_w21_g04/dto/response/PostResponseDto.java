package com.sprint.be_java_hisp_w21_g04.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sprint.be_java_hisp_w21_g04.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostResponseDto {
    private int userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    private Product product;
    private int category;
    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostResponseDto that = (PostResponseDto) o;
        return userId == that.userId && category == that.category && Double.compare(that.price, price) == 0 && Objects.equals(date, that.date) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, date, product, category, price);
    }
}
