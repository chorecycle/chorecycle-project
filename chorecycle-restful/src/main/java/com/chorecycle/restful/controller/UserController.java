package com.chorecycle.restful.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chorecycle.model.User;
import com.chorecycle.restful.repository.UserRepository;

/**
 * Maps endpoints for data pertaining to the {@link User} entity.
 */
@RestController
public class UserController {
	private final UserRepository userRepo;

	/**
	 * Autowired constructor for use by the framework.
	 * @param userRepo - Spring should inject an auto-configured {@link UserRepository}
	 */
	public UserController(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@GetMapping("/user")
	public List<User> listAllUsers() {
		return userRepo.findAll();
	}
}