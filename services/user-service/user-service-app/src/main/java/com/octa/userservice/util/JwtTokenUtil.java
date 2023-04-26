package com.octa.userservice.util;

import com.octa.userservice.model.Token;
import com.octa.userservice.model.User;
import com.octa.userservice.port.persistence.IRoleRepository;
import com.octa.userservice.port.persistence.IUserRepository;
import constant.TokenConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenUtil {

    public static final long JWT_TOKEN_VALIDITY = 7 * 24 * 60 * 60;

    private final IUserRepository userRepository;

    private final IRoleRepository roleRepository;

    public JwtTokenUtil(IUserRepository userRepository, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * @param token this method username from token
     * @return username
     */
    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            username = getClaimFromToken(token, Claims::getSubject);
        } catch (MalformedJwtException e) {
            username = TokenConstants.INVALID_TOKEN;
        }
        return username;
    }

    /**
     * @param token this method extract expiration from token using claims
     * @return Date
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String getUserRoleFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        var role = (String) claims.get("Roles");
        return role;
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * @param token this method extracts claims set of rules define to help to extract info from token
     *     using secret key
     * @return Claims
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(TokenConstants.SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * @param token check expiration of token
     * @return boolean value
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * this method generate token
     *
     * @param user
     * @return Token
     */
    public Token generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        var roleName = roleRepository.findById(user.getRole().getId()).get().getRoleName();
        String token = doGenerateToken(user.getEmail(), roleName);
        Token tokenObj=new Token();
        tokenObj.setToken(token);
        tokenObj.setType(TokenConstants.TOKEN_TYPE);
        tokenObj.setUser(user);
        tokenObj.setActive(true);
        return tokenObj;
    }

    /**
     * @param
     * @param subject this is internal method calling to generate token using secretkey
     * @return token
     */
    private String doGenerateToken(String subject, String roleName) {

        return Jwts.builder()
                .claim("Roles",roleName)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, TokenConstants.SECRET_KEY)
                .compact();
    }
}
