/**
 * 
 */
package com.redarpa.bookmatch.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author RedArpa - BookMatch
 *
 */
@Entity
@Table(name = "ratings")
public class Rating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rating")
	private Long idRating;

	@ManyToOne
	@JoinColumn(name = "id_user_rating")
	private User userRating;

	@ManyToOne
	@JoinColumn(name = "id_book_rating")
	private Book bookRating;

	@Column(name = "rating")
	private Long rating;

	@Column(name = "coment")
	private String comment;
	
	public Rating() {
		
	}

	public Rating(Long idRating, User userRating, Book bookRating, Long rating, String comment) {
		this.idRating = idRating;
		this.userRating = userRating;
		this.bookRating = bookRating;
		this.rating = rating;
		this.comment = comment;
	}

	public Long getIdRating() {
		return idRating;
	}

	public void setIdRating(Long idRating) {
		this.idRating = idRating;
	}

	public User getUserRating() {
		return userRating;
	}

	public void setUserRating(User userRating) {
		this.userRating = userRating;
	}

	public Book getBookRating() {
		return bookRating;
	}
	
	public void setBookRating(Book bookRating) {
		this.bookRating = bookRating;
	}

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}