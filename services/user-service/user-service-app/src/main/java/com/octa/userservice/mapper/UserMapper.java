package com.octa.userservice.mapper;

import com.octa.userservice.model.AuthenticationInfo;
import com.octa.userservice.model.User;
import http.request.RegisterUserRequest;
import http.response.AuthenticationInfoResponse;
import http.response.ForgotPasswordResponse;
import http.response.GetUserResponse;
import http.response.RegisterUserResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User fromRegisterUserRequestToUser(RegisterUserRequest registerUserRequest);

    @AfterMapping
    default void setValueFromRegisterUserRequestToUser(
            RegisterUserRequest registerUserRequest,
            @MappingTarget User user) {
        user.setUserUuid(UUID.randomUUID().toString());
    }

    RegisterUserResponse fromUserToRegisterUserResponse(User savedUser);

    ForgotPasswordResponse fromUserToForgotPasswordResponse(User savedUser);


    List<GetUserResponse> fromUserToGetAllUsersResponse(List<User> all);

    AuthenticationInfoResponse fromAuthenticationInfoToAuthenticationInfoResponse(AuthenticationInfo authenticationInfo);

    @AfterMapping
    default void setValuefromAuthenticationInfoToAuthenticationInfoResponse(
            AuthenticationInfo authenticationInfo,
            @MappingTarget AuthenticationInfoResponse authenticationInfoResponse) {
        authenticationInfoResponse.setTokenValue(
                authenticationInfo.getToken() == null ? null : authenticationInfo.getToken().getToken());
    }
}
