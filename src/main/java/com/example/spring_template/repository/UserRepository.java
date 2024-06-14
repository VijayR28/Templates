package com.example.spring_template.repository;

import com.example.spring_template.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {
    Optional<User> findByUserName(String username);
    User findByEmail(String email);
}
