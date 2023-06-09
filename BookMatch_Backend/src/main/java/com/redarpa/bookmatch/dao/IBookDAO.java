package com.redarpa.bookmatch.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.redarpa.bookmatch.dto.Book;
import com.redarpa.bookmatch.dto.User;

/**
 * @author RedArpa - BookMatch
 *
 */

public interface IBookDAO extends JpaRepository<Book, Long> {
	public List<Book> findBookByIsbnLike(String isbn);
	public List<Book> findBookByTitleLike(String title);
	public List<Book> findBookByAuthorLike(String author);
	public List<Book> findBookByCategoryLike(String category);
	public List<Book> findBooksByUser(User user);
	public Page<Book> findAll(Pageable pageable);
}
