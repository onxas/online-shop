package com.project.shop.model.dto.statistic;

import com.project.shop.model.dto.product.ProductGetInfoDTO;
import lombok.Builder;
import lombok.Data;

/**
 * DTO для поулчения статистики о товарах
 *
 * @author Алексей Климов
 */
@Builder
@Data
public class ProductStatisticDTO {

    private Long productCount;

    private Long availableCount;

    private ProductGetInfoDTO mostOrderedProduct;

    private ProductGetInfoDTO mostRatedProduct;

}