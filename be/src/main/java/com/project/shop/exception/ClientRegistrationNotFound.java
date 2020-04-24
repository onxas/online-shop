package com.project.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception для случая, когда у OAuth2 ошибка в id регистрации клиента
 *
 * @author Алексей Климоd
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientRegistrationNotFound extends RuntimeException {
    public ClientRegistrationNotFound(String message) {
        super(message);
    }
}
