package com.project.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception для случая, когда в корзигне пользователя слишком много товаров
 *
 * @author Алексей Климов
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TooManyItemsInCartException extends RuntimeException {
    public TooManyItemsInCartException(String message) {
        super(message);
    }
}
