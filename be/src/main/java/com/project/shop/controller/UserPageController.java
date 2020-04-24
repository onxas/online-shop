package com.project.shop.controller;

import com.project.shop.model.dto.user.UserInfoChangeDTO;
import com.project.shop.model.dto.user.UserInfoGetDTO;
import com.project.shop.model.entity.User;
import com.project.shop.service.UserPageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Контроллер для страницы пользователя
 *
 * @author Алексей Климов
 */
@RestController
public class UserPageController {

    @Autowired
    private UserPageService userPageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Возвращает данные о пользователе
     *
     * @param principal
     * @return
     */
    @ApiOperation("Получить данные о пользователе")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Неизвестный Id OAuth2 регистрации клиента"),
            @ApiResponse(code = 404, message = "Такого пользователя не существует"),
            @ApiResponse(code = 200, message = "Данные о пользователе успешно получены")
    })
    @GetMapping("/user")
    public UserInfoGetDTO getUserInfo(@ApiIgnore Principal principal) {
        User userFromDB = userPageService.getUserByPrincipal(principal);
        return UserInfoGetDTO.builder()
                .building(userFromDB.getBuilding())
                .city(userFromDB.getCity())
                .country(userFromDB.getCountry())
                .email(userFromDB.getEmail())
                .flat(userFromDB.getFlat())
                .gender(userFromDB.getGender())
                .index(userFromDB.getIndex())
                .name(userFromDB.getName())
                .picture(userFromDB.getPicture())
                .role(userFromDB.getRole().toString())
                .street(userFromDB.getStreet())
                .build();
    }

    /**
     * Изменяет пользователя в БД
     *
     * @param principal
     * @param dto
     */
    @ApiOperation("Изменить данные о пользователе")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Неизвестный Id OAuth2 регистрации клиента"),
            @ApiResponse(code = 404, message = "Такого пользователя не существует"),
            @ApiResponse(code = 406, message = "В запросе невалидные данные"),
            @ApiResponse(code = 200, message = "Данные о пользователе успешно обновлены")
    })
    @PatchMapping("/user")
    public void changeUserInfo(@ApiIgnore Principal principal, @Valid UserInfoChangeDTO dto) {
        User userFromDb = userPageService.getUserByPrincipal(principal);
        userFromDb.setEmail(dto.getEmail());
        userFromDb.setName(dto.getName());
        userFromDb.setPassword(passwordEncoder.encode(dto.getPassword()));
        userFromDb.setGender(dto.getGender());
        userFromDb.setCountry(dto.getCountry());
        userFromDb.setCity(dto.getCity());
        userFromDb.setStreet(dto.getStreet());
        userFromDb.setBuilding(dto.getBuilding());
        userFromDb.setFlat(dto.getFlat());
    }
}
