package com.project.shop.model.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * DTO для изменения пароля пользователя
 *
 * @author Алексей Климов
 */
@Data
public class UserPasswordChangeDTO {

    @NotBlank
    @Length(min = 8)
    private String oldPassword;

    @NotBlank
    @Length(min = 8)
    private String newPassword;

}