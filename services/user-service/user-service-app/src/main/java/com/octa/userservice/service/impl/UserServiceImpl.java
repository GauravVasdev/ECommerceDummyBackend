package com.octa.userservice.service.impl;

import com.octa.userservice.mapper.UserMapper;
import com.octa.userservice.model.AuthenticationInfo;
import com.octa.userservice.model.Token;
import com.octa.userservice.model.User;
import com.octa.userservice.model.ValidateToken;
import com.octa.userservice.port.persistence.IUserRepository;
import com.octa.userservice.service.IAuthenticationService;
import com.octa.userservice.service.ITokenService;
import com.octa.userservice.service.IUserService;
import com.octa.userservice.util.JwtTokenUtil;
import constant.TokenConstants;
import http.request.ForgotPasswordRequest;
import http.request.LoginRequest;
import http.request.RegisterUserRequest;
import http.response.AuthenticationInfoResponse;
import http.response.ForgotPasswordResponse;
import http.response.GetUserResponse;
import http.response.RegisterUserResponse;
import http.response.ValidateTokenResponse;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    private final UserMapper userMapper;

    private final ITokenService tokenService;

    private final IAuthenticationService authenticationService;

    private final JwtTokenUtil jwtTokenUtil;

    public UserServiceImpl(IUserRepository userRepository, UserMapper userMapper, ITokenService tokenService, IAuthenticationService authenticationService, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.authenticationService = authenticationService;
        this.jwtTokenUtil = jwtTokenUtil;
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

    @Override
    public ValidateTokenResponse validateJwt(String token) {
        ValidateToken validateToken = null;
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if (username.equals(TokenConstants.INVALID_TOKEN)) {
            validateToken =
                    new ValidateToken(TokenConstants.FAILURE, TokenConstants.INVALID_TOKEN_SIGNATURE);
            return userMapper.fromValidateTokenToValidateTokenResponse(validateToken);
        }
        User user = userRepository.findByEmail(username);
        Token tokenFromDb = tokenService.getByToken(token);
        if (user != null && tokenFromDb != null) {
            if (user.getEmail().equals(username)
                    && !jwtTokenUtil.isTokenExpired(token)
                    && tokenFromDb.getActive()) {
                validateToken = new ValidateToken(TokenConstants.SUCCESS, TokenConstants.TOKEN_VERIFIED);
                validateToken.setUser(user);
            } else {
                throw new RuntimeException("Invalid Token");
            }
        } else {
            throw new RuntimeException("User not found from token");
        }
        ValidateTokenResponse validateTokenResponse =
                userMapper.fromValidateTokenToValidateTokenResponse(validateToken);
        return validateTokenResponse;
    }

}
