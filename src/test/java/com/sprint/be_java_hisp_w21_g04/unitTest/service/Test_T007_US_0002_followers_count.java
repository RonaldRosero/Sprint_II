package com.sprint.be_java_hisp_w21_g04.unitTest.service;

import com.sprint.be_java_hisp_w21_g04.dto.response.PostResponseDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.UserFollowersCountDto;
import com.sprint.be_java_hisp_w21_g04.entity.User;
import com.sprint.be_java_hisp_w21_g04.exception.UserNotFoundException;
import com.sprint.be_java_hisp_w21_g04.repository.user.UserRepositoryImpl;
import com.sprint.be_java_hisp_w21_g04.service.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class Test_T007_US_0002_followers_count {


    @Mock
    UserRepositoryImpl userRepository;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("T0007 - UserNotFound")
    void userNotFound() {

        when(userRepository.findUserById(1)).thenReturn(null);
        // Act and Assert
        assertThrows(UserNotFoundException.class,()-> userService.getFollowersCount(1));
    }

    @Test
    @DisplayName("T0007 - count followers ok")
    void userFollowersCountOk() {

        // Arrange
        Integer userId = 2;

        List<Integer> followers = Arrays.asList(2, 3, 4, 5);
        List<Integer> followed = new ArrayList<>();

        User userRequest = new User(2, "MikeJohnson", followers, followed);

        // Mock User
        when(userRepository.findUserById(userId)).thenReturn(userRequest);

        // Expect results
        UserFollowersCountDto expectResponseDto = new UserFollowersCountDto(userId,userRequest.getUserName(), followers.size());

        // Act
        UserFollowersCountDto result = userService.getFollowersCount(userId);

        // Assert
        Assertions.assertEquals(4, result.getFollowersCount());
        Assertions.assertEquals(expectResponseDto, result);

    }



}
