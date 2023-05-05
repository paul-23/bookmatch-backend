package com.redarpa.bookmatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redarpa.bookmatch.dto.Book;
import com.redarpa.bookmatch.service.BookServiceImp;

@RestController
@RequestMapping("/api")
public class BookController {

	@Autowired
	BookServiceImp bookServiceImp;

	@GetMapping("/books")
	public List<Book> listBooks() {
		return bookServiceImp.listAllBooks();
	}

	@GetMapping("/books/{id}/rating")
	public double bookRatingById(@PathVariable(name = "id") Long id) {

		Book bookById = new Book();
		
		
		bookById = bookServiceImp.bookById(id);
		double rating_total = bookById.getRating_total();
		double num_rating = bookById.getNum_ratings();
		
		double rating = rating_total / num_rating;

		System.out.println("------------rating ----: " + rating);

		return rating;
	}

	@PostMapping("/books")
	public Book saveBook(@RequestBody Book book) {
		return bookServiceImp.saveBook(book);
	}

	@GetMapping("/books/{id}")
	public Book bookById(@PathVariable(name = "id") Long id) {

		Book bookById = new Book();

		bookById = bookServiceImp.bookById(id);

		System.out.println("Book by Id: " + bookById);

		return bookById;
	}
	
	@PutMapping("/books/{id}/rating")
	public Book updateBookRating(@PathVariable(name = "id") Long id, @RequestBody Book book) {

		Book selectedBook = new Book();
		Book updatedBook = new Book();

		selectedBook = bookServiceImp.bookById(id);
		
		selectedBook.setRating_total(selectedBook.getRating_total() + book.getRating_total());
		selectedBook.setNum_ratings(book.getNum_ratings() + 1);

		updatedBook = bookServiceImp.updateBook(selectedBook);

		System.out.println("Updated book is: " + updatedBook);

		return updatedBook;
	}

	@PutMapping("/books/{id}")
	public Book updateBook(@PathVariable(name = "id") Long id, @RequestBody Book book) {

		Book selectedBook = new Book();
		Book updatedBook = new Book();

		selectedBook = bookServiceImp.bookById(id);

		selectedBook.setAuthor(book.getAuthor());
		selectedBook.setTitle(book.getTitle());
		selectedBook.setIsbn(book.getIsbn());
		selectedBook.setCategory(book.getCategory());
		selectedBook.setUser(book.getUser());
		selectedBook.setEditorial(book.getEditorial());

		updatedBook = bookServiceImp.updateBook(selectedBook);

		System.out.println("Updated book is: " + updatedBook);

		return updatedBook;
	}

	@DeleteMapping("/books/{id}")
	public void deleteBook(@PathVariable(name = "id") Long id) {
		bookServiceImp.deleteBook(id);
	}

}
