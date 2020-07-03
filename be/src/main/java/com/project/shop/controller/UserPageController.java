package com.project.shop.controller;

import com.project.shop.model.dto.user.UserInfoChangeDTO;
import com.project.shop.model.dto.user.UserInfoGetDTO;
import com.project.shop.model.dto.user.UserPasswordChangeDTO;
import com.project.shop.model.entity.Role;
import com.project.shop.model.entity.User;
import com.project.shop.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.Console;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Контроллер для страницы пользователя
 *
 * @author Алексей Климов
 */
@RestController
public class UserPageController {

    @Autowired
    private UserService userPageService;

    /**
     * Вовзращает информацию о пользоваетеле
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
        return UserInfoGetDTO.createFromUser(userPageService.getUserByPrincipal(principal));
    }

    /**
     * Возвращает списко всех пользоваетелей
     *
     * @param page
     * @param size
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public List<UserInfoGetDTO> getUsersInfo(@RequestParam("page") Optional<Integer> page,
                                             @RequestParam("size") Optional<Integer> size) {
        return userPageService.getUsers(page, size).stream()
                .map(UserInfoGetDTO::createFromUser)
                .collect(Collectors.toList());
    }

    /**
     * Изменяет существующего пользователя
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
    public UserInfoGetDTO changeUserInfo(@ApiIgnore Principal principal, @RequestBody @Valid UserInfoChangeDTO dto) {
        User userFromDb = userPageService.getUserByPrincipal(principal);
        return UserInfoGetDTO.createFromUser(userPageService.saveUserFromDto(dto, userFromDb));
    }

    /**
     * Меняет роль пользователя
     *
     * @param id
     * @param role
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/users/{user_id}")
    public UserInfoGetDTO changeUserRole(@PathVariable("user_id") Long id, @RequestParam("role") Role role) {
        return UserInfoGetDTO.createFromUser(userPageService.changeUserRole(id, role));
    }


    /**
     * Меняет пароль пользователя
     *
     * @param principal
     * @param dto
     */
    @PatchMapping("/user/password")
    public void changeUserPassword(@ApiIgnore Principal principal, @RequestBody @Valid UserPasswordChangeDTO dto) {
        User userFromDb = userPageService.getUserByPrincipal(principal);
        userPageService.changeUserPasswordFromDto(userFromDb, dto);
    }
}
