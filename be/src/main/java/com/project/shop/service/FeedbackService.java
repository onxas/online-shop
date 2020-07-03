package com.project.shop.service;

import com.project.shop.exception.FeedbackAlreadyExistsException;
import com.project.shop.exception.NotOrderedItemException;
import com.project.shop.model.dao.FeedbackRepo;
import com.project.shop.model.dto.feedback.CreateFeedbackDTO;
import com.project.shop.model.entity.Feedback;
import com.project.shop.model.entity.Product;
import com.project.shop.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для отзывов
 *
 * @author Алексей Климов
 */
@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepo feedbackRepo;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    /**
     * Возвращает страницу отзывок к товару
     *
     * @param product
     * @param page
     * @param size
     * @return
     */
    public List<Feedback> getProductFeedback(Product product, Optional<Integer> page, Optional<Integer> size) {
        return feedbackRepo.findAllByProductOrderByDate(product,
                PageRequest.of(page.orElse(0), size.orElse(4))).getContent();
    }

    /**
     * Создаёт новый отзыв
     *
     * @param user
     * @param product
     * @param dto
     * @return
     */
    @Transactional
    public Feedback makeNewFeedback(User user, Product product, CreateFeedbackDTO dto) {
        if (!orderService.isUserOrderedProduct(user, product))
            throw new NotOrderedItemException("");
        if (feedbackRepo.findByAuthorAndProduct(user, product).isPresent())
            throw new FeedbackAlreadyExistsException("");
        productService.updateRating(product, dto.getRating(), feedbackRepo.countByProduct(product));
        Feedback feedback = new Feedback();
        feedback.setAuthor(user);
        feedback.setProduct(product);
        feedback.setRating(dto.getRating());
        feedback.setText(dto.getText());
        feedback.setDate(new Date());
        return feedbackRepo.save(feedback);
    }

    @Transactional
    public void deleteProductFeedback(Product product) {
        feedbackRepo.deleteAllByProduct(product);
    }
}
