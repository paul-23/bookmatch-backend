package com.redarpa.bookmatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redarpa.bookmatch.dto.Book;

public interface IBookDAO extends JpaRepository<Book, Long> {

}
