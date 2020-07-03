package com.project.shop.exception;

/**
 * Exception для случая, когда в корзине пользователя пусто
 *
 * @author
 */
public class NoItemsInCartException extends RuntimeException {
    public NoItemsInCartException(String message) {
        super(message);
    }
}
