package com.redarpa.bookmatch.service;

import java.util.List;

import com.redarpa.bookmatch.dto.Book;
import com.redarpa.bookmatch.dto.User;

/**
 * @author RedArpa - BookMatch
 *
 */

public interface IBookService {
	public List<Book> listAllBooks(); // List All

	public Book saveBook(Book book); // Save book

	public Book bookById(Long id); // List book by ID

	public List<Book> bookByIsbn(String isbn); // List book by ISBN

	public Book updateBook(Book book); // Update book

	public void deleteBook(Long id); // Delete a book

	public List<Book> bookByTitle(String title); // List books by title
	
	public List<Book> bookByCategory(String category); // List books by category
  
	public List<Book> bookByAuthor(String author); // List books by author

	List<Book> findBooksByUser(User user); // List books of an User
  
}
