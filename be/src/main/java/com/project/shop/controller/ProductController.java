package com.project.shop.controller;

import com.project.shop.model.dto.product.ProductAddInfoDTO;
import com.project.shop.model.entity.Category;
import com.project.shop.model.entity.Product;
import com.project.shop.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для товаров
 *
 * @author Алексей Климов
 */
@Api
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Возвращает страницу всех ил по категориям товаров
     *
     * @return
     */
    @ApiOperation("Получить все товары")
    @ApiResponse(code = 200, message = "Все существующие товары получены")
    @GetMapping(value = "/product")
    public List<Product> getProductPage(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
                                        @RequestParam(name = "category", required = false) Category category) {
        return productService.findPaginated(page, size, category).getContent();
    }

    /**
     * Возвращает товар по его ID
     *
     * @param id
     * @return
     */
    @ApiOperation("Получить товар по ID")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Такого товара не существует"),
            @ApiResponse(code = 200, message = "Товар получен успешно")
    })
    @GetMapping("/product/{product_id}")
    public Product getProduct(@PathVariable("product_id") Long id) {
        return productService.getProductById(id);
    }

    /**
     * Добавляет новый товар в БД
     *
     * @param dto
     */
    @ApiOperation("(ADMIN) Добавить новый товар в БД")
    @ApiResponses(value = {
            @ApiResponse(code = 406, message = "В запросе невалидные данные"),
            @ApiResponse(code = 200, message = "Товар добавлен успешно")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/product")
    public void addProduct(@RequestBody ProductAddInfoDTO dto) {
        productService.saveProductFromDto(dto);
    }

    /**
     * Обновляет товар в БД
     *
     * @param id
     * @param dto
     */
    @ApiOperation("(ADMIN) Обновить товар")
    @ApiResponses(value = {
            @ApiResponse(code = 406, message = "В запросе невалидные данные"),
            @ApiResponse(code = 404, message = "Такого товара не существует"),
            @ApiResponse(code = 200, message = "Товар обновлён успешно")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/product/{product_id}")
    public void changeProduct(@PathVariable("product_id") Long id, @RequestBody ProductAddInfoDTO dto) {
        Product product = productService.getProductById(id);
        productService.changeProductFromDto(product, dto);
    }

    /**
     * Удаляет товар из БД
     *
     * @param id
     */
    @ApiOperation("(ADMIN) удалить товар")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Такого товара не существует"),
            @ApiResponse(code = 200, message = "Товар удалён успешно")
    })
    @PreAuthorize("hasRole('ADMMIN')")
    @DeleteMapping("/product/{product_id}")
    public void deleteProduct(@PathVariable("product_id") Long id) {
        Product product = productService.getProductById(id);
        productService.deleteProduct(product);
    }
}
