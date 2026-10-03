package com.naina.expensemanager.controller;

import com.naina.expensemanager.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.naina.expensemanager.entity.User;
import com.naina.expensemanager.service.UserService;

@RestController
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
	    this.userService = userService;
	}
	@PostMapping("/users")
	public UserResponse registerUser(@Valid @RequestBody User user) {
	    return userService.registerUser(user);
	}
}