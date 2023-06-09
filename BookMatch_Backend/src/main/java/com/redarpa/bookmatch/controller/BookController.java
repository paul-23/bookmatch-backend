package com.redarpa.bookmatch.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redarpa.bookmatch.dao.IBookDAO;
import com.redarpa.bookmatch.dto.Book;
import com.redarpa.bookmatch.service.BookServiceImp;
import com.redarpa.bookmatch.service.RatingServiceImp;
import com.redarpa.bookmatch.service.UserDetailsImpl;
import com.redarpa.bookmatch.service.UserServiceImp;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author RedArpa - BookMatch
 *
 */

@CrossOrigin(origins = "*", maxAge = 3600) // Allows requests from all origins with 1 hour cache
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

	/**
	 * Get all books
	 * @return List of books
	 */
	@GetMapping("/books")
	public List<Book> listBooks() {
		return bookServiceImp.listAllBooks();
	}
	
	@GetMapping("/listbooks")
	public List<Book> listOrderedBooks(@RequestParam(defaultValue = "0") int page,
	                                                   @RequestParam(defaultValue = "8") int size) {
		Sort sort = Sort.by("idBook").descending();
	    PageRequest pageable = PageRequest.of(page, size, sort);
	    Page<Book> bookPage = bookServiceImp.listAllBooks(pageable);
	    return bookPage.getContent();
	}
	
	@GetMapping("/listbooks/available")
	public List<Book> listOrderedAvailableBooks(@RequestParam(defaultValue = "0") int page,
	                                                   @RequestParam(defaultValue = "8") int size) {
		Sort sort = Sort.by("idBook").descending();
	    PageRequest pageable = PageRequest.of(page, size, sort);
	    Page<Book> bookPage = bookServiceImp.findByAvailableTrue(pageable);
	    return bookPage.getContent();
	}
	
	@GetMapping("/books/latest")
    public List<Book> getLatestBooks() {
		Sort sort = Sort.by("idBook").descending();
	    PageRequest pageable = PageRequest.of(0, 4, sort);
	    Page<Book> bookPage = bookServiceImp.findByAvailableTrue(pageable);
	    return bookPage.getContent();
    }

	/**
	 * Add new book 
	 * @param imageFile
	 * @param bookJson
	 * @return data of the added book
	 * @throws IOException
	 */
	@PostMapping(value = "/book")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public Book saveBook(@RequestParam(value = "image", required = false) MultipartFile imageFile,
			@RequestPart("book") String bookJson) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Book book = objectMapper.readValue(bookJson, Book.class);

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

	/**
	 * Get a book by Id
	 * @param id
	 * @return Book by id
	 * @throws IOException
	 */
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

	/**
	 * Get cover image by book Id
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("book/image/{id}")
	public void getCoverByBookId(@PathVariable Long id, HttpServletResponse response) throws IOException {
		Book bookOptional = bookServiceImp.bookById(id);
		response.getOutputStream().write(bookOptional.getCover_image());
	}

	/**
	 * Method that controls if user not provided cover image and search for an image from Openlibrary API.
	 * If the image cannot be found at Openlibrary, load a default image
	 */
	public void saveCoverByBookISBN(Long id, String isbn) {
		try {
			String url = OPEN_LIBRARY_API.replace("ISBN_NUMBER", isbn);
			URL imageUrl = new URL(url);
			BufferedImage bufferedImage = ImageIO.read(imageUrl);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", outputStream);
			byte[] imageBytes = outputStream.toByteArray();

			Book bookById = bookServiceImp.bookById(id);
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

	/**
	 * Edit book information
	 * @param id
	 * @param imageFile
	 * @param bookJson
	 * @return
	 * @throws IOException
	 */
	@PutMapping(value = "/book/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public Book updateBook(@PathVariable(name = "id") Long id,
			@RequestParam(value = "deleteCover", required = false, defaultValue = "false") boolean deleteCover,
			@RequestPart(value = "image", required = false) MultipartFile imageFile,
			@RequestPart("book") String bookJson) throws IOException {

		Book selectedBook = bookServiceImp.bookById(id);

		// Verificar si el usuario actual es el creador del libro
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			Long userId = userDetails.getId();

			// Comparar el ID del usuario actual con el ID del usuario del libro
			if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
					&& !selectedBook.getUser().getId_user().equals(userId)) {
				throw new AccessDeniedException("No tienes permiso para modificar este libro");
			}
		}

		ObjectMapper objectMapper = new ObjectMapper();
		Book updatedBook = objectMapper.readValue(bookJson, Book.class);

		selectedBook.setAuthor(updatedBook.getAuthor());
		selectedBook.setTitle(updatedBook.getTitle());
		selectedBook.setIsbn(updatedBook.getIsbn());
		selectedBook.setCategory(updatedBook.getCategory());
		selectedBook.setAviable(updatedBook.getAviable());
		selectedBook.setEditorial(updatedBook.getEditorial());
		selectedBook.setDescription(updatedBook.getDescription());
		
		if (imageFile != null && !imageFile.isEmpty() && !deleteCover) {
			byte[] imageBytes = imageFile.getBytes();
			selectedBook = bookServiceImp.updateBookWithImage(selectedBook, imageBytes);
		} else if (deleteCover) {
			selectedBook = bookServiceImp.updateBook(selectedBook);
			saveCoverByBookISBN(selectedBook.getId_book(), selectedBook.getIsbn());
		} else {
			selectedBook = bookServiceImp.updateBook(selectedBook);
		}

		return selectedBook;
	}

	/**
	 * Delete book by its Id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/book/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<Map<String, Object>> deleteBook(@PathVariable(name = "id") Long id) {

		Book selectedBook = bookServiceImp.bookById(id);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			Long userId = userDetails.getId();

			// Comparar el ID del usuario actual con el ID del usuario del libro
			if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
					&& !selectedBook.getUser().getId_user().equals(userId)) {
				throw new AccessDeniedException("You do not have permission to delete this book");
			} else {
				bookServiceImp.deleteBook(id);
				Map<String, Object> response = new HashMap<>();
				response.put("success", true);
				response.put("message", "Book deleted successfully");

				return ResponseEntity.ok(response);
			}
		}
		throw new AccessDeniedException("You do not have permission to delete this book");
	}

	/**
	 * Change book to available state, only when logged with admin user or the owner's user
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/book/{id}/available")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public Book updateBookAvailability(@PathVariable(name = "id") Long id) {
		Book selectedBook = bookServiceImp.bookById(id);

		// Verificar si el usuario actual es el creador del libro
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			Long userId = userDetails.getId();

			// Comparar el ID del usuario actual con el ID del usuario del libro
			if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
					&& !selectedBook.getUser().getId_user().equals(userId)) {
				throw new AccessDeniedException("No tienes permiso para modificar este libro");
			}
		}

		selectedBook.setAviable(true);

		Book updatedBook = bookServiceImp.updateBook(selectedBook);

		return updatedBook;
	}

	/**
	 * Change book to not available state, only when logged with admin user or the owner's user
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/book/{id}/notavailable")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public Book updateBookNotAviable(@PathVariable(name = "id") Long id) {

		Book selectedBook = bookServiceImp.bookById(id);
		selectedBook.setAviable(false);
		Book updatedBook = bookServiceImp.updateBook(selectedBook);

		return updatedBook;
	}

	/**
	 * Find book by ISBN
	 * @param isbn
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/book/isbn/{isbn}")
	public List<Book> bookByIsbn(@PathVariable(name = "isbn") String isbn) throws IOException {
		return bookServiceImp.bookByIsbn(isbn);
	}

	/**
	 * Find books by author
	 * @param author
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/book/author/{author}")
	public List<Book> bookByAuthor(@PathVariable(name = "author") String author) throws IOException {
		return bookServiceImp.bookByAuthor(author);
	}

	/**
	 * Find books by title
	 * @param title
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/book/title/{title}")
	public List<Book> bookByTitle(@PathVariable(name = "title") String title) throws IOException {
		return bookServiceImp.bookByTitle(title);
	}

	/**
	 * Find books by category
	 * @param category
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/book/category/{category}")
	public List<Book> bookByCategory(@PathVariable(name = "category") String category) throws IOException {

		return bookServiceImp.bookByCategory(category);
	}

	/**
	 * Get books by user Id
	 * @param user
	 * @return
	 */
	@GetMapping("/books/user/{id}")
	public List<Book> findBooksByIdUser(@PathVariable(name = "id") Long user) {
		return bookServiceImp.findBooksByUser(userServiceImp.userById(user));
	}

}
