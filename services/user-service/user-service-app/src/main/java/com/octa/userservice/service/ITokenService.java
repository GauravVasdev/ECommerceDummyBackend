package com.octa.userservice.service;

import com.octa.userservice.model.Token;

public interface ITokenService {

    Token save(Token token);

    Token getByToken(String token);
}
