package com.yagrrusso.todolist.todo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITodoRepository extends JpaRepository<TodoModel, UUID> {
    List<TodoModel> findByUserId(UUID userId);
}
