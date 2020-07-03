package com.project.shop.model.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * DTO для изменения информации о пользователе
 *
 * @author Алексей Климов
 */
@Data
public class UserInfoChangeDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String gender;

}
