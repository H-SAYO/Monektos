package com.Monektos.Monektos.service;

import com.Monektos.Monektos.model.AppUser;
import com.Monektos.Monektos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean signUp(AppUser user) {
        Optional<AppUser> foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser.isPresent()) {
            return false; // User already exists
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true; // User created successfully
    }
}
