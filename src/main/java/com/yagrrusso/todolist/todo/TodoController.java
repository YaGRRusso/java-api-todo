package com.yagrrusso.todolist.todo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/todos")
public class TodoController {

    @Autowired()
    private ITodoRepository todoRepository;

    @GetMapping()
    public ResponseEntity list(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");
        List<TodoModel> todosList = this.todoRepository.findByUserId(userId);
        return ResponseEntity.ok().body(todosList);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody() TodoModel todo, HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");
        todo.setUserId(userId);

        if (todo.getPriority() == null) {
            todo.setPriority(PriorityEnum.MEDIUM);
        }

        if (todo.getStartAt() == null) {
            todo.setStartAt(LocalDateTime.now());
        }

        if (todo.getEndAt() == null) {
            todo.setEndAt(LocalDateTime.now().plusDays(3));
        }

        TodoModel createdTodo = this.todoRepository.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TodoModel todo, HttpServletRequest request, @PathVariable UUID id) {
        UUID userId = (UUID) request.getAttribute("userId");
        Optional<TodoModel> currentTodo = this.todoRepository.findById(id);

        if (currentTodo.isPresent()) {
            if (currentTodo.get().getUserId() == userId) {
                TodoModel updatedTodo = this.todoRepository.save(currentTodo.get());
                return ResponseEntity.ok().body(updatedTodo);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("todo not found");
    }
}
