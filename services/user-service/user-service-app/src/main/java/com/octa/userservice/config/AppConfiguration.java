package com.octa.userservice.config;

import com.octa.userservice.mapper.UserMapper;
import com.octa.userservice.port.persistence.ITokenRepository;
import com.octa.userservice.port.persistence.IUserRepository;
import com.octa.userservice.port.persistence.IUserRepository;
import com.octa.userservice.service.IAuthenticateService;
import com.octa.userservice.service.IAuthenticationService;
import com.octa.userservice.service.ITokenService;
import com.octa.userservice.service.IUserService;
import com.octa.userservice.service.impl.AuthenticateServiceImpl;
import com.octa.userservice.service.impl.AuthenticationServiceImpl;
import com.octa.userservice.service.impl.TokenServiceImpl;
import com.octa.userservice.service.impl.UserServiceImpl;
import com.octa.userservice.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration{

    @Bean
    public ITokenService tokenService(ITokenRepository tokenRepository){
        return new TokenServiceImpl(tokenRepository);
    }


    @Bean
    public IAuthenticateService authenticateService(){
        return new AuthenticateServiceImpl();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil(IUserRepository userRepository){
        return new JwtTokenUtil(userRepository);
    }

    @Bean
    public IAuthenticationService authenticationService(IUserRepository userRepository, IAuthenticateService authenticateService, JwtTokenUtil jwtTokenUtil){
        return new AuthenticationServiceImpl(userRepository,authenticateService,jwtTokenUtil);
    }

    @Bean
    public IUserService userService(IUserRepository userRepository, UserMapper userMapper, ITokenService tokenService, IAuthenticationService authenticationService){
        return new UserServiceImpl(userRepository,  userMapper, tokenService, authenticationService);
    }
}
