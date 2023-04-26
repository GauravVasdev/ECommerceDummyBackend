package com.octa.userservice.config;

import com.octa.userservice.mapper.UserMapper;
import com.octa.userservice.port.persistence.IRoleRepository;
import com.octa.userservice.port.persistence.ITokenRepository;
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
import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration{

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public ITokenService tokenService(ITokenRepository tokenRepository){
        return new TokenServiceImpl(tokenRepository);
    }


    @Bean
    public IAuthenticateService authenticateService(){
        return new AuthenticateServiceImpl();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil(IUserRepository userRepository, IRoleRepository roleRepository){
        return new JwtTokenUtil(userRepository, roleRepository);
    }

    @Bean
    public IAuthenticationService authenticationService(IUserRepository userRepository, IRoleRepository roleRepository,IAuthenticateService authenticateService, JwtTokenUtil jwtTokenUtil){
        return new AuthenticationServiceImpl(userRepository, roleRepository, authenticateService,jwtTokenUtil);
    }

    @Bean
    public IUserService userService(IUserRepository userRepository, IRoleRepository roleRepository,UserMapper userMapper, ITokenService tokenService, IAuthenticationService authenticationService, JwtTokenUtil jwtTokenUtil){
        return new UserServiceImpl(userRepository, roleRepository, userMapper, tokenService, authenticationService, jwtTokenUtil);
    }

    @PostConstruct
    public void runFlyway() {
        Flyway flyway =
                Flyway.configure().baselineOnMigrate(true).dataSource(url, userName, password).load();
        flyway.baseline();
        flyway.migrate();
    }
}
