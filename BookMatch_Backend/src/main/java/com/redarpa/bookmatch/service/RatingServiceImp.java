package com.redarpa.bookmatch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redarpa.bookmatch.dao.IRatingDAO;
import com.redarpa.bookmatch.dto.Rating;

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
	
}
