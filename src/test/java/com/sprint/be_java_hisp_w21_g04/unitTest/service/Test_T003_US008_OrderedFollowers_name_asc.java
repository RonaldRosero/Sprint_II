package com.sprint.be_java_hisp_w21_g04.unitTest.service;

import com.sprint.be_java_hisp_w21_g04.dto.response.FollowersResponseDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.UserResponseDto;
import com.sprint.be_java_hisp_w21_g04.entity.User;
import com.sprint.be_java_hisp_w21_g04.exception.IllegalDataException;
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
public class Test_T003_US008_OrderedFollowers_name_asc {

    @Mock
    UserRepositoryImpl userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("Test de validacion de orden invalido")
    public void test_T003_US003_OrderNoValid()  {

        // Arrange
        String order = "No valid";

        // Act and Assert
        assertThrows(IllegalDataException.class,()-> userService.getFollowersByIdSorted(1, order));
    }


    @Test
    @DisplayName("Test de validacion de orden ascendente por nombre")
    public void test_T003_US003_OrderOK() {
        // Datos de entrada
        int userId = 2;
        String order = "name_asc";

        // Mock de los seguidores
        List<Integer> followerIds = Arrays.asList(1, 3, 4);
        when(userRepository.getFollowersById(userId)).thenReturn(followerIds);

        // Mock de los usuarios

        List<Integer> followers = new ArrayList<>();
        List<Integer> followed = new ArrayList<>();

        User userRequest = new User(2, "MikeJohnson", followers, followed);
        User user1 = new User(1, "JohnDoe", followers, followed);
        User user2 = new User(4, "EmilyDavis", followers, followed);
        User user3 = new User(3, "DavidWilson", followers, followed);

        when(userRepository.getById(1)).thenReturn(user1);
        when(userRepository.getById(4)).thenReturn(user2);
        when(userRepository.getById(3)).thenReturn(user3);
        when(userRepository.getById(2)).thenReturn(userRequest);


        // Preparar respuesta esperada
        UserResponseDto user1Dto = new UserResponseDto(1, "JohnDoe");
        UserResponseDto user2Dto = new UserResponseDto(3, "DavidWilson");
        UserResponseDto user3Dto = new UserResponseDto(4, "EmilyDavis");

        List<UserResponseDto> followersResponse = Arrays.asList(user2Dto, user3Dto,user1Dto);

        FollowersResponseDto expectResponseDto = new FollowersResponseDto(userRequest.getUserId(), userRequest.getUserName(), followersResponse);

        // Act
        FollowersResponseDto result = userService.getFollowersByIdSorted(userId, order);

        // Assert
        List<UserResponseDto> followersResult = result.getFollowers();
        Assertions.assertEquals(3, followersResult.size());
        Assertions.assertEquals(followersResponse, followersResult);
        Assertions.assertEquals(expectResponseDto, result);

    }




}
