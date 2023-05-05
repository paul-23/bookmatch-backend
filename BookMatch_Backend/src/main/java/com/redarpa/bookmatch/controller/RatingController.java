package com.redarpa.bookmatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.redarpa.bookmatch.service.BookServiceImp;
import com.redarpa.bookmatch.service.RatingServiceImp;

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
	
	@GetMapping("/ratings/average/{bookId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long bookId) {
        Book book = new Book();
        book.setId_book(bookId);
        Double averageRating = ratingServiceImp.getAverageRating(book);
        if (averageRating == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(averageRating);
    }

	@GetMapping("/ratings")
	public List<Rating> listAllRatings() {
		return ratingServiceImp.listAllRatings();
	}

	@PostMapping("/ratings")
	public Rating saveRating(@RequestBody Rating rating) {
		return ratingServiceImp.saveRating(rating);
	}

	@GetMapping("/ratings/{id}")
	public Rating ratingById(@PathVariable(name = "id") Long id) {

		Rating ratingById = new Rating();

		ratingById = ratingServiceImp.ratingById(id);

		return ratingById;
	}

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
