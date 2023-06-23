package com.sprint.be_java_hisp_w21_g04.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SellerFollowedListPostResponseDto {
    private int userId;
    private List<PostResponseDto> posts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellerFollowedListPostResponseDto that = (SellerFollowedListPostResponseDto) o;
        return userId == that.userId && Objects.equals(posts, that.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, posts);
    }
}
