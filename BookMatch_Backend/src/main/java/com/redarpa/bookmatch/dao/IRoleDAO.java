/**
 * 
 */
package com.redarpa.bookmatch.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redarpa.bookmatch.dto.ERole;
import com.redarpa.bookmatch.dto.Role;

/**
 * @author Marc
 *
 */
public interface IRoleDAO extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
