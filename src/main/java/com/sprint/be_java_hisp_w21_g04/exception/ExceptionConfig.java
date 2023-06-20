package com.sprint.be_java_hisp_w21_g04.exception;

import com.sprint.be_java_hisp_w21_g04.dto.ErrorValidationsDTO;
import com.sprint.be_java_hisp_w21_g04.dto.response.ErrorDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import com.sprint.be_java_hisp_w21_g04.dto.response.UserNotFoundDto;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;


@ControllerAdvice(annotations = RestController.class)
public class ExceptionConfig {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleJsonEmpty(HttpMessageNotReadableException e){
        ErrorDto error = new ErrorDto("Error al deserializar JSON en el cuerpo de la solicitud", 400);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(Exception e){
        UserNotFoundDto userNotFoundDto = new UserNotFoundDto(e.getMessage(), 404);
        return new ResponseEntity<>(userNotFoundDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyFollowedException.class)
    public ResponseEntity<?> userAlreadyFollowedException(Exception e){
        ErrorDto error = new ErrorDto(e.getMessage(), 400);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFollowedException.class)
    public ResponseEntity<?> userNotFollowedException(Exception e){
        ErrorDto error = new ErrorDto(e.getMessage(), 400);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserFollowNotAllowedException.class)
    public ResponseEntity<?> userFollowNotAllowedException(Exception e){
        ErrorDto error = new ErrorDto(e.getMessage(), 400);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserUnfollowNotAllowedException.class)
    public ResponseEntity<?> userUnfollowNotAllowedException(Exception e){
        ErrorDto error = new ErrorDto(e.getMessage(), 400);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFound(NotFoundException e){
        ErrorDto error = new ErrorDto(e.getMessage(), 404);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalDataException.class)
    public ResponseEntity<?> idNotValid(IllegalDataException e){
        ErrorDto errorDto = new ErrorDto(e.getMessage(),400);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptySellerFollowedList.class)
    public ResponseEntity<?> emptySellerFollowedList(Exception e){
        ErrorDto error = new ErrorDto(e.getMessage(), 400);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PostAlreadyExist.class)
    public ResponseEntity<?> postAreadyExist(Exception e){
        ErrorDto error = new ErrorDto(e.getMessage(), 400);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorValidationsDTO> validationException(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body(
                new ErrorValidationsDTO("Se encontraron los siguientes errores en las validaciones: ",
                        e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList())
                )
        );
    }


//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> generalException(Exception e){
//        ErrorDto error = new ErrorDto(e.getMessage(), 500);
//        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
