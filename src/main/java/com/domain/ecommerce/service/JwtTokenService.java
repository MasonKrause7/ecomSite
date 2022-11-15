package com.domain.ecommerce.service;

import com.domain.ecommerce.exceptions.authenticationControllerExceptions.AuthenticationControllerException;
import com.domain.ecommerce.models.RefreshToken;
import com.domain.ecommerce.models.User;
import com.domain.ecommerce.repository.UserRepository;
import com.domain.ecommerce.utils.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Candelario Aguilar Torres
 **/
@Service
public class JwtTokenService {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    public JwtTokenService(JwtTokenUtil jwtTokenUtil, UserRepository userRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    public Map<String, String> getTokens(Authentication authentication) {
        Map<String,String> tokenMap = new HashMap<>();
        String accessToken = jwtTokenUtil.generateToken(authentication);
        String refreshToken = jwtTokenUtil.generateRefreshToken(authentication);
        tokenMap.put("accessToken",accessToken);
        tokenMap.put("refreshToken",refreshToken);
        saveRefreshToken(authentication);
        return tokenMap;

    }

    private void saveRefreshToken(Authentication authentication) {
       User user = userRepository.findUserByEmail(authentication.getName()).get();
       user.setRefreshToken(new RefreshToken());
       userRepository.save(user);


    }



    public String refreshAccessToken(Authentication authentication) throws AuthenticationControllerException{
        User user = userRepository.findUserByEmail(authentication.getName()).get();
        System.out.println(authentication.getName());
        Instant refreshTokeExpire = user.getRefreshToken().getExpiration();
        System.out.println(refreshTokeExpire);
        if(refreshTokeExpire != null && refreshTokeExpire.isAfter(Instant.now())) {
            return jwtTokenUtil.generateToken(authentication);
        }else throw new AuthenticationControllerException("REFRESH TOKEN IS EXPIRED OR DOES NOT EXIST");

    }

    public String getTempToken(User user) {
        user.setRefreshToken(new RefreshToken());
        userRepository.save(user);
        return jwtTokenUtil.generateTempToken(user);
    }


}
