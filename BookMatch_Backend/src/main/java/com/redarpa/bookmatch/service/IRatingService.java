package com.redarpa.bookmatch.service;

import java.util.List;

import com.redarpa.bookmatch.dto.Rating;

public interface IRatingService {
	public List<Rating> listAllRatings(); //List All 
	
	public Rating saveRating(Rating rating); //Save rating
	
	public Rating ratingById(Long id); //Read rating data
	
	public Rating updateRating(Rating rating); //Update rating
	
	public void deleteRating(Long id); //Delete a rating
}
