package com.sprint.be_java_hisp_w21_g04.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserResponseDto {
    
    private int userId;
    private String userName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponseDto that = (UserResponseDto) o;
        return userId == that.userId && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName);
    }
}
