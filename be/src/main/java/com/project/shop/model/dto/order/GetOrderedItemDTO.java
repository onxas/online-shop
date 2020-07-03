package com.project.shop.model.dto.order;

import com.project.shop.model.dto.product.ProductGetInfoDTO;
import com.project.shop.model.entity.OrderedItem;
import lombok.Builder;
import lombok.Data;

/**
 * DTO для элемента заказа
 *
 * @author Алексей Климов
 */
@Builder
@Data
public class GetOrderedItemDTO {

    private ProductGetInfoDTO product;

    private Long quantity;

    /**
     * Возвращает DTO, созданное из сущности
     *
     * @param orderedItem
     * @return
     */
    public static GetOrderedItemDTO createFromOrderedItem(OrderedItem orderedItem) {
        return GetOrderedItemDTO.builder()
                .product(ProductGetInfoDTO.createFromProduct(orderedItem.getProduct()))
                .quantity(orderedItem.getQuantity())
                .build();
    }
}