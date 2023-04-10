package com.octa.userservice.model;

public class AuthenticationInfo {
    private String userUuid;
    private Token token;

    public AuthenticationInfo() {}

    public String getUserUuid() {
        return userUuid;
    }

    public Token getToken() {
        return token;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}

