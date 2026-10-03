package com.naina.expensemanager.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.naina.expensemanager.dto.UserResponse;
import com.naina.expensemanager.entity.User;
import org.springframework.stereotype.Service;
import com.naina.expensemanager.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserResponse registerUser(User user) {
    	if (userRepository.existsByEmail(user.getEmail())) {
    	    throw new IllegalArgumentException("Email already exists");
    	}
    	user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();

        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());

        return response;
    }

}