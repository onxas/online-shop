package com.project.shop.model.dao;

import com.project.shop.model.entity.Category;
import com.project.shop.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Репозиторий для товаров
 *
 * @author Алексей Климов
 */
public interface ProductRepo extends JpaRepository<Product, Long> {

    /**
     * Вовзращает страницу товаров с фильтрацией по категории и имени
     *
     * @param pageable
     * @param category
     * @param name
     * @return
     */
    @Query("SELECT p from Product  p " +
            "where (:category is null or p.category=:category)" +
            " and (:name is null or lower (p.name) like lower(concat('%', :name,'%')))")
    Page<Product> findAll(Pageable pageable, @Param("category") Category category, @Param("name") String name);

    /**
     * Вощзвращает количетсво доступных твоаров
     *
     * @return
     */
    @Query("SELECT COUNT(p) from Product p where (p.amount>0) ")
    long countNonZeroAmount();

    /**
     * Возвращает наиболее заказываемый товар
     *
     * @return
     */
    Optional<Product> findTopByOrderByOrderedTimesDesc();

    /**
     * Возвращает товар с наибольшим средним рейтингом
     *
     * @return
     */
    Optional<Product> findTopByOrderByRatingDesc();

}
