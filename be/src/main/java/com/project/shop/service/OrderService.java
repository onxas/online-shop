package com.project.shop.service;

import com.project.shop.exception.NoItemsInCartException;
import com.project.shop.exception.NotEnoughPermissionsException;
import com.project.shop.exception.OrderNotFoundException;
import com.project.shop.model.dao.OrderRepo;
import com.project.shop.model.dto.order.GetOrderDTO;
import com.project.shop.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для заказов
 *
 * @author Алексей Климов
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderedItemService orderedItemService;

    /**
     * Создаёт и возвращает заказ из списка элементов корзины
     *
     * @param carts
     * @return
     */
    @Transactional
    public Order makeOrder(List<Cart> carts) {
        if (carts == null) throw new NoItemsInCartException("");
        Order newOrder = new Order();
        List<OrderedItem> orderedItems =
                carts.stream()
                        .map(cart -> orderedItemService.createOrderedItemFromCart(cart))
                        .collect(Collectors.toList());
        newOrder.setOrderedItems(orderedItems);
        newOrder.setUser(carts.get(0).getId().getUser());
        newOrder.setOrderDateAndTime(new Date());
        cartService.makeOrder(carts);
        productService.orderProducts(carts);
        return orderRepo.save(newOrder);

    }

    /**
     * Вовзращает страницу заказов пользователя
     *
     * @param user
     * @param page
     * @param size
     * @return
     */
    public List<Order> getUserOrders(User user, Optional<Integer> page, Optional<Integer> size) {
        return orderRepo.findAllByUser(PageRequest.of(page.orElse(0), size.orElse(4)), user).getContent();

    }

    /**
     * Возвращает заказ
     *
     * @param user
     * @param orderId
     * @param page
     * @param size
     * @return
     */
    public GetOrderDTO getOrder(User user, Long orderId, Optional<Integer> page, Optional<Integer> size) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId.toString()));
        if (!order.getUser().equals(user))
            throw new NotEnoughPermissionsException("Для пользователя" + user.toString()
                    + "нет доступа к заказу пользователя с id=" + orderId);
        return GetOrderDTO.createFromOrder(order, PageRequest.of(page.orElse(0), size.orElse(4)));
    }

    /**
     * Проверяет заказывал ли пользователь товар
     *
     * @param user
     * @param product
     * @return
     */
    public boolean isUserOrderedProduct(User user, Product product) {
        return orderRepo.findByUserAndProduct(user, product).isPresent();
    }

    /**
     * Удаляет из заказов товар
     *
     * @param product
     */
    @Transactional
    public void deleteProduct(Product product) {
        List<Order> orders = orderRepo.findAllByProduct(product);
        List<Order> newOrders = orders.stream().map(order -> {
            Order newOrder = new Order();
            newOrder.setOrderDateAndTime(order.getOrderDateAndTime());
            newOrder.setUser(order.getUser());
            newOrder.setId(order.getId());
            List<OrderedItem> newOrderedItems = new ArrayList<>();
            order.getOrderedItems().stream().forEach(orderedItem -> {
                if (!orderedItem.getProduct().equals(product))
                    newOrderedItems.add(orderedItem);
            });
            return newOrder;
        }).collect(Collectors.toList());
        orderRepo.saveAll(newOrders);
    }

}