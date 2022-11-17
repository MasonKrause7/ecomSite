package com.domain.ecommerce.security;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
private final RSAKeyProperties rsaKeys;

public SecurityConfig(RSAKeyProperties rsaKeys) {
    this.rsaKeys = rsaKeys;
}


   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity
               .csrf().disable()
               .cors().and()
               .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
               .exceptionHandling((exception) -> exception.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()).accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
               .authorizeRequests().mvcMatchers("/api/users/signup").permitAll()
               .mvcMatchers("/forgot-password").permitAll()
               .mvcMatchers("/h2-console/*").permitAll()// need in order to view h2 database console while security is enabled.
               .mvcMatchers("/api/users/signin").authenticated()
               .mvcMatchers("/api/employees/**").hasAuthority("SCOPE_ADMIN")
               .and()
               .headers().frameOptions().sameOrigin()// need in order to view h2 database console while security is enabled.
               .and()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()//disable because we are using jwt's not sessions
               .httpBasic().and()
               .build();
   }

    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }


    /*
    the two beans below are needed in order to configure application to use jwt tokens.
     must add spring-security-oauth2-jose  and spring-security-oauth2-resource-server to pom.xml before using.
     */

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


}
