package com.sprint.be_java_hisp_w21_g04.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Post {

    private int userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    private Product product;
    private int category;
    private double price;
    private boolean hasPromo;
    private double discount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return userId == post.userId && category == post.category && Double.compare(post.price, price) == 0 && hasPromo == post.hasPromo && Double.compare(post.discount, discount) == 0 && Objects.equals(date, post.date) && Objects.equals(product, post.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, date, product, category, price, hasPromo, discount);
    }
}
