package com.example.spring_template.service;

import com.example.spring_template.dto.JwtAuthenticationResponse;
import com.example.spring_template.dto.LoginRequest;
import com.example.spring_template.dto.RefreshToken;
import com.example.spring_template.model.User;

public interface IAuthenticationService {
    User saveEmp(User userEntity);
    public JwtAuthenticationResponse logIn(LoginRequest loginRequest);
    public JwtAuthenticationResponse refresh(RefreshToken refreshToken);
}
