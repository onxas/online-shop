package com.project.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception для случая, когда у пользователя недсотаточно прав
 *
 * @author Алексей Климов
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotEnoughPermissionsException extends RuntimeException {
    public NotEnoughPermissionsException(String message) {
        super(message);
    }
}
