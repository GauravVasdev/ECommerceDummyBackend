package com.octa.userservice.service;

import com.octa.userservice.model.User;
import http.request.ForgotPasswordRequest;
import http.request.LoginRequest;
import http.request.RegisterUserRequest;
import http.response.AuthenticationInfoResponse;
import http.response.ForgotPasswordResponse;
import http.response.GetUserResponse;
import http.response.RegisterUserResponse;

import java.util.List;

public interface IUserService {


    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);

    ForgotPasswordResponse updateUser(ForgotPasswordRequest forgotPasswordRequest);

    String deleteUser(String uuid);

    List<GetUserResponse> getAll();


    AuthenticationInfoResponse createAuthenticationToken(LoginRequest loginRequest);
}
