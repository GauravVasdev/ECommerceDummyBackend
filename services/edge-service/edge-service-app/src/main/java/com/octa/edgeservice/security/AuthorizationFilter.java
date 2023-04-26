package com.octa.edgeservice.security;

import com.octa.edgeservice.api.Userapi;
import http.request.AuthorizationFormRequest;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static constant.EdgeServiceConstants.ACCESS_DENIED;

@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    private final ApplicationProps applicationProps;

    private Userapi userApi;

    public AuthorizationFilter(ApplicationProps applicationProps, @Lazy Userapi userApi) {
        super(AuthorizationFilter.Config.class);
        this.applicationProps = applicationProps;
        this.userApi = userApi;
    }

    @Override
    public GatewayFilter apply(Config config) {
        List<String> urls = new ArrayList<>();
        urls.addAll(applicationProps.getUnfiltered());
        urls.addAll(applicationProps.getFiltered());
        return (exchange, chain) -> {
            String userNme = null;
            ServerHttpRequest request = exchange.getRequest();

            Predicate<ServerHttpRequest> isApiSecured =
                    r -> urls.stream().noneMatch(uri -> r.getURI().getPath().contains(uri));

            if (isApiSecured.test(request)) {

                final String requestTokenHeader = request.getHeaders().getOrEmpty("Authorization").get(0);
                if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                    String jwtToken = requestTokenHeader.substring(7);
                    AuthorizationFormRequest authorizationFormRequest = new AuthorizationFormRequest();
                    authorizationFormRequest.setToken(jwtToken);
                    authorizationFormRequest.setUri(request.getPath().toString());
                    var authorized = userApi.authorizationRole(authorizationFormRequest);
                    if (authorized == false) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, ACCESS_DENIED);
                    }
                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Put the configuration properties
    }
}

