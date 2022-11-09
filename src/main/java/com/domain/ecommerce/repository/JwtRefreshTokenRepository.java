package com.domain.ecommerce.repository;

import com.domain.ecommerce.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Candelario Aguilar Torres
 **/

@Repository
public interface JwtRefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
}
