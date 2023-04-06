package com.octa.userservice.http.controller;

import com.octa.userservice.service.IUserService;
import constant.UserServiceConstant;
import http.request.ForgotPasswordRequest;
import http.request.RegisterUserRequest;
import http.response.ForgotPasswordResponse;
import http.response.GetUserResponse;
import http.response.RegisterUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping(UserServiceConstant.UPDATE_USER_URL)
    public ResponseEntity<ForgotPasswordResponse> updateUser(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        ForgotPasswordResponse forgotPasswordResponse = userService.updateUser(forgotPasswordRequest);
            return new ResponseEntity<>(forgotPasswordResponse, HttpStatus.OK);
    }

    @DeleteMapping(UserServiceConstant.DELETE_USER_URL)
    public ResponseEntity<String> deleteUser(@PathVariable String uuid){
        String s = userService.deleteUser(uuid);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @GetMapping(UserServiceConstant.GET_USER_URL)
    public ResponseEntity<List<GetUserResponse>> getAll(){
        var getUserResponse = userService.getAll();
        return new ResponseEntity<>(getUserResponse, HttpStatus.OK);
    }
}
