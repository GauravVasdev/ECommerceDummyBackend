package com.octa.userservice.http.controller;

import com.octa.userservice.service.IUserService;
import constant.UserServiceConstant;
import http.request.AuthorizationFormRequest;
import http.request.ForgotPasswordRequest;
import http.request.LoginRequest;
import http.request.RegisterUserRequest;
import http.response.AuthenticationInfoResponse;
import http.response.ForgotPasswordResponse;
import http.response.GetUserResponse;
import http.response.RegisterUserResponse;
import http.response.ValidateTokenResponse;
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


    @PostMapping(UserServiceConstant.LOGIN_URL)
    public ResponseEntity<AuthenticationInfoResponse> createAuthenticationToken(@RequestBody LoginRequest loginRequest){
        AuthenticationInfoResponse authenticationInfoResponse = userService.createAuthenticationToken(loginRequest);
        return new ResponseEntity<>(authenticationInfoResponse, HttpStatus.OK);
    }

    @PostMapping(value = UserServiceConstant.VALIDATE_TOKEN_URL)
    public ResponseEntity<ValidateTokenResponse> validateJwt(@RequestParam("token") String token) {
        ValidateTokenResponse response = userService.validateJwt(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = UserServiceConstant.VALIDATE_ROLE)
    private Boolean authorizationRole(
            @RequestBody AuthorizationFormRequest authorizationFormRequest) {
        return userService.authorizeRole(authorizationFormRequest);
    }


    @PostMapping("/home")
    public String home() {
        System.out.println("I am in home");
        return "Yes I am in home";
    }
}
