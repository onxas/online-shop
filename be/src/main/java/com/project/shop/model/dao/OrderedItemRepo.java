package com.project.shop.model.dao;

import com.project.shop.model.entity.OrderedItem;
import com.project.shop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для предметов заказа
 *
 * @author Алексей Климов
 */
public interface OrderedItemRepo extends JpaRepository<OrderedItem, Long> {

    /**
     * Возвращает элемент заказа по товару
     *
     * @param product
     * @return
     */
    Optional<OrderedItem> findByProduct(Product product);

    /**
     * Удаляет все элементы заказа с товаром
     *
     * @param product
     */
    void deleteAllByProduct(Product product);

}
