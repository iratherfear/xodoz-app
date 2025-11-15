package dev.iratherfear.xodos_app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import dev.iratherfear.xodos_app.model.Todo;
import dev.iratherfear.xodos_app.repository.TodoRepository;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }   

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Optional<Todo> getTodoByID(UUID id) {
        return todoRepository.findById(id);
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(UUID id, Todo updatedTodo) {
        return todoRepository
            .findById(id).map(todo -> {
                todo.setTitle(updatedTodo.getTitle());
                todo.setDescription(updatedTodo.getDescription());
                todo.setIsCompleted(updatedTodo.getIsCompleted());
                return todoRepository.save(todo);}
            )
            .orElseThrow(() -> new RuntimeException("No TODO found with given ID."));
    }

    public void deleteTodo(UUID id) {
        if(todoRepository.findById(id) == null) {
            throw new RuntimeException("No TODO found with given ID.");
        }
        todoRepository.deleteById(id);
    }
}
