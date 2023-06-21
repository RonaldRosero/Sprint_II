package com.sprint.be_java_hisp_w21_g04.service.post;

import com.sprint.be_java_hisp_w21_g04.dto.request.PostRequestDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.PostResponseDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.ResponseDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.SellerFollowedListPostResponseDto;
import com.sprint.be_java_hisp_w21_g04.entity.Post;
import com.sprint.be_java_hisp_w21_g04.entity.User;
import com.sprint.be_java_hisp_w21_g04.exception.EmptySellerFollowedList;
import com.sprint.be_java_hisp_w21_g04.exception.UserNotFoundException;
import com.sprint.be_java_hisp_w21_g04.repository.post.IPostRepository;
import com.sprint.be_java_hisp_w21_g04.repository.user.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements IPostService{
    private IPostRepository _postRepository;
    private final IUserRepository _userRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(IPostRepository repository, IUserRepository userRepository){
        this._postRepository = repository;
        this._userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }
    @Override
    public ResponseDto post(PostRequestDto post) {
 //     Validar que exista el user que crea el post
        User user = _userRepository.findUserById(post.getUserId());
        if (user == null ) {
            throw new UserNotFoundException("Usuario no encontrado.");
        }

        Post newPost = modelMapper.map(post, Post.class);

        if (this._postRepository.post(newPost)) {
            return new ResponseDto("Post agregado exitosamente");
        } else {
            return new ResponseDto("Error al agregar el post");
        }
    }

    @Override
    public List<PostResponseDto> getAll() {
        return this._postRepository.getAll().stream().map(post -> modelMapper.map(post, PostResponseDto.class)).toList();
    }
    

//    metodo sobrecargado para prueba
    public SellerFollowedListPostResponseDto sellerFollowedListPosts(int userId, String order) {
 //     Validar que exista el usuario que consulta
        User user = _userRepository.findUserById(userId);
        if (user == null ) {
            throw new UserNotFoundException("Usuario no encontrado.");
        }
//      Se define el tiempo de publicacion de posts de las ultimas dos semanas
//      Se define una fecha limite/base de dos semanas hacia atras desde la fecha actual
        LocalDate twoWeeksAgo = LocalDate.now().minusWeeks(2);
        if(this._postRepository.getSellerFollowed(userId).isEmpty()) throw new EmptySellerFollowedList("Los vendedores que sigues no tienen publicaciones");
        List<PostResponseDto> posts = this._postRepository.getSellerFollowed(userId).stream()
                .filter(post -> {
                    LocalDate postDate = post.getDate();
//                  Se filtran los posts que tengan una fecha de publicacion mayor o igual a dos semanas
                    return postDate.isAfter(twoWeeksAgo) || postDate.isEqual(twoWeeksAgo);
                })
//              Se mapea la lista de posts a una lista de PostResponseDto
                .map(post -> modelMapper.map(post, PostResponseDto.class))
//              Se ordena la lista de posts por fecha de publicacion
                .sorted((post1, post2) -> {
                    LocalDate date1 = post1.getDate();
                    LocalDate date2 = post2.getDate();

//                  Se ordena la lista de posts por fecha de publicacion de forma ascendente o descendente
                    if ("date_asc".equals(order)) {
                        return date1.compareTo(date2);
                    } else if ("date_desc".equals(order)) {
                        return date2.compareTo(date1);
                    } else {
                        return 0;
                    }
                })
//               Se convierte la lista a un ArrayList
                .collect(Collectors.toList());
//        System.out.println("HOLA" + posts.size());
        if(posts.isEmpty()) throw new EmptySellerFollowedList("Los vendedores que sigues no han hecho publiciones en las últimas dos semanas");
        return new SellerFollowedListPostResponseDto(userId, posts);

    }
}
