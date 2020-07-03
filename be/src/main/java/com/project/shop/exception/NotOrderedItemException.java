package com.project.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception для случая, когда пользователь пытается оставить отзыв
 * к товару, котоырй он не заказывал
 *
 * @author Алексей Климов
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotOrderedItemException extends RuntimeException {
    public NotOrderedItemException(String message) {
        super(message);
    }
}
