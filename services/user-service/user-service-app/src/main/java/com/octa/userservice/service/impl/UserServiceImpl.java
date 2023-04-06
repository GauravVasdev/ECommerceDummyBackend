package com.octa.userservice.service.impl;

import com.octa.userservice.mapper.UserMapper;
import com.octa.userservice.model.User;
import com.octa.userservice.port.persistence.IUserRespository;
import com.octa.userservice.service.IUserService;
import http.request.ForgotPasswordRequest;
import http.request.RegisterUserRequest;
import http.response.ForgotPasswordResponse;
import http.response.GetUserResponse;
import http.response.RegisterUserResponse;

import java.util.List;

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



    @Override
    public ForgotPasswordResponse updateUser(ForgotPasswordRequest forgotPasswordRequest) {
        User user = null;
        if(forgotPasswordRequest.getUsername()!=null){
            user = userRespository.findByUsername(forgotPasswordRequest.getUsername());
        }else if(forgotPasswordRequest.getEmail()!=null){
            user = userRespository.findByEmail(forgotPasswordRequest.getEmail());
        }
        user.setPassword(forgotPasswordRequest.getPassword());
        User savedUser = userRespository.save(user);
        return userMapper.fromUserToForgotPasswordResponse(savedUser);
    }

    @Override
    public String deleteUser(String uuid) {
        User user = userRespository.findByUserUuid(uuid);
        userRespository.deleteById(user.getId());
        return "user deleted";
    }

    @Override
    public List<GetUserResponse> getAll(){
        List<User> all = userRespository.findAll();
        return userMapper.fromUserToGetAllUsersResponse(all);
//        return abc(all)
    }

}
