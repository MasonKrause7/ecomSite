package com.domain.ecommerce.utils;

import com.domain.ecommerce.exceptions.authenticationControllerExceptions.AuthenticationControllerException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author Candelario Aguilar Torres
 **/
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWSVerifier jwsVerifier;

    public JwtTokenAuthenticationFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


       Cookie cookie = getJwtToken(request);
       if(cookie != null) {
           String token = cookie.getValue();
           try {
               SignedJWT signedJWT = SignedJWT.parse(token);
               Boolean verified = signedJWT.verify(jwsVerifier);
               if(verified) {
                   filterChain.doFilter(request,response);
               } else {

                   System.out.println("could not authenticate");
                   response.sendError(401,"token could not be authenticated, may be null or expired");
               }

           } catch (ParseException e) {
               throw new RuntimeException(e);
           } catch (JOSEException e) {
               throw new RuntimeException(e);
           }

       } else {
           filterChain.doFilter(request,response);
       }


    }

    private Cookie getJwtToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("accesstoken")) {
                    return cookie;
                }

            }
        }

        return null;
    }
}
