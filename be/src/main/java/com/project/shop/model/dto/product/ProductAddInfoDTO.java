package com.project.shop.model.dto.product;

import com.project.shop.model.entity.Category;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * DTO для добавления/обновления товаров
 *
 * @author Алексей Климов
 */
@Data
@Builder
public class ProductAddInfoDTO {

    @NotBlank
    @Length(max = 255)
    private String name;

    @NotBlank
    @Length(max = 255)
    private String description;

    @NotBlank
    @Length(max = 255)
    private Long price;

    @NotBlank
    @Length(max = 255)
    private Category category;

    @NotBlank
    @Length(max = 255)
    private Long amount;
}
