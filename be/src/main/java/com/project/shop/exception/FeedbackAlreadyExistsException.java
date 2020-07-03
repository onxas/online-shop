package com.project.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception для случая,
 * когда пользователь пытается повторно добавить отзыв к товару
 *
 * @author Алексей Климов
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class FeedbackAlreadyExistsException extends RuntimeException {
    public FeedbackAlreadyExistsException(String message) {
        super(message);
    }
}