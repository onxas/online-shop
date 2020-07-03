package com.project.shop.model.dto.cart;

import com.project.shop.model.dto.product.ProductGetInfoDTO;
import com.project.shop.model.entity.Cart;
import lombok.Builder;
import lombok.Data;

/**
 * DTO для получения элемента корзины
 *
 * @author Алексей Климов
 */
@Builder
@Data
public class GetCartItemDTO {

    private ProductGetInfoDTO product;

    private Long quantity;

    public static GetCartItemDTO createFromCart(Cart cart) {
        return GetCartItemDTO.builder()
                .product(ProductGetInfoDTO.createFromProduct(cart.getId().getProduct()))
                .quantity(cart.getQuantity())
                .build();
    }

}