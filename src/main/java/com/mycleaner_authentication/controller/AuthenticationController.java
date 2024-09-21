package com.mycleaner_authentication.controller;

import com.mycleaner_authentication.model.User;
import com.mycleaner_authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        log.info("Fetching user with username: {}", username);
        User user = userService.findUserByUsername(username);

        if (user == null) {
            log.warn("User not found for username: {}", username);
            ErrorResponse errorResponse = new ErrorResponse(
                    "USER_NOT_FOUND",
                    "User not found with username: " + username,
                    HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        log.info("User found: {}", user);
        return ResponseEntity.ok(user);
    }
}

