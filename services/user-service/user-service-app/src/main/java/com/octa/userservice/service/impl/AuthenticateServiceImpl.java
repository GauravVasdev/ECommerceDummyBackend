package com.octa.userservice.service.impl;

import com.octa.userservice.model.User;
import com.octa.userservice.service.IAuthenticateService;
import com.octa.userservice.service.IAuthenticationService;

public class AuthenticateServiceImpl implements IAuthenticateService {
    @Override
    public Boolean authenticatePassword(String password, User user) {
        return password.equals(user.getPassword());
    }
}
