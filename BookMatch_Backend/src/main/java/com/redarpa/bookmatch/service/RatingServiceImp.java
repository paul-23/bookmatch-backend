package com.redarpa.bookmatch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redarpa.bookmatch.dao.IRatingDAO;
import com.redarpa.bookmatch.dto.Book;
import com.redarpa.bookmatch.dto.Rating;
import com.redarpa.bookmatch.dto.User;

/**
 * @author RedArpa - BookMatch
 *
 */

@Service
public class RatingServiceImp implements IRatingService {
	
	@Autowired
	IRatingDAO iRatingDAO;

	@Override
	public List<Rating> listAllRatings() {
		return iRatingDAO.findAll();
	}

	@Override
	public Rating saveRating(Rating rating) {
		return iRatingDAO.save(rating);
	}

	@Override
	public Rating ratingById(Long id) {
		return iRatingDAO.findById(id).get();
	}

	@Override
	public Rating updateRating(Rating rating) {
		return iRatingDAO.save(rating);
	}

	@Override
	public void deleteRating(Long id) {
		iRatingDAO.deleteById(id);
	}
	
	@Override
    public Double getAverageRating(Book book) {
        List<Rating> ratings = iRatingDAO.findByBookRating(book);
        if (ratings.isEmpty()) {
            return null;
        }
        Double averageRating = ratings.stream()
                .mapToLong(Rating::getRating)
                .average()
                .orElse(0.0);
        return averageRating;
    }
	
	@Override
	public boolean hasUserRated(User userId, Book bookId) {
        return iRatingDAO.existsByUserRatingAndBookRating(userId, bookId);
	}
	
	@Override 
	public List<Rating> getRatingsByBookId(Book book){
		
		return iRatingDAO.findByBookRating(book);
		
	}
	
}
