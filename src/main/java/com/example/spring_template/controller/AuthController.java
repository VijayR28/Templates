package com.example.spring_template.controller;

import com.example.spring_template.model.User;
import com.example.spring_template.service.JWTServiceImpl;
import com.example.spring_template.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTServiceImpl jwtTokenUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            User foundUser = userService.createUser(user);
            String token = jwtTokenUtil.generateToken(foundUser);
            return ResponseEntity.ok(token);
        }
        catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            System.out.println("aaa");
            User registeredUser = userService.registerUser(user);

            UserDetails userDetails = new User(registeredUser.getUsername(), registeredUser.getPassword(), new ArrayList<>());

            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            System.out.println("aaa");
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
