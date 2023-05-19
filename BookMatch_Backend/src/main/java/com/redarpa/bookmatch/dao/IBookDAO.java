package com.redarpa.bookmatch.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redarpa.bookmatch.dto.Book;

/**
 * @author RedArpa - BookMatch
 *
 */

public interface IBookDAO extends JpaRepository<Book, Long> {
	public Book findBookByIsbn(String isbn);
	public List<Book> findBookByTitleLike(String title);
	public List<Book> findBookByAuthorLike(String author);
	public List<Book> findBookByCategoryLike(String category);
	
}
