package com.example.authservice.controller;

import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepo;

    public AuthController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    // ✅ Login endpoint
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        Optional<User> user = userRepo.findByEmail(loginRequest.getEmail());

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email");
        }

        if (!user.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        }

        return ResponseEntity.ok("Login successfull");
    }
}