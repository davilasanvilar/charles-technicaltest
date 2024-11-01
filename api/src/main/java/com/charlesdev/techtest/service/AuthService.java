package com.charlesdev.techtest.service;

import javax.management.InstanceNotFoundException;

import org.springframework.stereotype.Service;

import com.charlesdev.techtest.model.User;
import com.charlesdev.techtest.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class AuthService {

    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String username) throws IllegalArgumentException {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        userRepository.save(new User(username));
    }

    public User signIn(String username) throws InstanceNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new InstanceNotFoundException("User not found");
        }
        return user;
    }
}
