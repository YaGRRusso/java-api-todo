package com.yagrrusso.todolist.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/todos")
public class TodoController {

    @Autowired()
    private ITodoRepository todoRepository;

    @GetMapping()
    public ResponseEntity list() {
        var todosList = this.todoRepository.findAll();
        return ResponseEntity.ok().body(todosList);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody() TodoModel todo) {
        var createdTodo = this.todoRepository.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }
}
