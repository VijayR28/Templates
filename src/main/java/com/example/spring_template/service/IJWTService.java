package com.example.spring_template.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


public interface IJWTService {
   public String extractUserName(String token);

   public String generateToken(UserDetails userDetails);

   boolean isTokenExpired(String token);

   boolean isTokenValid(String token, UserDetails userDetails);
}
