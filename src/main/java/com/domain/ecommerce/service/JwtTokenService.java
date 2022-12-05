package com.domain.ecommerce.service;

import com.domain.ecommerce.utils.JwtTokenUtil;
import com.domain.ecommerce.utils.TokenIdentifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Candelario Aguilar Torres
 **/

/*
 * Utility class used to generate JWT tokens
 */
@Service
public class JwtTokenService {
    private final JwtTokenUtil jwtTokenUtil;

    public JwtTokenService(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }
  /*
  Get access and refresh token
   */
    public Map<String, String> getTokens(Authentication authentication) {
        Map<String,String> tokenMap = new HashMap<>();
        String accessToken = jwtTokenUtil.generateToken(authentication);
        String refreshToken = jwtTokenUtil.generateRefreshToken(authentication);
        tokenMap.put(TokenIdentifier.ACESSTOKEN,accessToken);
        tokenMap.put(TokenIdentifier.REFRESHTOKEN,refreshToken);
        return tokenMap;

    }




}
