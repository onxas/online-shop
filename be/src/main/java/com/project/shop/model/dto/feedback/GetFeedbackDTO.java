package com.project.shop.model.dto.feedback;

import com.project.shop.model.dto.product.ProductGetInfoDTO;
import com.project.shop.model.dto.user.UserInfoGetDTO;
import com.project.shop.model.entity.Feedback;
import lombok.Builder;
import lombok.Data;


/**
 * DTO для получения отзыва
 *
 * @author Алексей Климов
 */
@Builder
@Data
public class GetFeedbackDTO {

    private UserInfoGetDTO user;

    private ProductGetInfoDTO product;

    private String text;

    private int rating;

    private Long date;

    /**
     * Возвращает DTO, созданное из сущности
     *
     * @param feedback
     * @return
     */
    public static GetFeedbackDTO createFromFeedback(Feedback feedback) {
        return GetFeedbackDTO.builder()
                .date(feedback.getDate().getTime())
                .product(ProductGetInfoDTO.createFromProduct(feedback.getProduct()))
                .rating(feedback.getRating())
                .text(feedback.getText())
                .user(UserInfoGetDTO.createFromUser(feedback.getAuthor()))
                .build();
    }
}
