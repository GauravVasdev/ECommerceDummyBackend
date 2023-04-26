package com.octa.userservice.service;

import com.octa.userservice.model.AuthenticationInfo;
import http.request.AuthorizationFormRequest;

public interface IAuthenticationService {
    AuthenticationInfo authenticate(String username, String password);

    Boolean authorizeRole(AuthorizationFormRequest authorizationFormRequest);
}
