package com.project.shop.controller;

import com.project.shop.exception.UserAlreadyExistsException;
import com.project.shop.model.dao.UserRepo;
import com.project.shop.model.dto.user.UserRegistrationDTO;
import com.project.shop.model.entity.Role;
import com.project.shop.model.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.*;
import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Контроллер для аунтификации
 *
 * @author Алексей Климов
 */
@Api
@RestController
public class AuthController {

    /**
     * URL фронтенда
     */
    @Value("${reroute.url}")
    private String rerouteURL;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public void addUser(@RequestBody @Valid UserRegistrationDTO registrationDto) {
        if (userRepo.findByEmail(registrationDto.getEmail()).isPresent())
            throw new UserAlreadyExistsException(registrationDto.getEmail());
        else {
            User userFromDto = new User();
            userFromDto.setEmail(registrationDto.getEmail());
            userFromDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            userFromDto.setName(registrationDto.getName());
            userFromDto.setRole(Role.USER);
            userRepo.save(userFromDto);
        }
    }

    @GetMapping("/")
    public String getTest() {
        return "zaebumba";
    }
}
