package com.sprint.be_java_hisp_w21_g04.integrationTest.PostController;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sprint.be_java_hisp_w21_g04.dto.request.PostRequestDto;
import com.sprint.be_java_hisp_w21_g04.dto.request.ProductDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.ErrorDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.ResponseDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.UserNotFoundDto;
import com.sprint.be_java_hisp_w21_g04.entity.Post;
import com.sprint.be_java_hisp_w21_g04.entity.Product;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class PostIntegrationTest {

    private String url = "/products/post";
    @Autowired
    MockMvc mockMvc;

    ObjectWriter writer = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .writer();


    @Test
    void postTestIntegrationOk() throws Exception {
        // Arrange
        LocalDate currentDate = LocalDate.now();
        ProductDto productDto =  new ProductDto(1, "Camiseta", "Ropa", "Adidas",
                "Rojo con blanco", "Camiseta de algodón con estampado");

        PostRequestDto postRequest = new PostRequestDto(2, currentDate.minusDays(5), productDto, 1, 10.0);

        ResponseDto expectResponseDto = new ResponseDto("Post agregado exitosamente");

        String payloadJson = writer.writeValueAsString(postRequest);
        String reponseJson = writer.writeValueAsString(expectResponseDto);

        // Expects
        ResultMatcher contentExpected = MockMvcResultMatchers.content().json(reponseJson);
        ResultMatcher statusExpected = MockMvcResultMatchers.status().isOk();
        ResultMatcher contentTypeExpected = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print())
                .andExpect(contentExpected)
                .andExpect(statusExpected)
                .andExpect(contentTypeExpected)
                .andReturn();

        assertEquals(reponseJson, mvcResult.getResponse().getContentAsString());
    }




    @Test
    void postUserNotFoundTestIntegration() throws Exception {
        // Arrange

        LocalDate currentDate = LocalDate.now();
        ProductDto productDto =  new ProductDto(1, "Camiseta", "Ropa", "Adidas",
                "Rojo con blanco", "Camiseta de algodón con estampado");

        PostRequestDto postRequest = new PostRequestDto(110, currentDate.minusDays(5), productDto, 1, 10.0);

        UserNotFoundDto userNotFoundDto = new UserNotFoundDto("Usuario no encontrado.", 404);

        String payloadJson = writer.writeValueAsString(postRequest);
        String errorExpected = writer.writeValueAsString(userNotFoundDto);


        // Expects
        ResultMatcher statusExpected = MockMvcResultMatchers.status().isNotFound();
        ResultMatcher contentTypeExpected = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
                .andDo(print())
                .andExpect(statusExpected)
                .andExpect(contentTypeExpected)
                .andReturn();

        assertEquals(errorExpected,mvcResult.getResponse().getContentAsString());
    }

}
