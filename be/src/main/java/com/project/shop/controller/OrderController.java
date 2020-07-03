package com.project.shop.controller;

import com.project.shop.model.dto.order.GetOrderDTO;
import com.project.shop.service.CartService;
import com.project.shop.service.OrderService;
import com.project.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Контроллер для заказов пользователя
 *
 * @author Алексей Климов
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    /**
     * Создаёт новый заказ
     *
     * @param principal
     * @return
     */
    @PostMapping("/order")
    public GetOrderDTO makeOrder(Principal principal) {
        return GetOrderDTO.createFromOrder(orderService.makeOrder(cartService.getCartItems(userService.getUserByPrincipal(principal))));
    }

    /**
     * Возвращает старницу заказов пользователя
     *
     * @param principal
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/order")
    public List<GetOrderDTO> getOrders(Principal principal,
                                       @RequestParam("page") Optional<Integer> page,
                                       @RequestParam("size") Optional<Integer> size) {
        return orderService.getUserOrders(userService.getUserByPrincipal(principal),
                page, size)
                .stream()
                .map(GetOrderDTO::createFromOrder)
                .collect(Collectors.toList());
    }

    /**
     * Вовзращает заказ пользователя
     *
     * @param principal
     * @param orderId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/order/{order_id}")
    public GetOrderDTO getOrder(Principal principal, @PathVariable("order_id") Long orderId,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        return orderService.getOrder(userService.getUserByPrincipal(principal), orderId, page, size);
    }

}