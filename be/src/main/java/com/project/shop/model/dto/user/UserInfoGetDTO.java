package com.project.shop.model.dto.user;

import lombok.Builder;
import lombok.Data;

/**
 * DTO для возврата информации о пользователе
 *
 * @author Алексей Климов
 */
@Data
@Builder
public class UserInfoGetDTO {

    private String email;
    private String name;
    private String picture;
    private String gender;
    private String country;
    private String city;
    private String street;
    private String building;
    private String flat;
    private String index;
    private String role;

}
