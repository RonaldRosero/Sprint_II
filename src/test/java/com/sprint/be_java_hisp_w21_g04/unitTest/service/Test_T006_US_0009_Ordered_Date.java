package com.sprint.be_java_hisp_w21_g04.unitTest.service;

import com.sprint.be_java_hisp_w21_g04.dto.response.PostResponseDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.SellerFollowedListPostResponseDto;
import com.sprint.be_java_hisp_w21_g04.entity.Post;
import com.sprint.be_java_hisp_w21_g04.entity.Product;
import com.sprint.be_java_hisp_w21_g04.entity.User;
import com.sprint.be_java_hisp_w21_g04.exception.UserNotFoundException;
import com.sprint.be_java_hisp_w21_g04.repository.post.IPostRepository;
import com.sprint.be_java_hisp_w21_g04.repository.user.IUserRepository;
import com.sprint.be_java_hisp_w21_g04.service.post.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class Test_T006_US_0009_Ordered_Date {


    @Mock
    IUserRepository userRepository;

    @Mock
    IPostRepository postRepository;

    @InjectMocks
    PostServiceImpl postService;
    @Test
    @DisplayName("T0006 - UserNotFound")
    void userNotFound() {


        when(userRepository.findUserById(1)).thenReturn(null);

        // Act and Assert
        assertThrows(UserNotFoundException.class,()-> postService.sellerFollowedListPosts(1, "date_asc"));

    }

    @Test
    @DisplayName("T0006 - Post order date date_desc")
    void orderDate() {

        // Arrange
        Integer userId = 2;
        String orderDate = "date_desc";

        List<Integer> followers = new ArrayList<>();
        List<Integer> followed = new ArrayList<>();

        User userRequest = new User(2, "MikeJohnson", followers, followed);

        when(userRepository.findUserById(userId)).thenReturn(userRequest);

       // Configurar la lista de publicaciones simuladas
        LocalDate currentDate = LocalDate.now();
        LocalDate twoWeeksAgo = currentDate.minusWeeks(2);

        Product product1 =  new Product(1, "Camiseta", "Ropa", "Adidas",
                "Rojo con blanco", "Camiseta de algodón con estampado");

        Product product2 = new Product(2, "Pantalón vaquero", "Ropa", "Levi's",
        "Azul", "Pantalón vaquero ajustado para hombre");

        Post post1 = new Post(2,currentDate.minusDays(5), product1, 1, 10.0, false, 0.0);
        Post post2 = new Post(2, twoWeeksAgo.plusDays(3), product2, 2, 20.0, false, 0.0);

        List<Post> uerPosts = new ArrayList<>();
        uerPosts.add(post1);
        uerPosts.add(post2);

        when(postRepository.getSellerFollowed(userId)).thenReturn(uerPosts);

        // Preparar respuesta esperada

        PostResponseDto postResponseDto1 = new PostResponseDto(userId, currentDate.minusDays(5), product1, 1, 10.0);
        PostResponseDto postResponseDto2 = new PostResponseDto(userId, twoWeeksAgo.plusDays(3), product2, 2, 20.0);

        List<PostResponseDto> postResponseDto = new ArrayList<>();
        postResponseDto.add(postResponseDto1);
        postResponseDto.add(postResponseDto2);
        SellerFollowedListPostResponseDto expectResponseDto = new SellerFollowedListPostResponseDto(userId,postResponseDto);


        // Act
        SellerFollowedListPostResponseDto result = postService.sellerFollowedListPosts(userId, orderDate);


        // Assert
        List<PostResponseDto>  postsResult = result.getPosts();
        Assertions.assertEquals(2, postsResult.size());
        Assertions.assertEquals(postResponseDto, postsResult);
        Assertions.assertEquals(expectResponseDto, result);

    }



}
