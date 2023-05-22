package com.redarpa.bookmatch.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redarpa.bookmatch.dto.Book;
import com.redarpa.bookmatch.dto.Rating;

/**
 * @author RedArpa - BookMatch
 *
 */

public interface IRatingDAO extends JpaRepository<Rating, Long> {
	List<Rating> findByBookRating(Book bookRating);
	//List<Rating> existsByUserRatingAndBookRating(Long userId, Long book);
//	List<Rating> findByBookRating(Long bookId);
	boolean existsByUserRatingAndBookRating(Long userId, Long bookId);
}
