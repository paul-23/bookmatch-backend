package com.redarpa.bookmatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redarpa.bookmatch.dto.User;
import com.redarpa.bookmatch.service.UserServiceImp;

/**
 * @author RedArpa - BookMatch
 *
 */

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserServiceImp userServiceImpl;
	
	@GetMapping("/users")
	public List<User> listUsers() {
		return userServiceImpl.listAllUsers();
	}

	@PostMapping("/users")
	public User saveUser(@RequestBody User user) {
		return userServiceImpl.saveUser(user);
	}

	@GetMapping("/users/{id}")
	public User userById(@PathVariable(name = "id") Long id) {

		User userById = new User();

		userById = userServiceImpl.userById(id);

		return userById;
	}

	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable(name = "id") Long id, @RequestBody User user) {

		User selectedUser = new User();
		User updatedUser = new User();

		selectedUser = userServiceImpl.userById(id);

		selectedUser.setId_user(user.getId_user());
		selectedUser.setUsername(user.getUsername());
		selectedUser.setEmail(user.getEmail());
		selectedUser.setPass(user.getPass());

		updatedUser = userServiceImpl.updateUser(selectedUser);

		return updatedUser;
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable(name = "id") Long id) {
		userServiceImpl.deleteUser(id);
	}
}
