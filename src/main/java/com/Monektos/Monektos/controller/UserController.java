package com.Monektos.Monektos.controller;

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
    public ResponseEntity<AppUser> signUp(@RequestBody AppUser user) {
        if(loginService.signUp(user)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
