package com.project.shop.controller;

import com.project.shop.model.dto.feedback.CreateFeedbackDTO;
import com.project.shop.model.dto.feedback.GetFeedbackDTO;
import com.project.shop.model.dto.product.ProductAddInfoDTO;
import com.project.shop.model.dto.product.ProductGetInfoDTO;
import com.project.shop.model.entity.Category;
import com.project.shop.model.entity.Product;
import com.project.shop.service.FeedbackService;
import com.project.shop.service.ProductService;
import com.project.shop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    /**
     * Возвращает странрицу товаров с фильтрацией по категории и имени
     *
     * @param page
     * @param size
     * @param category
     * @param name
     * @return
     */
    @ApiOperation("Получить все товары")
    @ApiResponse(code = 200, message = "Все существующие товары получены")
    @GetMapping(value = "/product")
    public List<ProductGetInfoDTO> getProductPage(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
                                                  @RequestParam(name = "category", required = false) Category category,
                                                  @RequestParam("search") Optional<String> name) {
        return productService.findPaginated(page, size, category, name)
                .getContent()
                .stream()
                .map(ProductGetInfoDTO::createFromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Вовзращает информацию о товаре
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
    public ProductGetInfoDTO getProduct(@PathVariable("product_id") Long id) {
        return ProductGetInfoDTO.createFromProduct(productService.getProductById(id));
    }

    /**
     * Добавляет новый товар
     *
     * @param dto
     * @param file
     * @return
     * @throws IOException
     */
    @ApiOperation("(ADMIN) Добавить новый товар в БД")
    @ApiResponses(value = {
            @ApiResponse(code = 406, message = "В запросе невалидные данные"),
            @ApiResponse(code = 200, message = "Товар добавлен успешно")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/product")
    public ProductGetInfoDTO addProduct(@RequestPart("json") @Valid ProductAddInfoDTO dto,
                                        @RequestPart("file") MultipartFile file) throws IOException {
        return ProductGetInfoDTO.createFromProduct(productService.saveProductFromDto(dto, file));
    }

    /**
     * Обновляет существующий товар
     *
     * @param id
     * @param dto
     * @param file
     * @throws IOException
     */
    @ApiOperation("(ADMIN) Обновить товар")
    @ApiResponses(value = {
            @ApiResponse(code = 406, message = "В запросе невалидные данные"),
            @ApiResponse(code = 404, message = "Такого товара не существует"),
            @ApiResponse(code = 200, message = "Товар обновлён успешно")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/product/{product_id}")
    public ProductGetInfoDTO changeProduct(@PathVariable("product_id") Long id,
                                           @RequestPart("json") @Valid ProductAddInfoDTO dto,
                                           @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        Product product = productService.getProductById(id);
        return ProductGetInfoDTO.createFromProduct(productService.changeProductFromDto(product, dto, file));
    }

    /**
     * Удаляет товар
     *
     * @param id
     */
    @ApiOperation("(ADMIN) удалить товар")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Такого товара не существует"),
            @ApiResponse(code = 200, message = "Товар удалён успешно")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/product/{product_id}")
    public void deleteProduct(@PathVariable("product_id") Long id) {
        productService.deleteProduct(productService.getProductById(id));
    }

    /**
     * Получает страницу отзывов о товаре
     *
     * @param id
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/product/{product_id}/feedback")
    public List<GetFeedbackDTO> getFeedback(@PathVariable("product_id") Long id,
                                            @RequestParam("page") Optional<Integer> page,
                                            @RequestParam("size") Optional<Integer> size) {
        return feedbackService.getProductFeedback(productService.getProductById(id), page, size)
                .stream()
                .map(GetFeedbackDTO::createFromFeedback)
                .collect(Collectors.toList());
    }


    /**
     * Дообавляет новй отзыв товару
     *
     * @param principal
     * @param id
     * @param createFeedbackDTO
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PostMapping("/product/{product_id}/feedback")
    public GetFeedbackDTO makeFeedback(Principal principal, @PathVariable("product_id") Long id,
                                       @RequestBody CreateFeedbackDTO createFeedbackDTO) {
        return GetFeedbackDTO.createFromFeedback(feedbackService.makeNewFeedback(
                userService.getUserByPrincipal(principal),
                productService.getProductById(id),
                createFeedbackDTO));
    }
}
