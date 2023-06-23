package com.sprint.be_java_hisp_w21_g04.unitTest.service;

import com.sprint.be_java_hisp_w21_g04.dto.response.ResponseDto;
import com.sprint.be_java_hisp_w21_g04.entity.User;
import com.sprint.be_java_hisp_w21_g04.exception.UserNotFoundException;
import com.sprint.be_java_hisp_w21_g04.repository.user.IUserRepository;
import com.sprint.be_java_hisp_w21_g04.repository.user.UserRepositoryImpl;
import com.sprint.be_java_hisp_w21_g04.service.user.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class Test_T0001_T0002_Follow_Unfollow {

    @Mock
    UserRepositoryImpl userRepository;
    @InjectMocks
    UserServiceImpl userService;


    @Test
    @DisplayName("T0001 - Follow Ok")
    void followOk() {

        // Arrange
        List<Integer> followers = new ArrayList<>();
        List<Integer> followed = new ArrayList<>();

        User userfollower = new User(1, "JohnDoe", followers, followed);
        User userFollow = new User(2, "JaneSmith", followers, followed);

        ResponseDto expectResponseDto = new ResponseDto();
        expectResponseDto.setMessage("Has seguido a " + userFollow.getUserName());


        when(userRepository.findUserById(1)).thenReturn(userfollower);
        when(userRepository.findUserById(2)).thenReturn(userFollow);

        // Act
        ResponseDto result = userService.followUser(1, 2);

        // Assert
        assertEquals(expectResponseDto, result);
    }



    @Test
    @DisplayName("T0002 - Unfollow Ok")
    void unFollowOk() {

        // Arrange
        List<Integer> followers = new ArrayList<>();
        List<Integer> followed = new ArrayList<>();

        followers.add(1);
        followed.add(2);

        User userfollower = new User(1, "JohnDoe", followers, followed);
        User userFollow = new User(2, "JaneSmith", followers, followed);

        ResponseDto expectResponseDto = new ResponseDto();
        expectResponseDto.setMessage("Has dejado de seguir a " + userFollow.getUserName());


        when(userRepository.findUserById(1)).thenReturn(userfollower);
        when(userRepository.findUserById(2)).thenReturn(userFollow);

        // Act
        ResponseDto result = userService.unfollowUser(1, 2);

        // Assert
        assertEquals(expectResponseDto, result);
    }
    @Test
    @DisplayName("T0002 - Unfollow Fail UserNotFound")
    void unFollowUserNotFound() {

        when(userRepository.findUserById(1)).thenReturn(null);

        // Act and Assert
        assertThrows(UserNotFoundException.class,()-> userService.unfollowUser(1, 2));

    }


}
