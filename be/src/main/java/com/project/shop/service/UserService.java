package com.project.shop.service;

import com.project.shop.exception.ClientRegistrationNotFound;
import com.project.shop.exception.UserAlreadyExistsException;
import com.project.shop.exception.UserNotFoundException;
import com.project.shop.exception.WrongPasswordException;
import com.project.shop.model.dao.UserRepo;
import com.project.shop.model.dto.user.UserInfoChangeDTO;
import com.project.shop.model.dto.user.UserPasswordChangeDTO;
import com.project.shop.model.dto.user.UserRegistrationDTO;
import com.project.shop.model.entity.Role;
import com.project.shop.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для профиля пользователя
 *
 * @author Алексей Климов
 */
@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Создаёт и возвращает нового пользователя
     *
     * @param registrationDto
     * @return
     */
    @Transactional
    public User saveUserFromDTO(UserRegistrationDTO registrationDto) {
        if (userRepo.findByEmail(registrationDto.getEmail()).isPresent())
            throw new UserAlreadyExistsException(registrationDto.getEmail());
        else {
            User userFromDto = new User();
            userFromDto.setEmail(registrationDto.getEmail());
            userFromDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            userFromDto.setName(registrationDto.getName());
            userFromDto.setRole(Role.USER);
            userFromDto.setGender("UNIDENTIFIED");
            return userRepo.save(userFromDto);
        }
    }

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

    /**
     * Возвращает список всех пользователей на сайте
     *
     * @param page
     * @param size
     * @return
     */
    public List<User> getUsers(Optional<Integer> page, Optional<Integer> size) {
        return userRepo.findAll(PageRequest.of(page.orElse(0), size.orElse(4))).getContent();
    }

    /**
     * Меняет сущетсвующего пользователя
     *
     * @param dto
     * @param user
     * @return
     */
    @Transactional
    public User saveUserFromDto(UserInfoChangeDTO dto, User user) {
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setGender(dto.getGender());
        return userRepo.save(user);
    }

    /**
     * Меняет пароль пользователя
     *
     * @param user
     * @param dto
     */
    @Transactional
    public void changeUserPasswordFromDto(User user, UserPasswordChangeDTO dto) {
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword()))
            throw new WrongPasswordException("error");
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepo.save(user);
    }

    /**
     * Меняет роль пользователя
     *
     * @param id
     * @param role
     * @return
     */
    @Transactional
    public User changeUserRole(Long id, Role role) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new UserNotFoundException(""));
        user.setRole(role);
        return userRepo.save(user);
    }

}
