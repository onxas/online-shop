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

    Optional<User> findByEmail(String email);

    Optional<User> findByGoogleId(String id);

    Optional<User> findByYandexId(String id);
}
