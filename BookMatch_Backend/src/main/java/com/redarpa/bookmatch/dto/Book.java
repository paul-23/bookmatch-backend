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
	
	//---------------------------------------------
	@Column(name = "rating")
	private String rating;
	
	@ManyToOne
	@JoinColumn(name = "message_id")
	private List<Message> message;

	@OneToMany
	@JoinColumn(name = "message_id")
	private List<Message> message;
	
	//manytomany
	@OneToMany
	@JoinColumn(name = "party_id")
	private List<Party> party;

	public Book() {

	}



	public String toString() {
		return "id:" + acc_id + ", username:" + username + 
				", password:" + password + ", email:" + email;
	}
	

	//Para evitar bucles
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "Message")
	public List<Message> getMessage() {
		return message;
	}

	public void setMessage(List<Message> message) {
		this.message = message;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "Party")
	public List<Party> getParty() {
		return party;
	}
//
//	public void setParty(List<Party> party) {
//		this.party = party;
//	}
	
	

}