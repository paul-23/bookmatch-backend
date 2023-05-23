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

	/**
	 * @return the idRating
	 */
	public Long getIdRating() {
		return idRating;
	}

	/**
	 * @return the userRating
	 */
	public User getUserRating() {
		return userRating;
	}

	/**
	 * @return the bookRating
	 */
	public Book getBookRating() {
		return bookRating;
	}

	/**
	 * @return the rating
	 */
	public Long getRating() {
		return rating;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param idRating the idRating to set
	 */
	public void setIdRating(Long idRating) {
		this.idRating = idRating;
	}

	/**
	 * @param userRating the userRating to set
	 */
	public void setUserRating(User userRating) {
		this.userRating = userRating;
	}

	/**
	 * @param bookRating the bookRating to set
	 */
	public void setBookRating(Book bookRating) {
		this.bookRating = bookRating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(Long rating) {
		this.rating = rating;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}