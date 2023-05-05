package com.redarpa.bookmatch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.redarpa.bookmatch.dao.IBookDAO;
import com.redarpa.bookmatch.dto.Book;

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
	
}
