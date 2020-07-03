package com.project.shop.controller;

import com.project.shop.model.dto.product.ProductGetInfoDTO;
import com.project.shop.model.dto.statistic.ProductStatisticDTO;
import com.project.shop.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для статистики товаров
 *
 * @author Алексей Климов
 */
@RestController
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    /**
     * Возвращает статистику о товарах
     *
     * @return
     */
    @GetMapping("/statistic")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductStatisticDTO getProductStatistic() {
        return ProductStatisticDTO.builder()
                .productCount(statisticService.getProductCount())
                .availableCount(statisticService.getAvailableProductCount())
                .mostOrderedProduct(ProductGetInfoDTO.createFromProduct(statisticService.getMostOrderedProduct()))
                .mostRatedProduct(ProductGetInfoDTO.createFromProduct(statisticService.getMostRatedProduct()))
                .build();
    }


}