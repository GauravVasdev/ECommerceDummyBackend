package com.octa.userservice.config;

import com.octa.userservice.mapper.UserMapper;
import com.octa.userservice.port.persistence.IUserRespository;
import com.octa.userservice.service.IUserService;
import com.octa.userservice.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class AppConfiguration{

    @Bean
    public IUserService userService(IUserRespository userRespository, UserMapper userMapper){
        return new UserServiceImpl(userRespository,  userMapper);
    }
}
