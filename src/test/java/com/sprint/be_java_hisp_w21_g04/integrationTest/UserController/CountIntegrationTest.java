package com.sprint.be_java_hisp_w21_g04.integrationTest.UserController;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprint.be_java_hisp_w21_g04.dto.response.ErrorDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.ResponseDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.UserFollowersCountDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.UserNotFoundDto;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class CountIntegrationTest {

    private String url = "/users/{userId}/followers/count";
    @Autowired
    MockMvc mockMvc;

    ObjectWriter writer = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .writer();


    @Test
    void countIntegrationTest() throws Exception {
        // Arrange
        UserFollowersCountDto expectResponseDto = new UserFollowersCountDto(9, "EthanAnderson", 0);
        String reponseJson = writer.writeValueAsString(expectResponseDto);

        // Request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( url, 9);

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
