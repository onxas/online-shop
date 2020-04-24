package com.project.shop.model.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * DTO для изменения информации о пользователе
 *
 * @author Алексей Климов
 */
@Data
public class UserInfoChangeDTO {

    @Email(regexp = "^(?:[a-zA-Z0-9_'^&/+-])" +
            "+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
            "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
            "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
            "+(?:[a-zA-Z]){2,}\\.?)$")
    @Length(max = 40)
    private String email;

    @NotBlank
    @Length(max = 255)
    private String password;

    @NotBlank
    @Length(max = 30)
    private String name;

    @NotBlank
    @Length(max = 255)
    private String picture;

    @NotBlank
    @Length(max = 10)
    private String gender;

    @NotBlank
    @Length(max = 255)
    private String country;

    @NotBlank
    @Length(max = 255)
    private String city;

    @NotBlank
    @Length(max = 255)
    private String street;

    @NotBlank
    @Length(max = 255)
    private String building;

    @NotBlank
    @Length(max = 255)
    private String flat;

    @NotBlank
    @Length(max = 255)
    private String index;
}
