package com.redarpa.bookmatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redarpa.bookmatch.dto.Book;
import com.redarpa.bookmatch.dto.Rating;
import com.redarpa.bookmatch.dto.User;
import com.redarpa.bookmatch.service.BookServiceImp;
import com.redarpa.bookmatch.service.RatingServiceImp;
import com.redarpa.bookmatch.service.UserServiceImp;

/**
 * @author RedArpa - BookMatch
 *
 */

@CrossOrigin(origins = "*", maxAge = 3600) // Allows requests from all origins with 1 hour cache
@RestController
@RequestMapping("/api")
public class RatingController {

	@Autowired
	RatingServiceImp ratingServiceImp;

	@Autowired
	BookServiceImp bookServiceImp;

	@Autowired
	UserServiceImp userServiceImp;

	/**
	 * Get rating average by book Id
	 * @param bookId
	 * @return
	 */
	@GetMapping("/ratings/average/{bookId}")
	public Double getAverageRating(@PathVariable("bookId") Book bookId) {
		Double averageRating = ratingServiceImp.getAverageRating(bookId);
		if (averageRating == null) {
			return 0.0;
		}
		return averageRating;
	}

	/**
	 * Get ratings by book Id
	 * @param bookId
	 * @return
	 */
	@GetMapping("/ratings/{bookId}")
	public List<Rating> getRatingsByBookId(@PathVariable("bookId") Book bookId) {
		return ratingServiceImp.getRatingsByBookId(bookId);
	}

	@GetMapping("/ratings")
	public List<Rating> listAllRatings() {
		return ratingServiceImp.listAllRatings();
	}

	/**
	 * Add new rating
	 * @param ratingRequest
	 * @return
	 */
	@PostMapping("/ratings")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public Rating saveRating(@RequestBody Rating ratingRequest) {
		
		User user = userServiceImp.userById(ratingRequest.getUserRating().getId_user());
		Book book = bookServiceImp.bookById(ratingRequest.getBookRating().getId_book());
		
		if (ratingServiceImp.hasUserRated(user, book)) {
			throw new IllegalStateException("El usuario ya ha realizado un rating para este libro.");
		}
				
		Rating rating = new Rating();
		rating.setUserRating(user);
		rating.setBookRating(book);
		rating.setRating(ratingRequest.getRating());
		rating.setComment(ratingRequest.getComment());

		return ratingServiceImp.saveRating(rating);
	}

//
//	@GetMapping("/ratings/{id}")
//	public Rating ratingById(@PathVariable(name = "id") Long id) {
//
//		Rating ratingById = new Rating();
//
//		ratingById = ratingServiceImp.ratingById(id);
//
//		return ratingById;
//	}

	/**
	 * Update rating by Id
	 * @param id
	 * @param rating
	 * @return
	 */
	@PutMapping("/ratings/{id}")
	public Rating updateRating(@PathVariable(name = "id") Long id, @RequestBody Rating rating) {

		Rating selectedRating = new Rating();
		Rating updatedRating = new Rating();

		selectedRating = ratingServiceImp.ratingById(id);

		selectedRating.setRating(rating.getRating());
		selectedRating.setComment(rating.getComment());

		updatedRating = ratingServiceImp.updateRating(selectedRating);

		return updatedRating;
	}

	/**
	 * Delete rating by Id
	 * @param id
	 */
	@DeleteMapping("/ratings/{id}")
	public void deleteBook(@PathVariable(name = "id") Long id) {
		ratingServiceImp.deleteRating(id);
	}

}
