package com.redarpa.bookmatch.service;

import java.util.List;

import com.redarpa.bookmatch.dto.User;

/**
 * @author RedArpa - BookMatch
 *
 */

public interface IUserService {

	public List<User> listAllUsers(); // List All users

	public User saveUser(User user); // Save user

	public User userById(Long id); // Read user data

	public User updateUser(User user); // Update user

	public void deleteUser(Long id); // Delete an user

}
