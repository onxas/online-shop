package com.project.shop.model.dto.product;

import com.project.shop.model.entity.Category;
import com.project.shop.model.entity.Product;
import lombok.Builder;
import lombok.Data;

/**
 * DTO для получения информации о товаре
 *
 * @author Алексей Климов
 */
@Builder
@Data
public class ProductGetInfoDTO {

    private Long id;
    private String name;
    private String imageFileName;
    private String description;
    private Long price;
    private Long amount;
    private Category category;

    /**
     * Возвращает DTO, созданное из сущности
     *
     * @param product
     * @return
     */
    public static ProductGetInfoDTO createFromProduct(Product product) {
        return ProductGetInfoDTO.builder()
                .amount(product.getAmount())
                .category(product.getCategory())
                .description(product.getDescription())
                .id(product.getId())
                .imageFileName(product.getImageFileName())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

}
