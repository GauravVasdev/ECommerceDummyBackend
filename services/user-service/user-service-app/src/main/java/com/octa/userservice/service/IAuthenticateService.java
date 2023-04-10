package com.octa.userservice.service;

import com.octa.userservice.model.User;

public interface IAuthenticateService {

    Boolean authenticatePassword(String password, User user);
}
