package com.example.spring_template.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.spring_template.config.JWTAuthenticationFilter;
import com.example.spring_template.dto.JwtAuthenticationResponse;
import com.example.spring_template.dto.LoginRequest;
import com.example.spring_template.dto.RefreshToken;
import com.example.spring_template.model.Role;
import com.example.spring_template.model.User;
import com.example.spring_template.service.IAuthenticationService;
import com.example.spring_template.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTServiceImpl jwtService;
    @Override
    public User saveEmp(User userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(Role.USER);
        return userRepository.save(userEntity);
    }

    @Override
    public JwtAuthenticationResponse logIn(LoginRequest loginRequest){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                loginRequest.getPassword()));
        var user = userRepository.findByUserName(loginRequest.getUserName()).orElseThrow(()-> new IllegalArgumentException("Username password Invalid"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;

    }

    @Override
    public JwtAuthenticationResponse refresh(RefreshToken refreshToken){
        var userName = jwtService.extractUserName(refreshToken.getToken());
        var user = userRepository.findByUserName(userName).orElseThrow(()-> new IllegalArgumentException("Token not vaild for user"));
        if(jwtService.isTokenValid(refreshToken.getToken(), user)){
            var jwt = jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
