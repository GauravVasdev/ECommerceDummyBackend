package com.octa.userservice.service.impl;

import com.octa.userservice.model.AuthenticationInfo;
import com.octa.userservice.model.Role;
import com.octa.userservice.model.Token;
import com.octa.userservice.model.Urls;
import com.octa.userservice.model.User;
import com.octa.userservice.port.persistence.IRoleRepository;
import com.octa.userservice.port.persistence.IUserRepository;
import com.octa.userservice.service.IAuthenticateService;
import com.octa.userservice.service.IAuthenticationService;
import com.octa.userservice.util.JwtTokenUtil;
import http.request.AuthorizationFormRequest;

import java.util.List;
import java.util.Optional;

public class AuthenticationServiceImpl implements IAuthenticationService {

    private final IUserRepository userRepository;

    private final IRoleRepository roleRepository;

    private final IAuthenticateService authenticateService;

    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationServiceImpl(IUserRepository userRepository, IRoleRepository roleRepository, IAuthenticateService authenticateService, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticateService = authenticateService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public AuthenticationInfo authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user==null) {
            throw new RuntimeException("User Not Found");
        }

        Boolean valid = authenticateService.authenticatePassword(password, user);

        if (!valid) {
            throw new RuntimeException("Invalid Credentials");
        }
        Token token = null;

        token = jwtTokenUtil.generateToken(user);
        AuthenticationInfo authenticationInfo = new AuthenticationInfo();
        authenticationInfo.setToken(token);
        authenticationInfo.setUserUuid(user.getUserUuid());

        return authenticationInfo;
    }

    @Override
    public Boolean authorizeRole(AuthorizationFormRequest authorizationFormRequest) {
        String token = authorizationFormRequest.getToken();
        String role = jwtTokenUtil.getUserRoleFromToken(token);
        if (role.toLowerCase().equals("admin")) {
            return true;
        }
        Role roleByName =roleRepository.findByRoleName(role).orElseThrow(() -> new RuntimeException("role not found"));
        List<Urls> urls = roleByName.getUrls();
        Boolean result = false;
        for (Urls url : urls) {
            if (authorizationFormRequest.getUri().contains(url.getUrl())) {
                result = true;
            }
        }
        return result;
    }
}
