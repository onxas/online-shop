package com.project.shop.model.dao;

import com.project.shop.model.entity.Feedback;
import com.project.shop.model.entity.Product;
import com.project.shop.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для отзывов
 *
 * @author Алексей Климов
 */
public interface FeedbackRepo extends JpaRepository<Feedback, Long> {

    /**
     * Возвращает страницу отззывов к товару
     *
     * @param product
     * @param pageable
     * @return
     */
    Page<Feedback> findAllByProductOrderByDate(Product product, Pageable pageable);

    /**
     * Возвращает отзыв пользователя к товару
     *
     * @param author
     * @param product
     * @return
     */
    Optional<Feedback> findByAuthorAndProduct(User author, Product product);

    /**
     * Возвращает количество отзывов товара
     *
     * @param product
     * @return
     */
    Long countByProduct(Product product);

    /**
     * Удаляет все отзывы к товару
     *
     * @param product
     */
    void deleteAllByProduct(Product product);

}