package com.redarpa.bookmatch.service;

import java.util.List;

import com.redarpa.bookmatch.dto.Book;
import com.redarpa.bookmatch.dto.Rating;

public interface IRatingService {
	public List<Rating> listAllRatings(); //List All 
	
	public Rating saveRating(Rating rating); //Save rating
	
	public Rating ratingById(Long id); //Read rating data
	
	public Rating updateRating(Rating rating); //Update rating
	
	public void deleteRating(Long id); //Delete a rating

	public Double getAverageRating(Book book); //Get average rating of a book by its id

	public List<Rating> getRatingsByBookId(Book book); //Get all rating for a specific book

	

	
}
