package com.project.shop.model.dao;

import com.project.shop.model.entity.Category;
import com.project.shop.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Репозиторий для товаров
 *
 * @author Алексей Климов
 */
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("SELECT p from Product  p " +
            "where :category is null or p.category=:category")
    Page<Product> findAllByCategory(Pageable pageable, @Param("category") Category category);

    Page<Product> findAllByName(Pageable pageable, String name);
}
