package com.project.shop.model.dto.user;

import com.project.shop.model.entity.User;
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

    private Long id;
    private String email;
    private String name;
    private String gender;
    private String role;

    /**
     * Возвращает DTO, созданное ищ сущности
     *
     * @param user
     * @return
     */
    public static UserInfoGetDTO createFromUser(User user) {
        return UserInfoGetDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .gender(user.getGender())
                .name(user.getName())
                .role(user.getRole().toString())
                .build();
    }
}
