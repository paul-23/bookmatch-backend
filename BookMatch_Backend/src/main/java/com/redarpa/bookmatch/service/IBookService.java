package com.redarpa.bookmatch.service;

import java.util.List;

import com.redarpa.bookmatch.dto.Book;

/**
 * @author RedArpa - BookMatch
 *
 */

public interface IBookService {
	public List<Book> listAllBooks(); // List All

	public Book saveBook(Book book); // Save book

	public Book bookById(Long id); // Read book data

	public Book bookByIsbn(String isbn); // Read by ISBN

	public Book updateBook(Book book); // Update book

	public void deleteBook(Long id); // Delete a book

	public List<Book> bookByTitle(String title);

	public List<Book> bookByAuthor(String author);
	
	public List<Book> bookByCategory(String category);
}
