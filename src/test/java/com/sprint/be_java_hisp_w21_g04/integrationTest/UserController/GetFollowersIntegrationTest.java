package com.sprint.be_java_hisp_w21_g04.integrationTest.UserController;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprint.be_java_hisp_w21_g04.dto.response.FollowersResponseDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.UserFollowersCountDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.UserResponseDto;
import com.sprint.be_java_hisp_w21_g04.entity.User;
import com.sprint.be_java_hisp_w21_g04.repository.user.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class GetFollowersIntegrationTest {

    private String url = "/users/{userId}/followers/list";

    @Autowired
    MockMvc mockMvc;

    ObjectWriter writer = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .writer();



    @Test
    void GetFollowersOk() throws Exception {
        // Arrange

        // Preparar respuesta esperada

        UserResponseDto userFollower = new UserResponseDto(4, "EmilyDavis");
        List<UserResponseDto> followersResponse = Arrays.asList(userFollower);
        FollowersResponseDto expectResponseDto = new FollowersResponseDto(3, "MikeJohnson", followersResponse);


        String reponseJson = writer.writeValueAsString(expectResponseDto);

        // Request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( url, 3);

        // Expects
        ResultMatcher contentExpected = MockMvcResultMatchers.content().json(reponseJson);
        ResultMatcher statusExpected = MockMvcResultMatchers.status().isOk();
        ResultMatcher contentTypeExpected = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request) // ejecutar la request
               .andExpect(contentExpected) // verifica que coincida body
                .andExpect(statusExpected) // verifica que coincida status
                .andExpect(contentTypeExpected) // verifica que coincida content type
                .andDo(MockMvcResultHandlers.print()); // Permite ver en consola la request
    }


    @Test
    void GetFollowersByIdSortedOk() throws Exception {
        // Arrange

        // Preparar respuesta esperada

        UserResponseDto userFollower1 = new UserResponseDto(7, "JacobBrown");
        UserResponseDto userFollower2 = new UserResponseDto(4, "EmilyDavis");
        List<UserResponseDto> followersResponse = Arrays.asList(userFollower1, userFollower2);
        FollowersResponseDto expectResponseDto = new FollowersResponseDto(6, "SophiaLee", followersResponse);


        String reponseJson = writer.writeValueAsString(expectResponseDto);

        // Request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( url + "?order=name_asc", 6);

        // Expects
        ResultMatcher contentExpected = MockMvcResultMatchers.content().json(reponseJson);
        ResultMatcher statusExpected = MockMvcResultMatchers.status().isOk();
        ResultMatcher contentTypeExpected = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request) // ejecutar la request
                .andExpect(contentExpected) // verifica que coincida body
                .andExpect(statusExpected) // verifica que coincida status
                .andExpect(contentTypeExpected) // verifica que coincida content type
                .andDo(MockMvcResultHandlers.print()); // Permite ver en consola la request
    }


}
