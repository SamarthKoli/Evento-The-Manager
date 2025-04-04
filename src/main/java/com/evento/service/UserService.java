package com.evento.service;

import com.evento.exception.InvalidEntityException;
import com.evento.model.User;
import com.evento.repository.UserRepository;
import com.evento.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Injected from SecurityConfig

    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Hash password
        return userRepository.save(user);
    }

    public String loginUser(String username, String password) throws InvalidEntityException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidEntityException("User does not exist with username: " + username));
        if (!passwordEncoder.matches(password, user.getPassword())) {  // Verify hashed password
            throw new InvalidEntityException("Invalid password");
        }
        return jwtUtil.generateToken(user.getUsername());  // Return JWT token
    }
}