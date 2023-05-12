/**
 * 
 */
package com.redarpa.bookmatch.dto;

import java.util.List;

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

/**
 * @author RedArpa - BookMatch
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
	
	@Column(name = "cover_image")
	private byte[] cover_image;
	
	@Column(name = "aviable")
	private boolean aviable;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;

	@ManyToOne
	@JoinColumn(name = "id_editorial")
	private Editorial editorial;
	
	@OneToMany
	@JoinColumn(name = "id_rating")
	private List<Rating> rating;

	public Book() {

	}
	
	public Book(String author, String title, String isbn, String category,
			boolean aviable, User user, Editorial editorial) {
		this.author = author;
		this.title = title;
		this.isbn = isbn;
		this.category = category;
		this.aviable = aviable;
		this.user = user;
		this.editorial = editorial;
	}
	
	public Book(Long id_book, String author, String title, String isbn, String category, byte[] cover_image,
			boolean aviable, User user, Editorial editorial) {
		this.id_book = id_book;
		this.author = author;
		this.title = title;
		this.isbn = isbn;
		this.category = category;
		this.cover_image = cover_image;
		this.aviable = aviable;
		this.user = user;
		this.editorial = editorial;
	}

	public byte[] getCover_image() {
		return cover_image;
	}

	public void setCover_image(byte[] cover_image) {
		this.cover_image = cover_image;
	}

	public boolean getAviable() {
		return aviable;
	}

	public void setAviable(boolean aviable) {
		this.aviable = aviable;
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
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "Ratings")
	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}

}