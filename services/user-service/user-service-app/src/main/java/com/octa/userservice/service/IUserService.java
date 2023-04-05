package com.octa.userservice.service;

import http.request.ForgotPasswordRequest;
import http.request.RegisterUserRequest;
import http.response.ForgotPasswordResponse;
import http.response.RegisterUserResponse;

public interface IUserService {


    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);

    ForgotPasswordResponse updateUser(ForgotPasswordRequest forgotPasswordRequest);
}
