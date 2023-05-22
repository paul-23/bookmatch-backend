package com.redarpa.bookmatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.redarpa.bookmatch.service.UserDetailsImpl;
import com.redarpa.bookmatch.service.UserServiceImp;

/**
 * @author RedArpa - BookMatch
 *
 */

@RestController
@RequestMapping("/api")
public class RatingController {

	@Autowired
	RatingServiceImp ratingServiceImp;

	@Autowired
	BookServiceImp bookServiceImp;

	@Autowired
	UserServiceImp userServiceImp;

	@GetMapping("/ratings/average/{bookId}")
	public Double getAverageRating(@PathVariable("bookId") Long bookId) {
		Book book = new Book();
		book.setId_book(bookId);
		Double averageRating = ratingServiceImp.getAverageRating(book);

		if (averageRating == null) {
			return 0.0;
		}
		return averageRating;
	}

	@GetMapping("/ratings/{bookId}")
	public List<Rating> getRatingsByBookId(@PathVariable("bookId") Long bookId) {
		Book book = new Book();
		book.setId_book(bookId);
		return ratingServiceImp.getRatingsByBookId(book);
	}

	@GetMapping("/ratings")
	public List<Rating> listAllRatings() {
		return ratingServiceImp.listAllRatings();
	}

	/*
	 * @PostMapping("/ratings")
	 * 
	 * @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") public Rating
	 * saveRating(@RequestBody Rating rating) { return
	 * ratingServiceImp.saveRating(rating); }
	 */

	@PostMapping("/ratings")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public Rating saveRating(@RequestBody Rating ratingRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
        	UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long userId = userDetails.getId();

            User user = userServiceImp.userById(userId);
            Book book = bookServiceImp.bookById(ratingRequest.getBookRating());

            if (ratingServiceImp.hasUserRated(user.getId_user(), book.getId_book())) {
                throw new IllegalStateException("El usuario ya ha realizado un rating");
            }

            Rating rating = new Rating();
            rating.setUserRating(user.getId_user());
            rating.setBookRating(book.getId_book());
            rating.setRating(ratingRequest.getRating());
            rating.setComment(ratingRequest.getComment());

            return ratingServiceImp.saveRating(rating);
        }

        throw new AccessDeniedException("No se pudo determinar la identidad del usuario");
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

	@DeleteMapping("/ratings/{id}")
	public void deleteBook(@PathVariable(name = "id") Long id) {
		ratingServiceImp.deleteRating(id);
	}

}
