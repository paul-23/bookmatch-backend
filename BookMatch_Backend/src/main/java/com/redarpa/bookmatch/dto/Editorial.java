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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @author RedArpa - BookMatch
 *
 */
@Entity
@Table(name = "editorials")
public class Editorial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_editorial;
	
	@Column(name = "name_editorial")
	private String name_editorial;

	@OneToMany
	@JoinColumn(name = "id_book")
	private List<Book> book;

	public Editorial() {

	}

	public Editorial(Long id_editorial, String name_editorial, List<Book> book) {
		this.id_editorial = id_editorial;
		this.name_editorial = name_editorial;
		this.book = book;
	}

	public Long getId_editorial() {
		return id_editorial;
	}

	public void setId_editorial(Long id_editorial) {
		this.id_editorial = id_editorial;
	}

	public String getName_editorial() {
		return name_editorial;
	}

	public void setName_editorial(String name_editorial) {
		this.name_editorial = name_editorial;
	}

	//Apunta al dto o al sql?
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "Books")
	public List<Book> getBook() {
		return book;
	}

	public void setBook(List<Book> book) {
		this.book = book;
	}
}