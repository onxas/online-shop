package com.project.shop.controller;

import com.project.shop.model.dto.user.UserInfoGetDTO;
import com.project.shop.model.dto.user.UserRegistrationDTO;
import com.project.shop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Контроллер аутентификации
 *
 * @author Алексей Климов
 */
@Api
@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * Регестрирует пользователя
     *
     * @param registrationDto
     * @return
     */
    @ApiOperation("Зарегестрировать пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Ошибка в запросе"),
            @ApiResponse(code = 406, message = "Пользователь с таким Email уже существует"),
            @ApiResponse(code = 200, message = "Пользователь зарегестрирован успешно")
    })
    @PostMapping("/registration")
    public UserInfoGetDTO addUser(@RequestBody @Valid UserRegistrationDTO registrationDto) {
        return UserInfoGetDTO.createFromUser(userService.saveUserFromDTO(registrationDto));
    }

}
