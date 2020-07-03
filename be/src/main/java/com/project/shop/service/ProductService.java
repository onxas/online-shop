package com.project.shop.service;

import com.project.shop.exception.ProductNotFoundException;
import com.project.shop.exception.TooManyItemsInCartException;
import com.project.shop.model.dao.ProductRepo;
import com.project.shop.model.dto.product.ProductAddInfoDTO;
import com.project.shop.model.entity.Cart;
import com.project.shop.model.entity.Category;
import com.project.shop.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис для работы с товарами
 *
 * @author Алексей Климов
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private OrderedItemService orderedItemService;

    @Autowired
    private OrderService orderService;

    @Value("${image.file.path}")
    private String imagePath;

    /**
     * Возвращает страницу товаров из бд по категори,имени или без них
     *
     * @param page
     * @param size
     * @param category
     * @return
     */
    public Page<Product> findPaginated(Optional<Integer> page, Optional<Integer> size, Category category, Optional<String> name) {
        return productRepo.findAll(PageRequest.of(page.orElse(0), size.orElse(4)), category, name.orElse(""));
    }

    /**
     * Создаёт и возвращает новый товар
     *
     * @param dto
     * @param file
     * @return
     * @throws IOException
     */
    @Transactional
    public Product saveProductFromDto(ProductAddInfoDTO dto, MultipartFile file) throws IOException {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setAmount(dto.getAmount());
        product.setOrderedTimes(0L);
        product.setRating(0F);
        File imageDir = new File(imagePath);
        if (!imageDir.exists())
            imageDir.mkdir();
        String fileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
        product.setImageFileName(fileName);
        file.transferTo(Paths.get(imagePath + "/" + fileName));
        return productRepo.save(product);
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
     * Меняет существующий товар
     *
     * @param product
     * @param dto
     * @param file
     * @return
     * @throws IOException
     */
    public Product changeProductFromDto(Product product, @Valid ProductAddInfoDTO dto, MultipartFile file) throws IOException {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setAmount(dto.getAmount());
        if (file != null) {
            new File(imagePath + "/" + product.getImageFileName()).delete();
            String fileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
            product.setImageFileName(fileName);
            file.transferTo(Paths.get(imagePath + "/" + fileName));
        }
        return productRepo.save(product);
    }

    /**
     * Удаляет товар
     *
     * @param product
     */
    @Transactional
    public void deleteProduct(Product product) {
        cartService.deleteCartProduct(product);
        feedbackService.deleteProductFeedback(product);
        orderService.deleteProduct(product);
        orderedItemService.deleteItemsWithProduct(product);
        productRepo.delete(product);
        new File(imagePath + "/" + product.getImageFileName()).delete();
    }

    /**
     * Добавляет товар в корзину
     *
     * @param cart
     */
    @Transactional
    public void addProductToCart(Cart cart) {
        Product product = cart.getId().getProduct();
        Long newAmount = product.getAmount() - cart.getQuantity();
        if (newAmount < 0)
            throw new TooManyItemsInCartException("");
        product.setAmount(newAmount);
        productRepo.save(product);
    }

    /**
     * Удаляет товар из корзины
     *
     * @param product
     * @param cartQuantity
     */
    @Transactional
    public void deleteProductFromCart(Product product, Long cartQuantity) {
        product.setAmount(product.getAmount() + cartQuantity);
        productRepo.save(product);
    }

    /**
     * Делает заказ на товары
     *
     * @param carts
     */
    @Transactional
    public void orderProducts(List<Cart> carts) {
        List<Product> products = carts.stream()
                .map(cart -> {
                    Product product = cart.getId().getProduct();
                    product.setOrderedTimes(product.getOrderedTimes() + 1);
                    return product;
                }).collect(Collectors.toList());
        productRepo.saveAll(products);
    }

    /**
     * Возвращает количетсво товаров
     *
     * @return
     */
    public long getProductCount() {
        return productRepo.count();
    }

    /**
     * Возвращает количество доступных товаров
     *
     * @return
     */
    public long getAvailableProductCount() {
        return productRepo.countNonZeroAmount();
    }

    /**
     * Возвращает товар с наибольшим количетсвом заказов
     *
     * @return
     */
    public Product getMostOrderedProduct() {
        return productRepo.findTopByOrderByOrderedTimesDesc().orElseThrow(() -> new ProductNotFoundException(""));
    }

    /**
     * Обновляет рейтинг товара
     *
     * @param product
     * @param newRating
     * @param feedbackTimes
     */
    @Transactional
    public void updateRating(Product product, int newRating, Long feedbackTimes) {
        product.setRating((feedbackTimes * product.getRating() + newRating) / (feedbackTimes + 1));
        productRepo.save(product);
    }

    /**
     * Возвращает товар с наибольшим рейтингом
     *
     * @return
     */
    public Product getMostRatedProduct() {
        return productRepo.findTopByOrderByRatingDesc().orElseThrow(() -> new ProductNotFoundException(""));
    }
}
