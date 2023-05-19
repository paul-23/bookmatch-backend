package com.redarpa.bookmatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redarpa.bookmatch.dto.User;

public interface IUserDAO extends JpaRepository<User, Long> {

}
