package com.project.shop.service;

import com.project.shop.exception.CartItemNorFoundException;
import com.project.shop.exception.TooManyItemsInCartException;
import com.project.shop.model.dao.CartRepo;
import com.project.shop.model.entity.Cart;
import com.project.shop.model.entity.CartPK;
import com.project.shop.model.entity.Product;
import com.project.shop.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с корзиной
 *
 * @author Алексей Климов
 */
@Service
public class CartService {

    private final static int MAX_CART_ITEM_QUANTITY = 5;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductService productService;

    /**
     * Добавляет товар в корзину пользователя
     *
     * @param user
     * @param product
     * @param quantity
     */
    @Transactional
    public Cart addProduct(User user, Product product, Long quantity) {
        Cart cart = cartRepo.findById(new CartPK(user, product)).orElse(new Cart(new CartPK(user, product), (long) 0));
        Long newQuantity = cart.getQuantity() + quantity;
        if (newQuantity > MAX_CART_ITEM_QUANTITY)
            throw new TooManyItemsInCartException(String.valueOf(newQuantity));
        Cart addingCart = new Cart(new CartPK(user, product), quantity);
        productService.addProductToCart(addingCart);
        cart.setQuantity(newQuantity);
        return cartRepo.save(cart);
    }

    /**
     * Возвращает страницу товаров в корзине пользователя
     *
     * @param user
     * @param page
     * @param size
     * @return
     */
    public List<Cart> getCartItems(User user, Optional<Integer> page, Optional<Integer> size) {
        return cartRepo.findAllByIdUserId(user.getId(), PageRequest.of(page.orElse(0), size.orElse(4))).getContent();
    }

    /**
     * Удаляет товар из корзины
     *
     * @param user
     * @param product
     * @param deleteQuantity
     */
    @Transactional
    public void deleteCartProduct(User user, Product product, Long deleteQuantity) {
        Cart cart = cartRepo.findById(new CartPK(user, product)).orElseThrow(() -> new CartItemNorFoundException(""));
        Long newQuantity = cart.getQuantity() - deleteQuantity;
        if (newQuantity <= 0) {
            productService.deleteProductFromCart(product, cart.getQuantity());
            cartRepo.delete(cart);
        } else {
            productService.deleteProductFromCart(product, deleteQuantity);
            cart.setQuantity(newQuantity);
            cartRepo.save(cart);
        }
    }

    /**
     * Удаляет все элементы корзины с товаром
     *
     * @param product
     */
    @Transactional
    public void deleteCartProduct(Product product) {
        cartRepo.deleteAllByIdProduct(product);
    }

    /**
     * Возвращает все товары в корзине пользователя
     *
     * @param user
     * @return
     */
    public List<Cart> getCartItems(User user) {
        return cartRepo.findAllByIdUserId(user.getId(), null).getContent();
    }

    /**
     * Очищает корзину пользователя, который делает заказ
     *
     * @param carts
     */
    @Transactional
    public void makeOrder(List<Cart> carts) {
        cartRepo.deleteAll(carts);
    }
}
