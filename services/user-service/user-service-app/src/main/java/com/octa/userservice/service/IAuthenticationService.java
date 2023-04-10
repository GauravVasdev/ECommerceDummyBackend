package com.octa.userservice.service;

import com.octa.userservice.model.AuthenticationInfo;

public interface IAuthenticationService {
    AuthenticationInfo authenticate(String username, String password);
}
