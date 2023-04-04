package com.octa.userservice.service.impl;

import com.octa.userservice.mapper.UserMapper;
import com.octa.userservice.model.User;
import com.octa.userservice.port.persistence.IUserRespository;
import com.octa.userservice.service.IUserService;
import http.request.RegisterUserRequest;
import http.response.RegisterUserResponse;

public class UserServiceImpl implements IUserService {

    private final IUserRespository userRespository;

    private final UserMapper userMapper;

    public UserServiceImpl(IUserRespository userRespository, UserMapper userMapper) {
        this.userRespository = userRespository;
        this.userMapper = userMapper;
    }

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {
        User user = userMapper.fromRegisterUserRequestToUser(registerUserRequest);
        User savedUser = userRespository.save(user);
        return userMapper.fromUserToRegisterUserResponse(savedUser);
    }
}