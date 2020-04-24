package com.project.shop.controller;

import com.project.shop.exception.ClientRegistrationNotFound;
import com.project.shop.model.dao.UserRepo;
import com.project.shop.model.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;
import java.security.Principal;

/**
 * Контроллер для OAuth2
 *
 * @author Алексей Климов
 */
@Api
@RestController
public class OAuth2Controller {

    @Autowired
    private UserRepo userRepo;

    @Value("${reroute.url}")
    private String rerouteURL;

    /**
     * Сохраняет нового пользователя из OAuth2 в БД
     *
     * @param principal
     */
    @ApiOperation("Сохранить нового пользователя из OAuth2 в бд")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Неверный Id регистарции клиента"),
            @ApiResponse(code = 200, message = "Ошибок нет")
    })
    @GetMapping("/registration/oauth2")
    public ResponseEntity<Void> getUserFromOAuth2(@ApiIgnore Principal principal) {
        OAuth2User userFromOauth = ((OAuth2AuthenticationToken) principal).getPrincipal();
        String clientRegistrationId = ((OAuth2AuthenticationToken) principal).getAuthorizedClientRegistrationId();
        String principalName = principal.getName();
        switch (clientRegistrationId) {
            case "google":
                if (userRepo.findByGoogleId(principalName).isEmpty()) {
                    User newUser = new User();
                    newUser.setName(userFromOauth.getAttribute("name"));
                    newUser.setGoogleId(principalName);
                    userRepo.save(newUser);
                }
                break;
            case "yandex":
                if (userRepo.findByYandexId(principalName).isEmpty()) {
                    User newUser = new User();
                    newUser.setName(userFromOauth.getAttribute("real_name"));
                    newUser.setYandexId(principalName);
                    userRepo.save(newUser);
                }
                break;
            default:
                throw new ClientRegistrationNotFound("unknown client registration id" + clientRegistrationId);
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(rerouteURL + "/home.html"));
        return new ResponseEntity<>(responseHeaders, HttpStatus.MOVED_PERMANENTLY);
    }
}
