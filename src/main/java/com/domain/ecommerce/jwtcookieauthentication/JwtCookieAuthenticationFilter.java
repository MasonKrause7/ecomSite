package com.domain.ecommerce.jwtcookieauthentication;

import com.domain.ecommerce.exceptions.authenticationControllerExceptions.AuthenticationControllerException;
import com.domain.ecommerce.utils.TokenIdentifier;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Candelario Aguilar Torres
 **/
public class JwtCookieAuthenticationFilter extends OncePerRequestFilter {

    private final JWSVerifier jwsVerifier;
    private final JwtDecoder jwtDecoder;

    public JwtCookieAuthenticationFilter(JWSVerifier jwsVerifier, JwtDecoder jwtDecoder) {
        this.jwsVerifier = jwsVerifier;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header != null) {

            filterChain.doFilter(request,response);//if not null/ forward request to proper filter that can handle authorization
            return;
        }
        System.out.println("Checking access token");
        if(checkRefreshToken(request,response,false)) {//executes only if authorization header in null: validates token and forwards request
            System.out.println("access token is valid");
            filterChain.doFilter(request,response);
            return;
        } else {
            System.out.println("access token invalid");
        }

        System.out.println("Checking refresh token");
        if(checkRefreshToken(request,response,true)) {//executes only if accesstoken is false.
            System.out.println("refresh token is valid");
           filterChain.doFilter(request,response);
           return;
        } else {
            System.out.println("refresh token is invalid");
        }

        filterChain.doFilter(request,response);

    }

    private boolean checkAccessTokenCookie( HttpServletRequest request,boolean isRefreshToken) {

        Cookie cookie;
         if(isRefreshToken) {
             cookie = WebUtils.getCookie(request,TokenIdentifier.REFRESHTOKEN);

         } else {

             cookie = WebUtils.getCookie(request, TokenIdentifier.ACESSTOKEN);

         }


        if(cookie == null) {
            System.out.println("cookie is null");
            return false;
        }


        String token = cookie.getValue();
        try {
            SignedJWT.parse(token).verify(jwsVerifier);;
            Jwt jwt = jwtDecoder.decode(token);

            String username = jwt.getSubject();
            List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(jwt.getClaims().get("scope").toString()));

            Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return true;


        } catch (JOSEException | ParseException | JwtValidationException e) {

          return false;
        }


    }

    private boolean checkRefreshToken(HttpServletRequest request, HttpServletResponse response,boolean isRefreshToken) {
        return checkAccessTokenCookie(request,isRefreshToken);
    }
}
