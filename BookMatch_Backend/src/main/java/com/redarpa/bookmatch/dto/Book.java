/**
 * 
 */
package com.redarpa.bookmatch.dto;

import java.util.List;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * @author Marc
 *
 */
@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_book;
	@Column(name = "author")
	private String author;
	@Column(name = "title")
	private String title;
	@Column(name = "isbn")
	private String isbn;
	@Column(name = "category")
	private String category;
	@Column(name = "rating_total")
	private double rating_total;
	@Column(name = "num_ratings")
	private double num_ratings;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;

	@ManyToOne
	@JoinColumn(name = "id_editorial")
	private Editorial editorial;

	public Book() {

	}

	public Book(Long id_book, String author, String title, String isbn, String category, double rating_total,
			double num_ratings, User user, Editorial editorial) {
		super();
		this.id_book = id_book;
		this.author = author;
		this.title = title;
		this.isbn = isbn;
		this.category = category;
		this.rating_total = rating_total;
		this.num_ratings = num_ratings;
		this.user = user;
		this.editorial = editorial;
	}

	public Long getId_book() {
		return id_book;
	}

	public void setId_book(Long id_book) {
		this.id_book = id_book;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getRating_total() {
		return rating_total;
	}

	public void setRating_total(double rating_total) {
		this.rating_total = rating_total;
	}

	public double getNum_ratings() {
		return num_ratings;
	}

	public void setNum_ratings(double num_ratings) {
		this.num_ratings = num_ratings;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Editorial getEditorial() {
		return editorial;
	}

	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}

}