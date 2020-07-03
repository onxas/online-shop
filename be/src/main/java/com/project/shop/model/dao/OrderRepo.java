package com.project.shop.model.dao;

import com.project.shop.model.entity.Order;
import com.project.shop.model.entity.Product;
import com.project.shop.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для заказов
 *
 * @author Алексей Климов
 */
public interface OrderRepo extends JpaRepository<Order, Long> {

    /**
     * Возращает страницу твоаров пользователя
     *
     * @param pageable
     * @param user
     * @return
     */
    Page<Order> findAllByUser(Pageable pageable, User user);

    /**
     * Вовзращает заказ пользователя с определённым товаром
     *
     * @param user
     * @param product
     * @return
     */
    @Query("SELECT o from orders o join OrderedItem oi on (o.user=:user and oi member of o.orderedItems and oi.product=:product)")
    Optional<Order> findByUserAndProduct(@Param("user") User user, @Param("product") Product product);

    @Query("SELECT o from orders o join OrderedItem oi on (oi member of o.orderedItems and oi.product=:product)")
    List<Order> findAllByProduct(@Param("product") Product product);

}