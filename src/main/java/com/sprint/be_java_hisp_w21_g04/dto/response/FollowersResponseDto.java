package com.sprint.be_java_hisp_w21_g04.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FollowersResponseDto {

    private int userId;
    private String userName;
    private List<UserResponseDto> followers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowersResponseDto that = (FollowersResponseDto) o;
        return userId == that.userId && Objects.equals(userName, that.userName) && Objects.equals(followers, that.followers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, followers);
    }
}
