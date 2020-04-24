package com.project.shop.service;

import com.project.shop.exception.ClientRegistrationNotFound;
import com.project.shop.exception.UserNotFoundException;
import com.project.shop.model.dao.UserRepo;
import com.project.shop.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * Сервис для профиля пользователя
 *
 * @author Алексей Климов
 */
@Service
public class UserPageService {

    @Autowired
    private UserRepo userRepo;


    /**
     * Возвращает пользователя по его текущей сесси на сайте
     *
     * @param principal
     * @return
     */
    public User getUserByPrincipal(Principal principal) {
        String name = principal.getName();
        if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2User oAuth2User = ((OAuth2AuthenticationToken) principal).getPrincipal();
            String clientRegistrationId = ((OAuth2AuthenticationToken) principal).getAuthorizedClientRegistrationId();
            switch (clientRegistrationId) {
                case "google":
                    return userRepo.findByGoogleId(name).orElseThrow(() -> new UserNotFoundException("can`t find google id= " + name));
                case "yandex":
                    return userRepo.findByYandexId(name).orElseThrow(() -> new UsernameNotFoundException("can`t find yandex id= " + name));
                default:
                    throw new ClientRegistrationNotFound("unknown client id= " + clientRegistrationId);
            }
        }
        return userRepo.findByEmail(name).orElseThrow(() -> new UsernameNotFoundException("can`t find with email=" + name));
    }

}
