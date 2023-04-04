package com.octa.userservice.service;

import http.request.RegisterUserRequest;
import http.response.RegisterUserResponse;

public interface IUserService {


    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);
}
