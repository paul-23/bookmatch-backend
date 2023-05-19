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
@Table(name = "messages")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_message;
	
	@Column(name = "content")
	private String content;

	@ManyToOne
	@JoinColumn(name = "id_book")
	private Book book;
	
	@ManyToOne
	@JoinColumn(name = "id_user_origin")
	private User userOrigin;
	
	@ManyToOne
	@JoinColumn(name = "id_user_destiny")
	private User userDestiny;

	public Message() {

	}

	public Message(Long id_message, String content, Book book, User userOrigin, User userDestiny) {
		this.id_message = id_message;
		this.content = content;
		this.book = book;
		this.userOrigin = userOrigin;
		this.userDestiny = userDestiny;
	}

	public Long getId_message() {
		return id_message;
	}

	public String getContent() {
		return content;
	}

	public Book getBook() {
		return book;
	}

	public User getUserOrigin() {
		return userOrigin;
	}

	public User getUserDestiny() {
		return userDestiny;
	}

	public void setId_message(Long id_message) {
		this.id_message = id_message;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void setUserOrigin(User userOrigin) {
		this.userOrigin = userOrigin;
	}

	public void setUserDestiny(User userDestiny) {
		this.userDestiny = userDestiny;
	}

}