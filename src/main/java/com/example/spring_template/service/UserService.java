package com.example.spring_template.service;

import com.example.spring_template.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.spring_template.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class UserService {
@Autowired
private UserRepository UserRepository;


//    private final BCryptPasswordEncoder passwordEncoder;
//
//    public UserService(BCryptPasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return (UserDetails) UserRepository.findByUserName(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User name not found"));
            }
        };
    }
    public User createUser(User user) //create User
    {

        return UserRepository.save(user);
    }
    public User searchUser(long userid) //search User
    {
        return UserRepository.findById(userid).orElse(null);
    }
    public User updateUserById( long userid,User User) //updating User details
    {
        User user = (User)UserRepository.findById(userid).orElse(null);
        if(user==null)
        {
            return null;
        }
        else
        {
            user.setUserName(User.getUsername());
            user.setEmail(User.getEmail());
            user.setPassword(User.getPassword());
            user.setUserid(User.getUserid());
            UserRepository.save(user);
            return user;
        }
    }

    public User deleteUser(long userid) //removing the User
    {
        User user= UserRepository.findById(userid).orElse(null);
        if(user!=null)
        {
            UserRepository.delete(user);
            return user;
        }
        else
        {
            return null;
        }
    }

    public List<User> getAllUsers()  //to get all Users
    {
        return UserRepository.findAll();
    }
    public User registerUser(User user) {
        // Check if username or email already exists
        if (UserRepository.findByUserName(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (UserRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Encode password before saving
        user.setPassword(user.getPassword());
        user.setUserName(user.getUsername());
        // Save the user
        return UserRepository.save(user);
    }

}
