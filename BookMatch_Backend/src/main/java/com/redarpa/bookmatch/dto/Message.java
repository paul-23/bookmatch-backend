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
 * @author Marc
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
	
	
	@Column(name = "id_book")
	private String id_book;
	@Column(name = "id_user_origin")
	private String id_user_origin;
	@Column(name = "id_user_destiny")
	private String id_user_destiny;

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

	public Message(Long id_message, String content, String id_book, String id_user_origin, String id_user_destiny) {
		super();
		this.id_message = id_message;
		this.content = content;
		this.id_book = id_book;
		this.id_user_origin = id_user_origin;
		this.id_user_destiny = id_user_destiny;
	}

	public Long getId_message() {
		return id_message;
	}

	public void setId_message(Long id_message) {
		this.id_message = id_message;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId_book() {
		return id_book;
	}

	public void setId_book(String id_book) {
		this.id_book = id_book;
	}

	public String getId_user_origin() {
		return id_user_origin;
	}

	public void setId_user_origin(String id_user_origin) {
		this.id_user_origin = id_user_origin;
	}

	public String getId_user_destiny() {
		return id_user_destiny;
	}

	public void setId_user_destiny(String id_user_destiny) {
		this.id_user_destiny = id_user_destiny;
	}
	


}