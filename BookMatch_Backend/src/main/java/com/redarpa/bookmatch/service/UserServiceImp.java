package com.redarpa.bookmatch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.redarpa.bookmatch.dao.IUserDAO;
import com.redarpa.bookmatch.dto.User;

public class UserServiceImp implements IUserService {
	
	@Autowired
	IUserDAO iUserDAO;

	@Override
	public List<User> listAllUsers() {
		return iUserDAO.findAll();
	}

	@Override
	public User saveUser(User user) {
		return iUserDAO.save(user);
	}

	@Override
	public User userById(Long id) {
		return iUserDAO.findById(id).get();
	}

	@Override
	public User updateUser(User user) {
		return iUserDAO.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		iUserDAO.deleteById(id);
	}

}
