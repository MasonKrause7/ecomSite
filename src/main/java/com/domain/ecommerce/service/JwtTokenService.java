package com.domain.ecommerce.service;

import com.domain.ecommerce.models.RefreshToken;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.repository.JwtRefreshTokenRepository;
import com.domain.ecommerce.repository.UserRepository;
import com.domain.ecommerce.utils.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Candelario Aguilar Torres
 **/
@Service
public class JwtTokenService {
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;
    private final UserRepository userRepository;

    public JwtTokenService(JwtTokenUtil jwtTokenUtil, JwtRefreshTokenRepository jwtRefreshTokenRepository, UserRepository userRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtRefreshTokenRepository = jwtRefreshTokenRepository;
        this.userRepository = userRepository;
    }

    public Map<String, String> getTokens(Authentication authentication) {
        Map<String,String> tokenMap = new HashMap<>();
        String accessToken = jwtTokenUtil.generateToken(authentication);
        String refreshToken = jwtTokenUtil.generateToken(authentication);
        tokenMap.put("accessToken",accessToken);
        tokenMap.put("refreshToken",refreshToken);
        saveRefreshToken(refreshToken,authentication);
        return tokenMap;

    }

    private void saveRefreshToken(String token, Authentication authentication) {
       User user = userRepository.findUserByEmail(authentication.getName()).get();
       user.setRefreshToken(new RefreshToken(token, user));
       userRepository.save(user);




    }
}
