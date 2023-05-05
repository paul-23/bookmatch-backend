package com.redarpa.bookmatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redarpa.bookmatch.dto.Rating;

public interface IRatingDAO extends JpaRepository<Rating, Long> {
	
}
