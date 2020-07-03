package com.project.shop.service;

import com.project.shop.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для статистики товаров
 *
 * @author
 */
@Service
public class StatisticService {

    @Autowired
    private ProductService productService;

    /**
     * Возвращает количетсво товаров
     *
     * @return
     */
    public long getProductCount() {
        return productService.getProductCount();
    }

    /**
     * Возвращает количетсво доступных товаров
     *
     * @return
     */
    public long getAvailableProductCount() {
        return productService.getAvailableProductCount();
    }

    /**
     * Возвращает самый заказываемый товар
     *
     * @return
     */
    public Product getMostOrderedProduct() {
        return productService.getMostOrderedProduct();
    }

    /**
     * Возвращает товар с наивысшим рейтингом
     *
     * @return
     */
    public Product getMostRatedProduct() {
        return productService.getMostRatedProduct();
    }

}