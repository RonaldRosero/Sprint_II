package com.sprint.be_java_hisp_w21_g04.integrationTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprint.be_java_hisp_w21_g04.dto.response.ErrorDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.ResponseDto;
import com.sprint.be_java_hisp_w21_g04.entity.User;
import com.sprint.be_java_hisp_w21_g04.exception.UserAlreadyFollowedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    ObjectWriter writer = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .writer();


    @Test
    void followTestIntegration() throws Exception {
        // Arrange

        List<Integer> followers = new ArrayList<>();
        List<Integer> followed = new ArrayList<>();

        User userfollower = new User(1, "JohnDoe", followers, followed);
        User userFollow = new User(2, "JaneSmith", followers, followed);

        //ResponseDto expectResponseDto = new ResponseDto("Has seguido a " + userFollow.getUserName());
        UserAlreadyFollowedException expectResponseDto = new UserAlreadyFollowedException("Ya se están siguiendo.");


        //String payloadJson = writer.writeValueAsString(houseRequest);
        String reponseJson = writer.writeValueAsString(expectResponseDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/users/{userId}/follow/{userIdToFollow}", 1, 2);

        // Expects
        ResultMatcher contentExpected = MockMvcResultMatchers.content().json(reponseJson);
        ResultMatcher statusExpected = MockMvcResultMatchers.status().isOk();
        ResultMatcher contentTypeExpected = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);



        MvcResult mvcResult = mockMvc.perform(request)
                .andDo(print())
                .andExpect(contentExpected)
                .andExpect(statusExpected)
                .andExpect(contentExpected) // verifica que coincida content type
                .andExpect(contentTypeExpected)
                .andReturn();

        assertEquals(reponseJson, mvcResult.getResponse().getContentAsString());

    }


    @Test
    void userAlreadyFollowedTestIntegration() throws Exception {
        // Arrange

        ErrorDto errorDto = new ErrorDto("Ya se estÃ¡n siguiendo.", 400);

        String errorExpected = writer.writeValueAsString(errorDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/users/{userId}/follow/{userIdToFollow}", 1, 2);

        // Expects
        ResultMatcher statusExpected = MockMvcResultMatchers.status().isBadRequest();
        ResultMatcher contentTypeExpected = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request)
                .andDo(print())
                .andExpect(statusExpected)
                .andExpect(contentTypeExpected)
                .andReturn();

        System.out.println(errorExpected);
        assertEquals(errorExpected,mvcResult.getResponse().getContentAsString());

    }
}
