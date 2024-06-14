package com.example.spring_template.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.context.annotation.Lazy;

@Getter
@Data

public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;

    public void setToken(String token) {
        this.token = token;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
