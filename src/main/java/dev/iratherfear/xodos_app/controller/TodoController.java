package dev.iratherfear.xodos_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.iratherfear.xodos_app.model.Todo;
import dev.iratherfear.xodos_app.model.User;
import dev.iratherfear.xodos_app.repository.TodoRepository;
import dev.iratherfear.xodos_app.repository.UserRepository;
import dev.iratherfear.xodos_app.service.TodoService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/xodos")
public class TodoController {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final TodoService todoService;

    public TodoController(TodoService todoService, TodoRepository todoRepository, UserRepository userRepository) {
        this.todoService = todoService;
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        List<Todo> listOfTodos = todoRepository.findByUser_Username(username);
        return ResponseEntity.ok(listOfTodos);
        // return todoRepository.findByUser_Username(user);
    }    

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoByID(@PathVariable UUID id, Authentication authentication) {
        String username = authentication.getName();
        Todo todo = todoService.getTodoByID(id)
                .orElseThrow(() -> new RuntimeException("No XODOZ found!"));
        // Check ownership
        if (!todo.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No XODOZ found!");
        }
        return ResponseEntity.ok(todo);
    }


    @PostMapping    
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo, Authentication authentication) {
        // todoService.createTodo(todo);
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        newTodo.setUser(user);
        Todo todo = todoService.createTodo(newTodo);
        URI location = URI.create("/api/xodos" + todo.getId());
        return ResponseEntity.created(location).body(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable UUID id, @RequestBody Todo updatedTodo, Authentication authentication) {
        String username = authentication.getName();
        Todo todo = todoService.getTodoByID(id)
                .orElseThrow(() -> new RuntimeException("No XODOZ found!"));
        // Check ownership
        if (!todo.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No XODOZ found!");
        }
       
        Todo updated = todoService.updateTodo(id, updatedTodo);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable UUID id, Authentication authentication) {
        String username = authentication.getName();
        Todo todo = todoService.getTodoByID(id)
                .orElseThrow(() -> new RuntimeException("No XODOZ found!"));
        // Check ownership
        if (!todo.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No XODOZ found!");
        }
       
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    
    }
}
