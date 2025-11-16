package dev.iratherfear.xodos_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.iratherfear.xodos_app.model.Todo;
import dev.iratherfear.xodos_app.repository.TodoRepository;
import dev.iratherfear.xodos_app.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired private TodoRepository todoRepository;
    @SuppressWarnings("unused")
    @Autowired private UserRepository userRepository;

    @GetMapping("/todos")
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
}
