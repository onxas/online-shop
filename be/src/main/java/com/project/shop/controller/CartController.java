package com.project.shop.controller;

import com.project.shop.model.dto.cart.AddCartItemDTO;
import com.project.shop.model.dto.cart.DeleteCartItemDto;
import com.project.shop.model.dto.cart.GetCartItemDTO;
import com.project.shop.model.entity.Product;
import com.project.shop.model.entity.User;
import com.project.shop.service.CartService;
import com.project.shop.service.ProductService;
import com.project.shop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с корзиной
 *
 * @author Алексей Климов
 */
@Api
@RestController
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    /**
     * Добавляет предмет в корзину пользователя
     *
     * @param principal
     * @param dto
     */
    @ApiOperation(value = "Добавить товар в корзину")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Товар добавлен успешно"),
            @ApiResponse(code = 404, message = "Пользователь или товар не найден"),
            @ApiResponse(code = 404, message = "Неизвестный id регистрации клиента"),
            @ApiResponse(code = 401, message = "Пользователь не аутенфицирован")
    })
    @PostMapping("/cart")
    public GetCartItemDTO addCartItem(@ApiIgnore Principal principal, @RequestBody @Valid AddCartItemDTO dto) {
        User user = userService.getUserByPrincipal(principal);
        Product product = productService.getProductById(dto.getProductId());
        return GetCartItemDTO.createFromCart(cartService.addProduct(user, product, dto.getQuantity()));
    }

    /**
     * Возвращает страницу товаров из корзины пользователя
     *
     * @param principal
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "Показать страницу товаров в корзине")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Страница получена"),
            @ApiResponse(code = 401, message = "")
    })
    @GetMapping("/cart")
    public List<GetCartItemDTO> getCartItems(@ApiIgnore Principal principal, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        User user = userService.getUserByPrincipal(principal);
        return cartService.getCartItems(user, page, size)
                .stream()
                .map(GetCartItemDTO::createFromCart)
                .collect(Collectors.toList());
    }

    /**
     * Удаляет товар из корзины пользователя
     *
     * @param principal
     * @param dto
     */
    @DeleteMapping("/cart")
    public void deleteItem(@ApiIgnore Principal principal, @RequestBody @Valid DeleteCartItemDto dto) {
        User user = userService.getUserByPrincipal(principal);
        Product product = productService.getProductById(dto.getProductId());
        cartService.deleteCartProduct(user, product, dto.getQuantity());
    }

}
