/**
 * 
 */
package com.redarpa.bookmatch.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * @author RedArpa - BookMatch
 *
 */

@Entity
@Table(name = "users", // User users table
		uniqueConstraints = { // Make email unique
				@UniqueConstraint(columnNames = "email") })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_user;

	@Column(name = "username")
	private String username;

	@Column(name = "email")
	private String email;

	@Column(name = "pass")
	private String pass;

	@Column(name = "profile_image")
	private byte[] profile_image;
	
	@Column(name = "role_id")
	private String roleId;

	@OneToMany
	@JoinColumn(name = "id_book")
	private List<Book> book;

	@OneToMany
	@JoinColumn(name = "id_message")
	private List<Message> message;

	@OneToMany(mappedBy = "userRating", cascade = CascadeType.ALL)
	private List<Rating> rating;

	public User() {

	}
	
	public User(String username, String email, String pass) {
		this.username = username;
		this.email = email;
		this.pass = pass;
	}

	public User(Long id_user, String username, String email, String pass, byte[] profile_image, List<Book> book,
			List<Message> message, List<Rating> rating) {
		this.id_user = id_user;
		this.username = username;
		this.email = email;
		this.pass = pass;
		this.profile_image = profile_image;
		this.book = book;
		this.message = message;
		this.rating = rating;
	}

	public byte[] getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(byte[] profile_image) {
		this.profile_image = profile_image;
	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "Books")
	public List<Book> getBook() {
		return book;
	}

	public void setBook(List<Book> book) {
		this.book = book;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "Messages")
	public List<Message> getMessage() {
		return message;
	}

	public void setMessage(List<Message> message) {
		this.message = message;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "Ratings")
	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}