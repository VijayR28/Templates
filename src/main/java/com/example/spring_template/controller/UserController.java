package com.example.spring_template.controller;

import com.example.spring_template.model.User;
import com.example.spring_template.repository.UserRepository;
import com.example.spring_template.service.JWTServiceImpl;
import com.example.spring_template.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService Userservice;

    @Autowired
    private JWTServiceImpl JwtServiceImpl;

    @GetMapping("/display")
    public List<User> getAllUsers()
    {
        return Userservice.getAllUsers();
    }
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User savedUser = Userservice.createUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception ex) {
            // Handle validation errors
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> searchUserById(@PathVariable Long id) {

        User user = Userservice.searchUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }


@PutMapping("/updateUser/{userId}")
public ResponseEntity<?> updateUserById(@PathVariable Long userId,
                                        @RequestBody User updatedUser) {
    // Authenticate user using token
    User user = Userservice.updateUserById(userId,updatedUser);
    if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    else {
        return ResponseEntity.ok(user);
    }
}
    @DeleteMapping("/deleteUser/{pno}")
    public User deleteUser(@PathVariable long userid)
    {
        return Userservice.deleteUser(userid);
    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userid) {
        // Delete user
        Userservice.deleteUser(userid);
        return ResponseEntity.ok("User deleted successfully");
    }

//    @PostMapping("/register")
//    public ResponseEntity<User> registerUser(@RequestBody User user) {
//        User registeredUser = Userservice.registerUser(user);
//        return ResponseEntity.ok(registeredUser);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<User> loginUser(@RequestParam String email, @RequestParam String password) {
//        User user = Userservice.loginUser(email, password);
//        if (user != null) {
//            return ResponseEntity.ok(user);
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }
}
