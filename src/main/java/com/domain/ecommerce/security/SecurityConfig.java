package com.domain.ecommerce.security;
import com.domain.ecommerce.jwtcookieauthentication.JwtCookieAuthenticationFilter;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
public class SecurityConfig {
private final RSAKeyProperties rsaKeys;


@Autowired
public SecurityConfig(RSAKeyProperties rsaKeys) {
    this.rsaKeys = rsaKeys;

}



   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity
               .csrf().disable()
               .cors().and()
               .authorizeRequests().mvcMatchers("/api/users/signup").permitAll()
               .mvcMatchers("/api/users/forgot-password").permitAll()
               .mvcMatchers("/api/users/logout").permitAll()
               .mvcMatchers("/api/categories/all-categories").hasAuthority("ADMIN")
               .mvcMatchers("/api/employees/**").hasAuthority("ADMIN")
               .anyRequest().authenticated()
               .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .httpBasic().and()//c
               .addFilterBefore(new JwtCookieAuthenticationFilter(jwsVerifier(),jwtDecoder()),BasicAuthenticationFilter.class)// ustom filter must use new must be created, do not add bean to spring context!
               .build();
   }

    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }




    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }//public key verifies that the verifies the signature on the token

    @Bean
    JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build(); //private key used to sign token
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
    }

    @Bean
    JWSVerifier jwsVerifier() {
        return new RSASSAVerifier(rsaKeys.publicKey());
    }





}
