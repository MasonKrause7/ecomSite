package com.domain.ecommerce.repository;

import com.domain.ecommerce.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author Candelario Aguilar Torres
 **/
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
