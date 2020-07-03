package com.project.shop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * Сущность, представляющая элемент корзины
 *
 * @author Алексей Климов
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @EmbeddedId
    private CartPK id;

    private Long quantity;

}