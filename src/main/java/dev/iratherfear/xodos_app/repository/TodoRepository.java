package dev.iratherfear.xodos_app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.iratherfear.xodos_app.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, UUID> {
    List<Todo> findByUser_Username(String username);
}
