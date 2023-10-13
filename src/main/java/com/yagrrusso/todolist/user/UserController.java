package com.yagrrusso.todolist.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController()
@RequestMapping("/users")
public class UserController {

    @Autowired()
    private IUserRepository userRepository;

    @GetMapping()
    public ResponseEntity list() {
        var usersList = this.userRepository.findAll();
        return ResponseEntity.ok().body(usersList);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody() UserModel user) {
        var username = this.userRepository.findByUsername(user.getUsername());

        if (username != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user already created");
        }

        var passwordHash = BCrypt.withDefaults().hashToString(8, user.getPassword().toCharArray());
        user.setPassword(passwordHash);

        var createdUser = this.userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
