package com.redarpa.bookmatch.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redarpa.bookmatch.dto.Book;
import com.redarpa.bookmatch.dto.Rating;
import com.redarpa.bookmatch.dto.User;

/**
 * @author RedArpa - BookMatch
 *
 */

public interface IRatingDAO extends JpaRepository<Rating, Long> {
	List<Rating> findByBookRating(Book bookRating);
	boolean existsByUserRatingAndBookRating(User userId, Book bookId);
}
