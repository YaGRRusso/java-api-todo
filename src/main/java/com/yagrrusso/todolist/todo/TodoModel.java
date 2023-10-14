package com.yagrrusso.todolist.todo;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data()
@Entity(name = "tb_todo")
public class TodoModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 36, nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private PriorityEnum priority;

    @CreationTimestamp()
    private LocalDateTime createdAt;

    private UUID userId;

    public void setTitle(String title) throws Exception {
        if (title.length() > 36) {
            throw new Exception("title shouldn't have more than 36 characters");
        }

        this.title = title;
    }
}
