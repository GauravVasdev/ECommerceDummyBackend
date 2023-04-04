package com.octa.userservice.http.controller;

import com.octa.userservice.service.IUserService;
import constant.UserServiceConstant;
import http.request.RegisterUserRequest;
import http.response.RegisterUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = UserServiceConstant.BASE_URL)
public class UserApiController {

    private final IUserService userService;

    public UserApiController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(UserServiceConstant.CREATE_USER_URL)
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);
        return new ResponseEntity<>(registerUserResponse, HttpStatus.OK);
    }
}
