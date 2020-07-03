package com.project.shop.service;

import com.project.shop.model.dao.OrderedItemRepo;
import com.project.shop.model.entity.Cart;
import com.project.shop.model.entity.OrderedItem;
import com.project.shop.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для элементов заказов
 *
 * @author Алексей Климов
 */
@Service
public class OrderedItemService {

    @Autowired
    private OrderedItemRepo orderedItemRepo;

    /**
     * Создаёт элемент заказа из элемента корзины
     *
     * @param cart
     * @return
     */
    @Transactional
    public OrderedItem createOrderedItemFromCart(Cart cart) {
        OrderedItem orderedItem = new OrderedItem();
        orderedItem.setProduct(cart.getId().getProduct());
        orderedItem.setQuantity(cart.getQuantity());
        return orderedItemRepo.save(orderedItem);
    }

    @Transactional
    public void deleteItemsWithProduct(Product product) {
        orderedItemRepo.deleteAllByProduct(product);
    }
}