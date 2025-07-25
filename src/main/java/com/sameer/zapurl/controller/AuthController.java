package com.sameer.zapurl.controller;

import com.sameer.zapurl.dtos.LoginRequest;
import com.sameer.zapurl.dtos.RegisterRequest;
import com.sameer.zapurl.models.User;
import com.sameer.zapurl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setRole("ROLE_USER");
        user.setPassword(registerRequest.getPassword());

        User user1 = userService.registerUser(user);
        return ResponseEntity.ok("User registered Successfully " + user1.toString());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
}
