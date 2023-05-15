package com.redarpa.bookmatch.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

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
import com.redarpa.bookmatch.service.RatingServiceImp;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author RedArpa - BookMatch
 *
 */
@RestController
@RequestMapping("/api")
public class BookController {
	
	private final String OPEN_LIBRARY_API = "https://covers.openlibrary.org/b/isbn/ISBN_NUMBER-L.jpg";

	@Autowired
	BookServiceImp bookServiceImp;

	@Autowired
	RatingServiceImp ratingServiceImp;

	@GetMapping("/books")
	public List<Book> listBooks() {
		return bookServiceImp.listAllBooks();
	}

	@PostMapping("/book")
	public Book saveBook(@RequestBody Book book) {
		return bookServiceImp.saveBook(book);
	}
	
	@GetMapping("/book/{id}")
	public Book bookById(@PathVariable(name = "id") Long id) throws IOException {

		Book bookById = new Book();
		bookById = bookServiceImp.bookById(id);

		checkCover(bookById);

		return bookById;
	}
	
	
	public void checkCover(Book book) {
		if (book.getCover_image() == null || book.getCover_image().equals(null) || book.getCover_image().equals("")) {
			saveCoverByBookISBN(book.getId_book(), book.getIsbn());
		}
	}

	@GetMapping("book/image/{id}")
	public void getCoverByBookId(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Book bookOptional = bookServiceImp.bookById(id);
        response.getOutputStream().write(bookOptional.getCover_image());
    }

	@PutMapping("/book/image/{id}")
	public Book saveCoverByBookId(@PathVariable(name = "id") Long id, @RequestBody String url) throws IOException {

		URL imageUrl = new URL(url);
		BufferedImage bufferedImage = ImageIO.read(imageUrl);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", outputStream);
		byte[] imageBytes = outputStream.toByteArray();

		Book bookById = new Book();
		bookById = bookServiceImp.bookById(id);
		bookById.setCover_image(imageBytes);

		bookServiceImp.saveImage(bookById);

		return bookById;
	}
	
	public void saveCoverByBookISBN(Long id, String isbn) {
		try {
			String url = OPEN_LIBRARY_API.replace("ISBN_NUMBER", isbn);
			URL imageUrl = new URL(url);
			BufferedImage bufferedImage = ImageIO.read(imageUrl);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", outputStream);
			byte[] imageBytes = outputStream.toByteArray();

			Book bookById = new Book();
			bookById = bookServiceImp.bookById(id);
			bookById.setCover_image(imageBytes);

			bookServiceImp.saveImage(bookById);

		} catch (Exception e) {
			try {
	            String defaultImagePath = "default_cover.jpg";
	            
	            BufferedImage defaultImage = ImageIO.read(getClass().getClassLoader().getResource(defaultImagePath));
	            
	            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	            ImageIO.write(defaultImage, "jpg", outputStream);
	            byte[] defaultImageBytes = outputStream.toByteArray();

	            Book bookById = bookServiceImp.bookById(id);
	            bookById.setCover_image(defaultImageBytes);

	            bookServiceImp.saveImage(bookById);
	        } catch (Exception ex) {
	            System.out.println("Ocurrió un error al asignar la imagen por defecto: " + ex.getMessage());
	        }
		}		
	}

	@PutMapping("/book/{id}")
	public Book updateBook(@PathVariable(name = "id") Long id, @RequestBody Book book) {

		Book selectedBook = new Book();
		Book updatedBook = new Book();

		selectedBook = bookServiceImp.bookById(id);

		selectedBook.setAuthor(book.getAuthor());
		selectedBook.setTitle(book.getTitle());
		selectedBook.setIsbn(book.getIsbn());
		selectedBook.setCategory(book.getCategory());
		selectedBook.setCover_image(book.getCover_image());
		selectedBook.setAviable(book.getAviable());
		selectedBook.setUser(book.getUser());
		selectedBook.setEditorial(book.getEditorial());

		updatedBook = bookServiceImp.updateBook(selectedBook);

		System.out.println("Updated book is: " + updatedBook);

		return updatedBook;
	}

	@DeleteMapping("/book/{id}")
	public void deleteBook(@PathVariable(name = "id") Long id) {
		bookServiceImp.deleteBook(id);
	}

}
