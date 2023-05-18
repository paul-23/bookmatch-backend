package com.redarpa.bookmatch.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redarpa.bookmatch.dto.User;

public interface IUserDAO extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
