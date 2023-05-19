package com.redarpa.bookmatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redarpa.bookmatch.dto.Editorial;

/**
 * @author RedArpa - BookMatch
 *
 */

public interface IEditorialDAO extends JpaRepository<Editorial, Long> {

}
