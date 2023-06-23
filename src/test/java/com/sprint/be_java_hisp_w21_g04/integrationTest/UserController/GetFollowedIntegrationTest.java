package com.sprint.be_java_hisp_w21_g04.integrationTest.UserController;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprint.be_java_hisp_w21_g04.dto.response.ErrorDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.FollowedResponseDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.FollowersResponseDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.UserResponseDto;
import com.sprint.be_java_hisp_w21_g04.exception.IllegalDataException;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class GetFollowedIntegrationTest {

    private String url = "/users/{userId}/followed/list";

    @Autowired
    MockMvc mockMvc;

    ObjectWriter writer = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .writer();



    @Test
    void GetFollowedOk() throws Exception {
        // Arrange

        // Preparar respuesta esperada
        UserResponseDto userFollowed1 = new UserResponseDto( 3, "MikeJohnson");
        UserResponseDto userFollowed2 = new UserResponseDto( 5, "DavidWilson");
        List<UserResponseDto> followedResponse = Arrays.asList(userFollowed1, userFollowed2);
        FollowedResponseDto expectResponseDto = new FollowedResponseDto( 4, "EmilyDavis", followedResponse);


        String reponseJson = writer.writeValueAsString(expectResponseDto);

        // Request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( url, 4);

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
    void GetFollowedByIdSortedOk() throws Exception {
        // Arrange

        // Preparar respuesta esperada

        UserResponseDto userFollowed1 = new UserResponseDto( 5, "DavidWilson");
        UserResponseDto userFollowed2 = new UserResponseDto( 3, "MikeJohnson");
        List<UserResponseDto> followedResponse = Arrays.asList(userFollowed1, userFollowed2);
        FollowedResponseDto expectResponseDto = new FollowedResponseDto( 4, "EmilyDavis", followedResponse);

        String reponseJson = writer.writeValueAsString(expectResponseDto);
        // Request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( url +"?order=name_asc", 4);

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
    void GetFollowedOrderInvalid() throws Exception {
        // Arrange

        // Preparar respuesta esperada
        ErrorDto errorDto = new ErrorDto("Ordenamiento invalido",400);

        String errorExpected = writer.writeValueAsString(errorDto);

        // Request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( url + "?order=not_valid", 4);

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
