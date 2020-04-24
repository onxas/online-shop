package com.project.shop.service;

import com.project.shop.exception.ProductNotFoundException;
import com.project.shop.model.dao.ProductRepo;
import com.project.shop.model.dto.product.ProductAddInfoDTO;
import com.project.shop.model.entity.Category;
import com.project.shop.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Сервис для работы с товарами
 *
 * @author Алексей Климов
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    /**
     * Возвращает страницу всех или по категориям товаров из бд
     *
     * @param page
     * @param size
     * @param category
     * @return
     */
    public Page<Product> findPaginated(Optional<Integer> page, Optional<Integer> size, Category category) {
        return productRepo.findAllByCategory(PageRequest.of(page.orElse(0), size.orElse(4)), category);
    }

    /**
     * Сохраняет новы товар из DTO
     *
     * @param dto
     */
    public void saveProductFromDto(@Valid ProductAddInfoDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPict(dto.getImage());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(Category.valueOf(dto.getCategory()));
        productRepo.save(product);
    }

    /**
     * Вовзращает товар по Id
     *
     * @param id
     * @return
     */
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("can`t find product with id= " + id.toString()));
    }

    /**
     * Меняет товар по DTO
     *
     * @param product
     * @param dto
     */
    public void changeProductFromDto(Product product, @Valid ProductAddInfoDTO dto) {
        product.setName(dto.getName());
        product.setPict(dto.getImage());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(Category.valueOf(dto.getCategory()));
        productRepo.save(product);
    }

    /**
     * Удаляет товар
     *
     * @param product
     */
    public void deleteProduct(Product product) {
        productRepo.delete(product);
    }
}
