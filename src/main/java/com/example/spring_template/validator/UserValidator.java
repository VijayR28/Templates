package com.example.spring_template.validator;

import com.example.spring_template.model.User;
import com.example.spring_template.repository.UserRepository;
import com.example.spring_template.service.JWTServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.validation.Validator;



public class UserValidator {

    @Autowired
    private User user;

    private JWTServiceImpl JwtServiceImpl;

    private UserRepository userRepository;

//    public User createUser(User user) {
//        User savedUser = userRepository.save(user);
//
//        // Generate JWT Token
//
//        String token = JwtServiceImpl.generateToken(savedUser);
//
//        // Set the token in the user object (if needed)
//        savedUser.setToken(token);
//
//        return savedUser;
//    }


}
