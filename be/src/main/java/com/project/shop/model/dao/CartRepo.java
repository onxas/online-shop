package com.project.shop.model.dao;

import com.project.shop.model.entity.Cart;
import com.project.shop.model.entity.CartPK;
import com.project.shop.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для корзин пользователей
 *
 * @author Алексей Климов
 */
public interface CartRepo extends JpaRepository<Cart, CartPK> {

    /**
     * Возвращает страницу товаров в корзине пользователя
     *
     * @param id
     * @param pageable
     * @return
     */
    Page<Cart> findAllByIdUserId(Long id, Pageable pageable);

    /**
     * Удаляет все элементы корзины с определённым товаром
     *
     * @param product
     */
    void deleteAllByIdProduct(Product product);

}
