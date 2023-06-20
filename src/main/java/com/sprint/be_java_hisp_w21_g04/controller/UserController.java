package com.sprint.be_java_hisp_w21_g04.controller;

import com.sprint.be_java_hisp_w21_g04.dto.response.*;
import com.sprint.be_java_hisp_w21_g04.service.user.UserServiceImpl;
import com.sprint.be_java_hisp_w21_g04.dto.response.FollowedResponseDto;
import com.sprint.be_java_hisp_w21_g04.dto.response.FollowersResponseDto;
import com.sprint.be_java_hisp_w21_g04.service.user.IUserService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {
    private UserServiceImpl _userService;

    @Min(value = 1, message = "El id debe ser mayor a cero")

    public UserController(UserServiceImpl userService) {
        this._userService = userService;
    }
    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<ResponseDto> userFollow(
            @PathVariable @NotNull(message = "El id no puede estar vacío") @Min(value = 1, message = "El id debe ser mayor a cero") Integer userId,
            @PathVariable @NotNull(message = "El id no puede estar vacío") @Min(value = 1, message = "El id debe ser mayor a cero") Integer userIdToFollow){
        return ResponseEntity.ok(_userService.followUser(userId, userIdToFollow));
    }
    @GetMapping("/users/{userId}/followers/count")
    public ResponseEntity<UserFollowersCountDto> userFollowersCountDto(@PathVariable int userId){
        return ResponseEntity.status(200).body(_userService.getFollowersCount(userId));
    }

    @PostMapping("/users/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<ResponseDto> userUnfollow(
            @PathVariable @NotNull(message = "El id no puede estar vacío") @Min(value = 1, message = "El id debe ser mayor a cero") Integer userId,
            @PathVariable @NotNull(message = "El id no puede estar vacío") @Min(value = 1, message = "El id debe ser mayor a cero") Integer userIdToUnfollow){
        return ResponseEntity.ok(_userService.unfollowUser(userId, userIdToUnfollow));
    }

    @GetMapping("/users/{userId}/followers/list")
    public ResponseEntity<FollowersResponseDto> getFollowersById(@PathVariable ("userId") int userId,
                                                                 @RequestParam (value = "order",required = false) String order){
        if(order == null){
            return new ResponseEntity<>(_userService.getFollowersById(userId), HttpStatus.OK);
        }
        return new ResponseEntity<>(_userService.getFollowersByIdSorted(userId,order), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/followed/list")
    public ResponseEntity<FollowedResponseDto> getFollowedById(@PathVariable ("userId") int userId,
                                                               @RequestParam (value = "order",required = false) String order){
        if(order == null){
            return new ResponseEntity<>(_userService.getFollowedById(userId), HttpStatus.OK);
        }
        return new ResponseEntity<>(_userService.getFollowedByIdSorted(userId, order), HttpStatus.OK);
    }

}
