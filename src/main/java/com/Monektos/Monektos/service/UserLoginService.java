package com.Monektos.Monektos.service;

import com.Monektos.Monektos.dto.SignupResponse;
import com.Monektos.Monektos.exception.UserAlreadyExistsException;
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

    public SignupResponse signUp(AppUser user) {
        Optional<AppUser> foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        AppUser savedUser=userRepository.save(user);
        return new SignupResponse("Account created",200,savedUser); // User created successfully
    }
}
