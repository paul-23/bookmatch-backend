package com.redarpa.bookmatch.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.redarpa.bookmatch.dao.IBookDAO;
import com.redarpa.bookmatch.dto.Book;
import com.redarpa.bookmatch.service.BookServiceImp;
import com.redarpa.bookmatch.service.RatingServiceImp;
import com.redarpa.bookmatch.service.UserServiceImp;

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
	UserServiceImp userServiceImp;

	@Autowired
	RatingServiceImp ratingServiceImp;

	@Autowired
	IBookDAO iBookDAO;

	@GetMapping("/books")
	public List<Book> listBooks() {
		return bookServiceImp.listAllBooks();
	}

	@PostMapping(value = "/book", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Book saveBook(@RequestParam(value = "image", required = false) MultipartFile imageFile, @RequestPart("book") Book book)
			throws IOException {
		if (imageFile != null && !imageFile.isEmpty()) {
			byte[] imageBytes = imageFile.getBytes();
			Book savedBook = bookServiceImp.saveBookWithImage(book, imageBytes);
			return savedBook;
		} else {
			Book savedBook = bookServiceImp.saveBook(book);
			saveCoverByBookISBN(savedBook.getId_book(), savedBook.getIsbn());
			return savedBook;
		}
	}

	@GetMapping("/book/{id}")
	public Book bookById(@PathVariable(name = "id") Long id) throws IOException {

		Book bookById = new Book();
		bookById = bookServiceImp.bookById(id);

		if (bookById.getCover_image() == null || bookById.getCover_image().equals(null)
				|| bookById.getCover_image().equals("")) {
			saveCoverByBookISBN(bookById.getId_book(), bookById.getIsbn());
		}

		return bookById;
	}

	@GetMapping("book/image/{id}")
	public void getCoverByBookId(@PathVariable Long id, HttpServletResponse response) throws IOException {
		Book bookOptional = bookServiceImp.bookById(id);
		response.getOutputStream().write(bookOptional.getCover_image());
	}

	@PutMapping("/book/image/{id}")
	public Book saveCoverByBookId(@PathVariable(name = "id") Long id, @RequestParam("image") MultipartFile imageFile)
			throws IOException {

		BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", outputStream);
		byte[] imageBytes = outputStream.toByteArray();

		Book bookById = bookServiceImp.bookById(id);
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

	@PutMapping(value = "/book/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Book updateBook(@PathVariable(name = "id") Long id, @RequestParam(value = "image", required = false) MultipartFile imageFile,
			@RequestPart("book") Book book) throws IOException {
		Book selectedBook = bookServiceImp.bookById(id);
		selectedBook.setAuthor(book.getAuthor());
		selectedBook.setTitle(book.getTitle());
		selectedBook.setIsbn(book.getIsbn());
		selectedBook.setCategory(book.getCategory());
		selectedBook.setAviable(book.getAviable());
		selectedBook.setUser(book.getUser());
		selectedBook.setEditorial(book.getEditorial());

		Book updatedBook;
		if (imageFile != null && !imageFile.isEmpty()) {
			updatedBook = bookServiceImp.updateBookWithImage(selectedBook, imageFile.getBytes());
		} else {
			updatedBook = bookServiceImp.updateBook(selectedBook);
			saveCoverByBookISBN(updatedBook.getId_book(), updatedBook.getIsbn());
		}

		if (updatedBook.getCover_image() == null || updatedBook.getCover_image().equals(null)
				|| updatedBook.getCover_image().equals("")) {
			saveCoverByBookISBN(updatedBook.getId_book(), updatedBook.getIsbn());
		}

		return updatedBook;
	}

	@DeleteMapping("/book/{id}")
	public void deleteBook(@PathVariable(name = "id") Long id) {
		bookServiceImp.deleteBook(id);
	}

	@GetMapping("/book/isbn/{isbn}")
	public Book bookByIsbn(@PathVariable(name = "isbn") String isbn) throws IOException {

		Book bookByIsbn = new Book();
		bookByIsbn = bookServiceImp.bookByIsbn(isbn);

		return bookByIsbn;
	}

	@GetMapping("/book/author/{author}")
	public List<Book> bookByAuthor(@PathVariable(name = "author") String author) throws IOException {
		return bookServiceImp.bookByAuthor(author);
	}

	@GetMapping("/book/title/{title}")
	public List<Book> bookByTitle(@PathVariable(name = "title") String title) throws IOException {
		return bookServiceImp.bookByTitle(title);
	}

	@GetMapping("/books/user/{id}")
	public List<Book> findBooksByIdUser(@PathVariable(name = "id") Long user) {
		return bookServiceImp.findBooksByUser(userServiceImp.userById(user));
	}

}
