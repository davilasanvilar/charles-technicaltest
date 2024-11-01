package com.charlesdev.techtest.controller;

import javax.management.InstanceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charlesdev.techtest.form.SignInForm;
import com.charlesdev.techtest.form.SignUpForm;
import com.charlesdev.techtest.model.User;
import com.charlesdev.techtest.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpForm signUpForm) {
        try {
            authService.signUp(signUpForm.getUsername());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<User> signIn(@RequestBody @Valid SignInForm signInForm) {
        try {
            User user = authService.signIn(signInForm.getUsername());
            return ResponseEntity.ok().body(user);
        } catch (InstanceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}