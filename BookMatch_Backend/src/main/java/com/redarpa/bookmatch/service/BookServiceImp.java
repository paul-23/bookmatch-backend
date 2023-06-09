package com.redarpa.bookmatch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.redarpa.bookmatch.dao.IBookDAO;
import com.redarpa.bookmatch.dto.Book;
import com.redarpa.bookmatch.dto.User;

/**
 * @author RedArpa - BookMatch
 *
 */

@Service
public class BookServiceImp implements IBookService {

	@Autowired
	IBookDAO iBookDAO;

	@Override
	public List<Book> listAllBooks() {
		return iBookDAO.findAll();
	}

	@Override
	public Book saveBook(Book book) {
		return iBookDAO.save(book);
	}

	@Override
	public Book bookById(Long id) {
		return iBookDAO.findById(id).get();
	}

	@Override
	public Book updateBook(Book book) {
		return iBookDAO.save(book);
	}

	@Override
	public void deleteBook(Long id) {
		iBookDAO.deleteById(id);
	}

	public Book saveImage(Book book) {
		return iBookDAO.save(book);
	}

	@Override
	public List<Book> bookByIsbn(String isbn) {
		String formattedISBN= "%" + isbn.toLowerCase() + "%";
		return iBookDAO.findBookByIsbnLike(formattedISBN);
	}
	
	@Override
	public List<Book> bookByTitle(String title) {
		String formattedTitle = "%" + title.toLowerCase() + "%";
		return iBookDAO.findBookByTitleLike(formattedTitle);
	}

	@Override
	public List<Book> bookByAuthor(String author) {
		String formattedAuthor = "%" + author.toLowerCase() + "%";
		return iBookDAO.findBookByAuthorLike(formattedAuthor);
	}
	
	@Override
	public List<Book> bookByCategory(String category) {
		String formattedCategory = "%" + category.toLowerCase() + "%";
		return iBookDAO.findBookByCategoryLike(formattedCategory);
	}

	@Override
	public List<Book> findBooksByUser(User user) {
		return iBookDAO.findBooksByUser(user);
	}

	public Book saveBookWithImage(Book book, byte[] bytes) {
		book.setCover_image(bytes);
		return iBookDAO.save(book);
	}

	public Book updateBookWithImage(Book book, byte[] imageBytes) {
		book.setCover_image(imageBytes);
		return iBookDAO.save(book);
	}
	
	public Page<Book> listAllBooks(Pageable pageable) {
        return iBookDAO.findAll(pageable);
    }
	
	public Page<Book> findByAvailableTrue(Pageable pageable) {
		return iBookDAO.findByAviableTrue(pageable);
	}
	
}
