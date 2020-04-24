package com.project.shop.model.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Роли, существующие в приложении
 *
 * @author Алексей Климов
 */
public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}