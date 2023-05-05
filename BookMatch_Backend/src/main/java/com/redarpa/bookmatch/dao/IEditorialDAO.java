package com.redarpa.bookmatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redarpa.bookmatch.dto.Editorial;

public interface IEditorialDAO extends JpaRepository<Editorial, Long> {

}
