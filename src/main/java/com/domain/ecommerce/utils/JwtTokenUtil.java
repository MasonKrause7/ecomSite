package com.domain.ecommerce.utils;

import com.domain.ecommerce.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/**
 * @author Candelario Aguilar Torres
 **/


/*
generates the jwt tokens
 */
@Service
public class JwtTokenUtil {
    private final JwtEncoder jwtEncoder;



    @Autowired
    public JwtTokenUtil(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }
  /*
  generates an access token
   */
    public String generateToken(Authentication authentication) {// use spring authentication interface
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(15,ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope",scope)
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }

    public String generateRefreshToken(Authentication authentication) {// use spring authentication interface
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(24,ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope",scope)
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }
/*
returns a temporary token to be used when a password reset is requested
 */
    public String generateTempToken(User user, int duration) {
        Instant now = Instant.now();
        String scope = user.getAuthority();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(duration,ChronoUnit.MINUTES))
                .subject(user.getEmail())
                .claim("scope",scope)
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();


    }

}
