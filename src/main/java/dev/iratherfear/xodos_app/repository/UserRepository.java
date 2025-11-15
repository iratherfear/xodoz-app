package dev.iratherfear.xodos_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.iratherfear.xodos_app.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
