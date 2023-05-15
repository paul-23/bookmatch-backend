package com.redarpa.bookmatch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redarpa.bookmatch.dao.IBookDAO;
import com.redarpa.bookmatch.dto.Book;

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
	public Book bookByIsbn(String isbn) {
		return iBookDAO.findBookByIsbn(isbn);
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
	public List<Book> bookByTitle(String title) {
		String formattedTitle = "%" + title.toLowerCase() + "%";
		return iBookDAO.findBookByTitleLike(formattedTitle);
	}
	
	@Override
	public List<Book> bookByAuthor(String author) {
		String formattedAuthor = "%" + author.toLowerCase() + "%";
		return iBookDAO.findBookByAuthorLike(formattedAuthor);
	}

}
