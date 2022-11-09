package com.domain.ecommerce.models;

import javax.persistence.*;

/**
 * @author Candelario Aguilar Torres
 **/

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long token_id;
    @Column(length = 500)
    private String token;
    @OneToOne(mappedBy = "refreshToken")
    private User user;

    public RefreshToken(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public RefreshToken() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RefreshToken(String token) {
        this.token = token;
    }
}
