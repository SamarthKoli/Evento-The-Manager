package com.evento.controller;

import com.evento.exception.InvalidEntityException;
import com.evento.model.User;
import com.evento.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginUser) throws InvalidEntityException {
        String token = userService.loginUser(loginUser.getUsername(), loginUser.getPassword());
        return ResponseEntity.ok(token);  // Return JWT token
    }
}