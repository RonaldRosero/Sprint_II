package com.sprint.be_java_hisp_w21_g04.unitTest.service;

import com.sprint.be_java_hisp_w21_g04.dto.response.PostResponseDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.SellerFollowedListPostResponseDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.UserFollowersCountDto;
import com.sprint.be_java_hisp_w21_g04.entity.Post;
import com.sprint.be_java_hisp_w21_g04.entity.Product;
import com.sprint.be_java_hisp_w21_g04.entity.User;
import com.sprint.be_java_hisp_w21_g04.exception.UserNotFoundException;
import com.sprint.be_java_hisp_w21_g04.repository.post.IPostRepository;
import com.sprint.be_java_hisp_w21_g04.repository.user.IUserRepository;
import com.sprint.be_java_hisp_w21_g04.repository.user.UserRepositoryImpl;
import com.sprint.be_java_hisp_w21_g04.service.post.PostServiceImpl;
import com.sprint.be_java_hisp_w21_g04.service.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class Test_T008_US_0006 {


    @Mock
    IUserRepository userRepository;

    @Mock
    IPostRepository postRepository;

    @InjectMocks
    PostServiceImpl postService;

    @Test
    @DisplayName("T0008 - Post order date date_desc")
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

        // Post con más de dos semanas de publicado, no se sera publicado por el servicio
        Post post1 = new Post(2, twoWeeksAgo.minusDays(20), product2, 2, 20.0, false, 0.0);
        Post post2 = new Post(2,currentDate.minusDays(5), product1, 1, 10.0, false, 0.0);
        Post post3 = new Post(2, twoWeeksAgo.plusDays(3), product2, 2, 20.0, false, 0.0);


        List<Post> userPosts = Arrays.asList(post1, post2, post3);


        // Mock de los posts, el repositorio devuelve 3 post
        when(postRepository.getSellerFollowed(userId)).thenReturn(userPosts);

        // Preparar respuesta esperada

        // Se esperan solo dos post por parte del servicio
        PostResponseDto postResponseDto1 = new PostResponseDto(userId, currentDate.minusDays(5), product1, 1, 10.0);
        PostResponseDto postResponseDto2 = new PostResponseDto(userId, twoWeeksAgo.plusDays(3), product2, 2, 20.0);

        List<PostResponseDto> postResponseDto = Arrays.asList(postResponseDto1, postResponseDto2);

        SellerFollowedListPostResponseDto expectResponseDto = new SellerFollowedListPostResponseDto(userId,postResponseDto);


        // Act
        // El servicio devuelve solo 2 post que fueron publicados en las ultimas 2 semanas
        SellerFollowedListPostResponseDto result = postService.sellerFollowedListPosts(userId, orderDate);


        // Assert
        List<PostResponseDto>  postsResult = result.getPosts();
        Assertions.assertEquals(2, postsResult.size());
        Assertions.assertEquals(postResponseDto, postsResult);
        Assertions.assertEquals(expectResponseDto, result);

    }



}
