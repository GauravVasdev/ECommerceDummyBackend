package com.octa.userservice.service.impl;

import com.octa.userservice.mapper.UserMapper;
import com.octa.userservice.model.AuthenticationInfo;
import com.octa.userservice.model.Token;
import com.octa.userservice.model.User;
import com.octa.userservice.port.persistence.IUserRepository;
import com.octa.userservice.service.IAuthenticationService;
import com.octa.userservice.service.ITokenService;
import com.octa.userservice.service.IUserService;
import http.request.ForgotPasswordRequest;
import http.request.LoginRequest;
import http.request.RegisterUserRequest;
import http.response.AuthenticationInfoResponse;
import http.response.ForgotPasswordResponse;
import http.response.GetUserResponse;
import http.response.RegisterUserResponse;

import java.util.List;

public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    private final UserMapper userMapper;

    private final ITokenService tokenService;

    private final IAuthenticationService authenticationService;

    public UserServiceImpl(IUserRepository userRepository, UserMapper userMapper, ITokenService tokenService, IAuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.authenticationService = authenticationService;
    }

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {
        User user = userMapper.fromRegisterUserRequestToUser(registerUserRequest);
        User savedUser = userRepository.save(user);
        return userMapper.fromUserToRegisterUserResponse(savedUser);
    }



    @Override
    public ForgotPasswordResponse updateUser(ForgotPasswordRequest forgotPasswordRequest) {
        User user = null;
        if(forgotPasswordRequest.getUsername()!=null){
            user = userRepository.findByUsername(forgotPasswordRequest.getUsername());
        }else if(forgotPasswordRequest.getEmail()!=null){
            user = userRepository.findByEmail(forgotPasswordRequest.getEmail());
        }
        user.setPassword(forgotPasswordRequest.getPassword());
        User savedUser = userRepository.save(user);
        return userMapper.fromUserToForgotPasswordResponse(savedUser);
    }

    @Override
    public String deleteUser(String uuid) {
        User user = userRepository.findByUserUuid(uuid);
        userRepository.deleteById(user.getId());
        return "user deleted";
    }

    @Override
    public List<GetUserResponse> getAll(){
        List<User> all = userRepository.findAll();
        return userMapper.fromUserToGetAllUsersResponse(all);
    }

    @Override
    public AuthenticationInfoResponse createAuthenticationToken(LoginRequest loginRequest) {
        AuthenticationInfo authenticationInfo =
                authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (authenticationInfo.getToken() != null) {
            Token token = tokenService.save(authenticationInfo.getToken());
        }
        return userMapper.fromAuthenticationInfoToAuthenticationInfoResponse(authenticationInfo);
    }

}
