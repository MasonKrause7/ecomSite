package com.domain.ecommerce.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author Candelario Aguilar Torres
 **/
@ConfigurationProperties(prefix = "rsa")//annotation used to access properties in the .properties file
public record RSAKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {/* private and public keys used to generate jwt tokens. path to keys are specified in the .properties file*/
}
