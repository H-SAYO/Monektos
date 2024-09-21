package com.Monektos.Monektos.controller;

import com.Monektos.Monektos.dto.ApiResponse;
import com.Monektos.Monektos.dto.SignupResponse;
import com.Monektos.Monektos.exception.UserAlreadyExistsException;
import com.Monektos.Monektos.model.AppUser;
import com.Monektos.Monektos.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserLoginService loginService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody AppUser user) {
        try {
            SignupResponse signup=loginService.signUp(user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(signup);
        }
        catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),HttpStatus.CONFLICT.value()));
        }
    }
}
