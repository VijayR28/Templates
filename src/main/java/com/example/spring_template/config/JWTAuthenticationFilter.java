package com.example.spring_template.config;

import com.example.spring_template.service.IJWTService;
import com.example.spring_template.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final IJWTService ijwtService;
    @Autowired
    @Lazy
    private  final UserService userService;

    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain)
            throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userName;
        System.out.println("Request to: " + request.getRequestURI());

        if (request.getServletPath().contains("/api/auth/signup")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
    jwt = authHeader.substring(7);
    userName = ijwtService.extractUserName(jwt);

    if(StringUtils.isNoneEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null){
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userName);

        if (ijwtService.isTokenValid(jwt,userDetails)){
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

            UsernamePasswordAuthenticationToken token = new
                    UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            securityContext.setAuthentication(token);
            SecurityContextHolder.setContext(securityContext);
        }
    }
filterChain.doFilter(request,response);
    }
}
