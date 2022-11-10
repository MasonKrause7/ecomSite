package com.domain.ecommerce.models;

import javax.persistence.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author Candelario Aguilar Torres
 **/

@Entity
public class RefreshToken {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long token_id;

    private Instant expiration;
    @OneToOne(mappedBy = "refreshToken")
    private User user;

    public Instant getExpiration() {
        return expiration;
    }

    public RefreshToken() {
        this.expiration = Instant.now().plus(24, ChronoUnit.HOURS);

    }

}
