package com.octa.edgeservice.security;

import com.octa.constants.CommonConstants;
import com.octa.edgeservice.api.Userapi;
import constant.EdgeServiceConstants;
import http.response.ValidateTokenResponse;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Component
public class AuthenticationFilter
        extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final ApplicationProps applicationProps;
    private Userapi userApi;

    private Map<String, String> mapOfDomainNameAndTenantId = new HashMap<>();

    public AuthenticationFilter(ApplicationProps applicationProps, @Lazy Userapi userApi) {
        super(Config.class);
        this.applicationProps = applicationProps;
        this.userApi = userApi;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            final List<String> apiEndpoints = List.of(EdgeServiceConstants.IGNORE_END_POINTS);
            Predicate<ServerHttpRequest> isApiNotSecured =
                    serverHttpRequest ->
                            applicationProps.getUnfiltered().stream()
                                    .noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));
            if (isApiNotSecured.test(request)) {
                if (!isAuthMissing(request)) {
                    return this.onError(
                            exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
                }

                final String requestTokenHeader = getAuthHeader(request);
                if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                    String jwtToken = requestTokenHeader.substring(7);
                    ResponseEntity<ValidateTokenResponse> validateTokenResponse =
                            userApi.validateJwt(jwtToken);

                    if (validateTokenResponse != null
                            && validateTokenResponse.getBody().getStatus().equals(CommonConstants.FAILURE)) {
                        throw new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED, validateTokenResponse.getBody().getMessage());
                    }
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        byte[] bytes = err.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return request.getHeaders().containsKey("Authorization");
    }

    public static class Config {
        // Put the configuration properties
    }
}
