package com.project.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception для случая, когда запрос идёт к несуществующему предмету в корзине
 *
 * @author Алексей Климов
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartItemNorFoundException extends RuntimeException {
    public CartItemNorFoundException(String message) {
        super(message);
    }
}
