package com.yagrrusso.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello") // /test/hello
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/ping") // /test/ping
    public String ping() {
        return "Pong";
    }
}
