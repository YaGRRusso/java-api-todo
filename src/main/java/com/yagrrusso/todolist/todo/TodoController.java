package com.yagrrusso.todolist.todo;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity list() {
        List<TodoModel> todosList = this.todoRepository.findAll();
        return ResponseEntity.ok().body(todosList);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody() TodoModel todo, HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");
        todo.setUserId(userId);

        if (todo.getPriority() == null) {
            todo.setPriority(PriorityEnum.MEDIUM);
        }

        TodoModel createdTodo = this.todoRepository.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }
}
