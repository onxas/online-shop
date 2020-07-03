package com.project.shop.model.dto.cart;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * DTO для добавления товара в корзину
 *
 * @author Алексей Климов
 */
@Data
public class AddCartItemDTO {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    @Max(10)
    private Long quantity;

}