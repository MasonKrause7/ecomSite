package com.domain.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Candelario Aguilar Torres
 **/
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
