package com.project.shop.model.dao;

import com.project.shop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репрозиторий для пользователей
 *
 * @author Алексей Климов
 */
public interface UserRepo extends JpaRepository<User, Long> {

    /**
     * Возвразает пользователя по его почте
     *
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);

    /**
     * Возвращает пользователя по google OAuth
     *
     * @param id
     * @return
     */
    Optional<User> findByGoogleId(String id);

    /**
     * Возвращает пользователя по yandex OAuth
     *
     * @param id
     * @return
     */
    Optional<User> findByYandexId(String id);

    /**
     * Возвращает пользователя по id
     *
     * @param id
     * @return
     */
    Optional<User> findById(Long id);


}
