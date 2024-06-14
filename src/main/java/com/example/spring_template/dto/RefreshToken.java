package com.example.spring_template.dto;

import lombok.Data;
import org.springframework.context.annotation.Lazy;

@Data
@Lazy
public class RefreshToken {
    private String token;
}