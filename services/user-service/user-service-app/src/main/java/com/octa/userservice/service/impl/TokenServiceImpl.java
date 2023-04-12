package com.octa.userservice.service.impl;

import com.octa.userservice.model.Token;
import com.octa.userservice.port.persistence.ITokenRepository;
import com.octa.userservice.service.ITokenService;

public class TokenServiceImpl implements ITokenService {


    private ITokenRepository tokenRepository;

    public TokenServiceImpl(ITokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     * @param token
     * @return
     */
    @Override
    public Token save(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public Token getByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
