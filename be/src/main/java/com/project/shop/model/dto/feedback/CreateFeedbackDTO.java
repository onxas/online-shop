package com.project.shop.model.dto.feedback;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * DTO для создания отзыва
 *
 * @author Алексей Климов
 */
@Data
public class CreateFeedbackDTO {

    @NotNull
    @Length(max = 255)
    private String text;

    @NotNull
    @Min(1)
    @Max(5)
    private int rating;

}