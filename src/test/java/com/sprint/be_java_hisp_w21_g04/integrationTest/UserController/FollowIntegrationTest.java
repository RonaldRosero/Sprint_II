package com.sprint.be_java_hisp_w21_g04.integrationTest.UserController;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprint.be_java_hisp_w21_g04.dto.response.ErrorDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.ResponseDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.UserNotFoundDto;
import com.sprint.be_java_hisp_w21_g04.entity.User;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class FollowIntegrationTest {

    private String url = "/users/{userId}/follow/{userIdToFollow}";
    @Autowired
    MockMvc mockMvc;

    ObjectWriter writer = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .writer();


    @Test
    void followTestIntegration() throws Exception {
        // Arrange

        ResponseDto expectResponseDto = new ResponseDto("Has seguido a " + "JaneSmith");

        String reponseJson = writer.writeValueAsString(expectResponseDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url, 8, 2);

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

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url, 1, 2);

        // Expects
        ResultMatcher statusExpected = MockMvcResultMatchers.status().isBadRequest();
        ResultMatcher contentTypeExpected = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request)
                .andDo(print())
                .andExpect(statusExpected)
                .andExpect(contentTypeExpected)
                .andReturn();

        assertEquals(errorExpected,mvcResult.getResponse().getContentAsString());
    }

    @Test
    void userNotFoundTestIntegration() throws Exception {
        // Arrange

        UserNotFoundDto userNotFoundDto = new UserNotFoundDto("Usuario no encontrado.", 404);

        String errorExpected = writer.writeValueAsString(userNotFoundDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url, 110, 2);

        // Expects
        ResultMatcher statusExpected = MockMvcResultMatchers.status().isNotFound();
        ResultMatcher contentTypeExpected = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request)
                .andDo(print())
                .andExpect(statusExpected)
                .andExpect(contentTypeExpected)
                .andReturn();

        assertEquals(errorExpected,mvcResult.getResponse().getContentAsString());
    }

    @Test
    void UserFollowNotAllowedTestIntegration() throws Exception {
        // Arrange

        ErrorDto errorDto = new ErrorDto("No puedes seguirte a ti mismo.", 400);

        String errorExpected = writer.writeValueAsString(errorDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url, 2, 2);

        // Expects
        ResultMatcher statusExpected = MockMvcResultMatchers.status().isBadRequest();
        ResultMatcher contentTypeExpected = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request)
                .andDo(print())
                .andExpect(statusExpected)
                .andExpect(contentTypeExpected)
                .andReturn();

        assertEquals(errorExpected,mvcResult.getResponse().getContentAsString());
    }
}
